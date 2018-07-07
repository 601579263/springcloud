package com.spc.mq;



import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.ctc.wstx.util.StringUtil;
import com.google.gson.JsonObject;
import com.spc.adapter.MessageAdapter;
import com.spc.constants.Constants;
import com.spc.email.service.EmailService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class ConsumerDistribute {

	@Autowired
	private MessageAdapter messageAdapter;
	@Autowired
	private EmailService emailService;
	//监听消息
	@JmsListener(destination="messages_queue")
	public void distribute(String json) {
		log.info("==========消息服务平台接收消息内容============",json);
		if(StringUtils.isEmpty(json)) {
			return ;
		}
		JSONObject rootJson=new JSONObject().parseObject(json);
		JSONObject header=rootJson.getJSONObject("header");
		String interfaceType=header.getString("interfaceType");
		if(StringUtils.isEmpty(interfaceType)) {
			return ;
		}
		//判断接口类型是否为发邮件
		if(interfaceType.equals(Constants.MSG_EMAIL)) {
			messageAdapter=emailService;
		}
		if(messageAdapter==null) {
			return ;
		}
		JSONObject contentJson=rootJson.getJSONObject("content");
		messageAdapter.sendMsg(contentJson);
	}
}
