package com.spc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
@Controller
public class WxLoginController {

	@RequestMapping("/locaWXLogin")
	public String locaWXLogin() {
		//暂时无法接入微信登陆 需要公司账号才可以接入
		return "wx";
	}
}
