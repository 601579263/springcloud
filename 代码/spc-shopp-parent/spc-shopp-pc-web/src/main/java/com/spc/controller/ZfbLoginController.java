package com.spc.controller;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayOpenAuthTokenAppRequest;
import com.alipay.api.request.AlipaySystemOauthTokenRequest;
import com.alipay.api.request.AlipayUserInfoShareRequest;
import com.alipay.api.response.AlipayOpenAuthTokenAppResponse;
import com.alipay.api.response.AlipaySystemOauthTokenResponse;
import com.alipay.api.response.AlipayUserInfoShareResponse;
import com.spc.alipay.config.AlipayConfig;
import com.spc.base.ResponseBase;
import com.spc.constants.Constants;
import com.spc.entity.UserEntity;
import com.spc.feign.MemBerServiceFeign;
import com.spc.utils.CookieUtil;

/**
 * 支付宝登陆控制层
 * @author 60157
 *
 */
@Controller
public class ZfbLoginController {

	@Autowired
	private MemBerServiceFeign memBerServiceFeign;
	
	/**
	 * 支付宝登陆后回调地址
	 * @return
	 * @throws AlipayApiException 
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping("/zfbLoginCallback1")
	public String zfbLoginCallback1(HttpServletRequest request) throws AlipayApiException, UnsupportedEncodingException {
		//获取支付宝GET过来反馈信息
	    Map<String,String> params = new HashMap<String,String>();
	    Map requestParams = request.getParameterMap();
	    for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
	        String name = (String) iter.next();
	        String[] values = (String[]) requestParams.get(name);
	        String valueStr = "";
	        for (int i = 0; i < values.length; i++) {
	            valueStr = (i == values.length - 1) ? valueStr + values[i]
	                    : valueStr + values[i] + ",";
	        }
	        //乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
	        valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
	        params.put(name, valueStr);
	    }
	    
	    //获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以下仅供参考)//
	    //支付宝用户号
	    String app_id = new String(request.getParameter("app_id").getBytes("ISO-8859-1"),"UTF-8");
	    //获取第三方登录授权
	    String alipay_app_auth = new String(request.getParameter("source").getBytes("ISO-8859-1"),"UTF-8");
	    //第三方授权code
	    String app_auth_code = new String(request.getParameter("app_auth_code").getBytes("ISO-8859-1"),"UTF-8");//获的第三方登录用户授权app_auth_code
	    
	    //使用auth_code换取接口access_token及用户userId
	     //AlipayClient alipayClient = new DefaultAlipayClient("https://openapi.alipay.com/gateway.do","应用APPID",privateKey,"json","UTF-8",publicKey,"RSA2");//正常环境下的网关
	     AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfig.gatewayUrl,AlipayConfig.app_id,AlipayConfig.merchant_private_key,"json","UTF-8",AlipayConfig.alipay_public_key,"RSA2");//沙箱下的网关
	     
	     
	    AlipayOpenAuthTokenAppRequest requestLogin1 = new AlipayOpenAuthTokenAppRequest();
	    requestLogin1.setBizContent("{" +
	        "\"grant_type\":\"authorization_code\"," +
	        "\"code\":\""+ app_auth_code +"\"" +
	        "}");
	    
	    //第三方授权
	    AlipayOpenAuthTokenAppResponse responseToken = alipayClient.execute(requestLogin1);
	    if(responseToken.isSuccess()){
	        request.setAttribute("AuthAppId", responseToken.getAuthAppId());
	        request.setAttribute("AuthToken", responseToken.getAppAuthToken());
	        request.setAttribute("UserId", responseToken.getUserId());
	    } else {
	        request.setAttribute("result", "调用失败");
	    }
		return "zfbrelation";
	}
	
	/**
	 * 支付宝登陆后用户授权回调地址
	 * @return
	 * @throws AlipayApiException 
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping("/zfbLoginCallback")
	public String zfbLoginCallback(HttpServletRequest request,HttpSession httpSession) throws AlipayApiException, UnsupportedEncodingException {
		//获取支付宝GET过来反馈信息
	    Map<String,String> params = new HashMap<String,String>();
	    Map requestParams = request.getParameterMap();
	    for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
	        String name = (String) iter.next();
	        String[] values = (String[]) requestParams.get(name);
	        String valueStr = "";
	        for (int i = 0; i < values.length; i++) {
	            valueStr = (i == values.length - 1) ? valueStr + values[i]
	                    : valueStr + values[i] + ",";
	        }
	        //乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
	        valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
	        params.put(name, valueStr);
	    }
	    
	    //获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以下仅供参考)//
	    //支付宝用户号
	    String app_id = new String(request.getParameter("app_id").getBytes("ISO-8859-1"),"UTF-8");
	    
	    //获取用户信息授权
	    String auth_user = new String(request.getParameter("scope").getBytes("ISO-8859-1"),"UTF-8");
	    
	    //获的第三方登录用户授权auth_code
	    String auth_code = new String(request.getParameter("auth_code").getBytes("ISO-8859-1"),"UTF-8");
	    
	    //使用auth_code换取接口access_token及用户userId
	     //AlipayClient alipayClient = new DefaultAlipayClient("https://openapi.alipay.com/gateway.do","应用APPID",privateKey,"json","UTF-8",publicKey,"RSA2");//正常环境下的网关
	     AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfig.gatewayUrl,AlipayConfig.app_id,AlipayConfig.merchant_private_key,"json","UTF-8",AlipayConfig.alipay_public_key,"RSA2");//沙箱下的网关

	    //获取用户信息授权
	       AlipaySystemOauthTokenRequest requestLogin2 = new AlipaySystemOauthTokenRequest();
	       requestLogin2.setCode(auth_code);
	       requestLogin2.setGrantType("authorization_code");
	       try {
	    AlipaySystemOauthTokenResponse oauthTokenResponse = alipayClient.execute(requestLogin2);
	     //调用接口获取用户信息
	    AlipayClient alipayClientUser = new DefaultAlipayClient(AlipayConfig.gatewayUrl,AlipayConfig.app_id,AlipayConfig.merchant_private_key,"json","UTF-8",AlipayConfig.alipay_public_key,"RSA2"); 
	    AlipayUserInfoShareRequest requestUser = new AlipayUserInfoShareRequest();
	    try {
		        AlipayUserInfoShareResponse userinfoShareResponse = alipayClient.execute(requestUser, oauthTokenResponse.getAccessToken());
		     	/*request.setAttribute("UserId", userinfoShareResponse.getUserId());//用户支付宝ID
				request.setAttribute("UserType", userinfoShareResponse.getUserType());//用户类型
				request.setAttribute("UserStatus", userinfoShareResponse.getUserStatus());//用户账户动态
				request.setAttribute("Email", userinfoShareResponse.getEmail());//用户Email地址
				request.setAttribute("IsCertified", userinfoShareResponse.getIsCertified());//用户是否进行身份认证
				request.setAttribute("IsStudentCertified", userinfoShareResponse.getIsStudentCertified());//用户是否进行学生认证
				request.setAttribute("app_id", app_id);//支付宝用户号
				request.setAttribute("auth_user", auth_user);//获取用户信息授权
				request.setAttribute("auth_code", auth_code);//获的第三方登录用户授权auth_code*/
		        
