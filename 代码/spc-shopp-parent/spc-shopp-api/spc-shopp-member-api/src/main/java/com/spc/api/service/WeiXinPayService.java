package com.spc.api.service;

import java.util.Map;

import org.springframework.web.bind.annotation.RequestMapping;
@RequestMapping("/weiXinPay")
public interface WeiXinPayService {

	/**
	 * 生成二维码
	 * @param out_trade_no
	 * @param total_fee
	 * @return
	 */
	public Map createNative(String out_trade_no,String total_fee);
}
