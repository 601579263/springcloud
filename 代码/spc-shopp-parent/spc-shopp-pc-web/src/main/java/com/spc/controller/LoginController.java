package com.spc.controller;

import static org.mockito.Matchers.contains;

import java.util.LinkedHashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.spc.base.ResponseBase;
import com.spc.constants.Constants;
import com.spc.entity.UserEntity;
import com.spc.feign.MemBerServiceFeign;
import com.spc.utils.CookieUtil;
import com.qq.connect.QQConnectException;
import com.qq.connect.api.OpenID;
import com.qq.connect.javabeans.AccessToken;
import com.qq.connect.oauth.Oauth;

import io.reactivex.netty.protocol.http.server.HttpServerResponse;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

@Controller
public class LoginController {

	private static final String LOGIN="login";
	private static final String INDEX="redirect:/";//重定向到首页
	private static final String qqrelation = "qqrelation";
	
	@Autowired
	private MemBerServiceFeign memBerServiceFeign;
	
	/**
	 * 跳到登陆界面
	 * @return
	 */
	@RequestMapping(value="/login",method=RequestMethod.GET)
	public String loginGet() {
		return LOGIN;
	}
	
	/**
	 * 登陆具体实现
	 * @return
	 */
	@RequestMapping(value="/login",method=RequestMethod.POST)
	public String loginPost( UserEntity userEntity,HttpServletRequest request,HttpServletResponse response) {
		//1.调用登陆接口 获取token信息
		ResponseBase responseBase=this.memBerServiceFeign.login(userEntity);
		if(!responseBase.getCode().equals(Constants.HTTP_RES_CODE_200)) {
			request.setAttribute("error", "账号或者密码错误");
			return LOGIN;
		}
		LinkedHashMap loginData=(LinkedHashMap) responseBase.getData();
		String token=(String) loginData.get("token");
		if(StringUtils.isEmpty(token)) {
			request.setAttribute("error", "会话已经失效");
			return LOGIN;
		}
		//2.将token信息放到cookie里面
		CookieUtil.addCookie(response, Constants.COOKIE_MEMBER_TOKEN, token, Constants.COOKIE_TOKEN_MEMBER_TIME);
		return INDEX;
	}
	
	
		// 生成qq授权登录链接
		@RequestMapping("/locaQQLogin")
		public String locaQQLogin(HttpServletRequest reqest) throws QQConnectException {
			String authorizeURL = new Oauth().getAuthorizeURL(reqest);
			return "redirect:" + authorizeURL;
		}
	
		@RequestMapping("/qqLoginCallback")
		public String qqLoginCallback(HttpServletRequest reqest, HttpServletResponse response,HttpSession httpSession) throws QQConnectException {
			// 1.获取授权码COde
			// 2.使用授权码Code获取accessToken
			AccessToken accessTokenOj = new Oauth().getAccessTokenByRequest(reqest);
			if (accessTokenOj == null) {
				reqest.setAttribute("error", "QQ授权失败");
				return "error";
			}
			String accessToken = accessTokenOj.getAccessToken();
			if (accessToken == null) {
				reqest.setAttribute("error", "accessToken为null");
				return "error";
			}
			// 3.使用accessToken获取openid
			OpenID openidOj = new OpenID(accessToken);
			String userOpenId = openidOj.getUserOpenID();
			// 4.调用会员服务接口 使用userOpenId 查找是否已经关联过账号
			ResponseBase openUserBase = memBerServiceFeign.findByOpenIdUser(userOpenId);
			if(openUserBase.getCode().equals(Constants.HTTP_RES_CODE_201)){
				// 5.如果没有关联账号，跳转到关联账号页面
				httpSession.setAttribute("qqOpenid", userOpenId);
				return qqrelation;
			}
			//6.已经绑定账号  自动登录 将用户token信息存放在cookie中
			LinkedHashMap dataTokenMap = (LinkedHashMap) openUserBase.getData();
			String memberToken=(String) dataTokenMap.get("memberToken");
			CookieUtil.addCookie(response, Constants.COOKIE_MEMBER_TOKEN, memberToken, Constants.COOKIE_TOKEN_MEMBER_TIME);
			return INDEX;
		}

		// 登录请求具体提交实现
		@RequestMapping(value = "/qqRelation", method = RequestMethod.POST)
		public String qqRelation(UserEntity userEntity, HttpServletRequest request, HttpServletResponse response,HttpSession httpSession) {
			// 1.获取openid
			String qqOpenid=(String) httpSession.getAttribute("qqOpenid");
			if(StringUtils.isEmpty(qqOpenid)){
				request.setAttribute("error", "没有获取到openid");
				return "error";
			}
			
			// 2.调用登录接口，获取token信息
			userEntity.setOpenid(qqOpenid);
			ResponseBase loginBase = memBerServiceFeign.qqLogin(userEntity);
			if (!loginBase.getCode().equals(Constants.HTTP_RES_CODE_200)) {
				request.setAttribute("error", "账号或者密码错误!");
				return LOGIN;
			}

			LinkedHashMap loginData = (LinkedHashMap) loginBase.getData();
			String memberToken = (String) loginData.get("token");
			if (StringUtils.isEmpty(memberToken)) {
				request.setAttribute("error", "会话已经失效!");
				return LOGIN;
			}
			// 3.将token信息存放在cookie里面
			CookieUtil.addCookie(response, Constants.COOKIE_MEMBER_TOKEN, memberToken, Constants.COOKIE_TOKEN_MEMBER_TIME);
			return INDEX;
		}
}
