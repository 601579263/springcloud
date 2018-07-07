package com.spc.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.spc.base.ResponseBase;
import com.spc.constants.Constants;
import com.spc.entity.UserEntity;
import com.spc.feign.MemBerServiceFeign;
import com.spc.weibo4j.Oauth;
import com.spc.weibo4j.Users;
import com.spc.weibo4j.http.AccessToken;
import com.spc.weibo4j.model.User;
import com.spc.weibo4j.model.WeiboException;
import com.spc.weibo4j.util.BareBonesBrowserLaunch;

@Controller
public class SinaLoginController {
	
	@Autowired
	private MemBerServiceFeign memBerServiceFeign;

		/**
		* 新浪登录页面
		* @param request
		* @param response
		 * @throws WeiboException 
		 * @throws IOException 
		*/
		@RequestMapping("/sinaLogin")
		public void sinaLogin(HttpServletRequest request, HttpServletResponse response) throws WeiboException, IOException{
		        try {
		            response.sendRedirect(new Oauth().authorize("code","",""));
		        } catch (Exception e) {
		           //LoggerUtil.error(e);
		        }
			
		}
		
		
		/**
		* 新浪回调页面
		* @param request
		* @param response
		* @return
		 * @throws WeiboException 
		*/
		@RequestMapping(value="/sinaLoginCallback")
		public String sinaLoginCallback(HttpServletRequest request, HttpServletResponse response,HttpSession httpSession) throws WeiboException{
			String code = request.getParameter("code");  
			Oauth oauth = new Oauth();
			 AccessToken token = null ;
			 try {
				 token = oauth.getAccessTokenByCode(code);
			} catch (WeiboException e) {
				e.printStackTrace();
			}
			//token 
			String strToken = token.getAccessToken() ;
			//用户ID 当作OpenID使用
			String openid = token.getUid();
			Users um = new Users(strToken);
	        User user = um.showUserById(openid);//调用新浪微博查询接口
	      //根据用户新浪微博id判断是否存在数据库
	        UserEntity en=this.memBerServiceFeign.findUserBySinaid(user.getId());
	        if(en==null) {
	        	//如果不存在 跳到关联新浪微博界面
	        	httpSession.setAttribute("sina_userId",user.getId());
	        	return "sinarelation";
	        }
	        return "index";
	}
		
	//新浪微博登录请求具体提交实现
	@RequestMapping(value = "/sinarelation", method = RequestMethod.POST)
	public String sinarelation(UserEntity userEntity, HttpServletRequest request, HttpServletResponse response,HttpSession httpSession) {
		// 1.获取sina_userId
		String sina_userId=(String) httpSession.getAttribute("sina_userId");
		if(StringUtils.isEmpty(sina_userId)){
			request.setAttribute("error", "没有获取到sina_userId");
			return "error";
		}
		// 2.调用登录接口
		userEntity.setSinaid(sina_userId);
		ResponseBase loginBase = memBerServiceFeign.sinaLogin(userEntity);
		if (!loginBase.getCode().equals(Constants.HTTP_RES_CODE_200)) {
			request.setAttribute("error", "账号或者密码错误!");
			return "login";
		}
		return "index";
	}	
		
	
}
