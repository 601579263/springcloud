package com.spc.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.spc.base.ResponseBase;
import com.spc.constants.Constants;
import com.spc.entity.UserEntity;
import com.spc.feign.MemBerServiceFeign;

@Controller
public class RegisterController {
	private static final String REGISTER="register";
	private static final String LOGIN="login";
	@Autowired
	private MemBerServiceFeign memBerServiceFeign;
	/**
	 * 跳转注册页面
	 * @return
	 */
	@RequestMapping(value="/register",method=RequestMethod.GET)
	public String register() {
		return REGISTER;
	}
	
	/**
	 * 注册业务具体实现
	 * @return
	 */
	@RequestMapping(value="/registerPost",method=RequestMethod.POST)
	public String registerPost(UserEntity userEntity,HttpServletRequest request) {
		//1.验证参数
		//2.调用会员注册接口
		 ResponseBase responseBase=memBerServiceFeign.registUser(userEntity);
		//3.如果失败跳转到失败界面
		 if(!responseBase.getCode().equals(Constants.HTTP_RES_CODE_200)) {
			 request.setAttribute("error", "注册失败");
			 return REGISTER;
		 }
		//4.如果成功跳转到成功界面
		return LOGIN;
	}
}
