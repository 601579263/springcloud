package com.spc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
/**
 * 测试网关
 * @author 60157
 *
 */
@RestController
public class TestZuulController {
	
	
	@RequestMapping("/getMemberServiceZuul")
	public String getMemberServiceZuul(){
		return "这是这是会员网关服务";
	}
}