		        //根据用户支付宝id判断是否存在数据库
		        UserEntity user=this.memBerServiceFeign.findUserByZfbid(userinfoShareResponse.getUserId());
		        if(user==null) {
		        	//如果不存在 跳到关联支付宝界面
		        	httpSession.setAttribute("zfb_userId", userinfoShareResponse.getUserId());
		        	request.setAttribute("UserId", userinfoShareResponse.getUserId());//用户支付宝ID
		        	return "zfbrelation";
		        }
	        
	        } catch (AlipayApiException e) {
	        //处理异常
	        e.printStackTrace();
	    }
	    } catch (AlipayApiException e) {
	        //处理异常
	        e.printStackTrace();
	    }
		return "index";
	}
	
	// 支付宝登录请求具体提交实现
	@RequestMapping(value = "/zfbRelation", method = RequestMethod.POST)
	public String zfbRelation(UserEntity userEntity, HttpServletRequest request, HttpServletResponse response,HttpSession httpSession) {
		// 1.获取zfb_userId
		String zfb_userId=(String) httpSession.getAttribute("zfb_userId");
		if(StringUtils.isEmpty(zfb_userId)){
			request.setAttribute("error", "没有获取到zfb_userId");
			return "error";
		}
		// 2.调用登录接口
		userEntity.setZfbid(zfb_userId);
		ResponseBase loginBase = memBerServiceFeign.zfbLogin(userEntity);
		if (!loginBase.getCode().equals(Constants.HTTP_RES_CODE_200)) {
			request.setAttribute("error", "账号或者密码错误!");
			return "login";
		}
		return "index";
	}
}
