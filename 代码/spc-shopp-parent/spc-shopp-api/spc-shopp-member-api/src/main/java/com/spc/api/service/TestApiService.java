package com.spc.api.service;

import java.util.Map;

import org.springframework.web.bind.annotation.RequestMapping;

import com.spc.base.ArchivesLog;
import com.spc.base.ResponseBase;

@RequestMapping("/member")
public interface TestApiService {

	@RequestMapping("/test")
	Map<String,Object>test(Integer id,String name);
	@RequestMapping("/testResponseBase") 
	public ResponseBase testResponseBase();
}
