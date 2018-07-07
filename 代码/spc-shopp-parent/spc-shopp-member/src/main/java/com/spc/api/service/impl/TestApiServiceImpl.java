package com.spc.api.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

import com.spc.api.service.TestApiService;
import com.spc.base.ArchivesLog;
import com.spc.base.BaseApiService;
import com.spc.base.ResponseBase;

@RestController //返回json格式
public class TestApiServiceImpl extends BaseApiService implements TestApiService{

	@Override
	public Map<String, Object> test(Integer id, String name) {
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("rtnCode","200");
		map.put("rtnMsg", "success");
		map.put("data", new String[] {"111","222",id+"",name});
		return map;
	}

	@Override
	@ArchivesLog(operationType="查询操作:",operationName="查询文件")
	public ResponseBase testResponseBase() {
		return setResultSuccess();
	}

}
