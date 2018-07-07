package com.spc.controller;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.spc.alipay.config.AlipayConfig;
import com.spc.entity.OrderEntity;
import com.spc.feign.OrderServiceFeign;
import com.alipay.api.request.*;
/**
 * 支付宝支付和订单控制层
 * @author 60157
 *
 */
@Controller
public class PayOrderController {

	@Autowired
	private OrderServiceFeign orderServiceFeign;
	
	@RequestMapping(value="/queryOrderList",method=RequestMethod.GET)
	public String queryOrderList(HttpServletRequest request) {
		List<OrderEntity>orderList=this.orderServiceFeign.queryOrderList();
		request.setAttribute("orderList", orderList);
		return "order";
	}
	/**
	 * 付款请求
	 * @param request
	 * @return
	 * @throws UnsupportedEncodingException
	 * @throws AlipayApiException 
	 */
	@RequestMapping(value="/alipayTradePagePay",method=RequestMethod.POST)
	public String alipayTradePagePay(HttpServletRequest request) throws UnsupportedEncodingException, AlipayApiException {
		//获得初始化的AlipayClient
		AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfig.gatewayUrl, AlipayConfig.app_id, AlipayConfig.merchant_private_key, "json", AlipayConfig.charset, AlipayConfig.alipay_public_key, AlipayConfig.sign_type);
		
		//设置请求参数
		AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
		alipayRequest.setReturnUrl(AlipayConfig.return_url);//支付成功后的同步返回地址
		alipayRequest.setNotifyUrl(AlipayConfig.notify_url);//支付成功后的异步返回地址
		
		//商户订单号，商户网站订单系统中唯一订单号，必填
		String out_trade_no = new String(request.getParameter("WIDout_trade_no").getBytes("ISO-8859-1"),"UTF-8");
		//付款金额，必填
		String total_amount = new String(request.getParameter("WIDtotal_amount").getBytes("ISO-8859-1"),"UTF-8");
		//订单名称，必填new String(request.getParameter("WIDsubject").getBytes("ISO-8859-1"),"UTF-8");
		String subject = request.getParameter("WIDsubject");
		//商品描述，可空new String(request.getParameter("WIDbody").getBytes("ISO-8859-1"),"UTF-8");
		String body = request.getParameter("WIDbody");
		if(StringUtils.isEmpty(out_trade_no)) {
			return "订单号不能为空";
		}
		if(StringUtils.isEmpty(total_amount)) {
			return "付款金额不能为空";
		}
		if(StringUtils.isEmpty(subject)) {
			return "订单名称不能为空";
		}
		//添加到商户订单表中
		OrderEntity order=new OrderEntity();
		order.setWidoutTradeNo(out_trade_no);
		order.setWidsubject(subject);
		order.setWidtotalAmount(Double.parseDouble(total_amount));
		order.setWidbody(body);
		//1.根据订单号获取订单表数据
		OrderEntity or=this.orderServiceFeign.findByOrderId(out_trade_no);
		if(or!=null) {
			request.setAttribute("result", "该订单已存在");
			return "error";
		}
		
		int add=this.orderServiceFeign.insertOrderAll(order);
		alipayRequest.setBizContent("{\"out_trade_no\":\""+ out_trade_no +"\"," 
				+ "\"total_amount\":\""+ total_amount +"\"," 
				+ "\"subject\":\""+ subject +"\"," 
				+ "\"body\":\""+ body +"\"," 
				+ "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");
		
		//若想给BizContent增加其他可选请求参数，以增加自定义超时时间参数timeout_express来举例说明
		//alipayRequest.setBizContent("{\"out_trade_no\":\""+ out_trade_no +"\"," 
		//		+ "\"total_amount\":\""+ total_amount +"\"," 
		//		+ "\"subject\":\""+ subject +"\"," 
		//		+ "\"body\":\""+ body +"\"," 
		//		+ "\"timeout_express\":\"10m\"," 
		//		+ "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");
		//请求参数可查阅【电脑网站支付的API文档-alipay.trade.page.pay-请求参数】章节
		
		//请求
		String result = alipayClient.pageExecute(alipayRequest).getBody();
		//输出
		request.setAttribute("result", result);
		//out.println(result);
		return "alipayTradePagePay";//打开支付宝支付界面
	}
	/**
	 * 支付成功后的同步返回地址
	 * @return
	 * @throws AlipayApiException 
	 */
	@RequestMapping(value="/returnUrl",method=RequestMethod.GET)
	public String returnUrl(HttpServletRequest request) throws AlipayApiException {
		//获取支付宝GET过来反馈信息
		Map<String,String> params = new HashMap<String,String>();
		Map<String,String[]> requestParams = request.getParameterMap();
		for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext();) {
			String name = (String) iter.next();
			String[] values = (String[]) requestParams.get(name);
			String valueStr = "";
			for (int i = 0; i < values.length; i++) {
				valueStr = (i == values.length - 1) ? valueStr + values[i]
						: valueStr + values[i] + ",";
			}
			//乱码解决，这段代码在出现乱码时使用valueStr =new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
			 
			params.put(name, valueStr);
		}
		
		boolean signVerified = AlipaySignature.rsaCheckV1(params, AlipayConfig.alipay_public_key, AlipayConfig.charset, AlipayConfig.sign_type); //调用SDK验证签名

		//——请在这里编写您的程序（以下代码仅作参考）——
		if(signVerified) {
			//商户订单号
			String out_trade_no = request.getParameter("out_trade_no");
			//支付宝交易号
			String trade_no = request.getParameter("trade_no");
			//付款金额
			String total_amount = request.getParameter("total_amount");
			//修改商户订单表
			//1.根据订单号获取订单表数据
			OrderEntity order=this.orderServiceFeign.findByOrderId(out_trade_no);
			order.setWidtqtradeNo(trade_no);//存储支付宝交易号
			int upd=this.orderServiceFeign.updateOrderAll(order);
			String msg="商户订单号:"+out_trade_no+"支付宝交易号:"+trade_no+"付款金额:"+total_amount;
			request.setAttribute("msg", msg);
		}else {
			request.setAttribute("msg", "验签失败");
		}
		//——请在这里编写您的程序（以上代码仅作参考）——
		return "returnUrl";//支付成功后的同步返回界面
	}
	
	/**
	 * 交易查询
	 * @return
	 * @throws AlipayApiException 
	 */
	@RequestMapping(value="/alipayTradeQuery",method=RequestMethod.POST)
	public String alipayTradeQuery(HttpServletRequest request) throws AlipayApiException {
		//获得初始化的AlipayClient
		AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfig.gatewayUrl, AlipayConfig.app_id, AlipayConfig.merchant_private_key, "json", AlipayConfig.charset, AlipayConfig.alipay_public_key, AlipayConfig.sign_type);
		
		//设置请求参数
		AlipayTradeQueryRequest alipayRequest = new AlipayTradeQueryRequest();
		
		//商户订单号，商户网站订单系统中唯一订单号
		String out_trade_no =request.getParameter("WIDTQout_trade_no");
		//支付宝交易号
		String trade_no = request.getParameter("WIDTQtrade_no");
		//请二选一设置
		
		alipayRequest.setBizContent("{\"out_trade_no\":\""+ out_trade_no +"\","+"\"trade_no\":\""+ trade_no +"\"}");
		
		//请求
		String result = alipayClient.execute(alipayRequest).getBody();
		JSONObject jsonObject=JSONObject.parseObject(result);
		String alipay_trade_query_response= jsonObject.get("alipay_trade_query_response").toString();
		JSONObject json=JSONObject.parseObject(alipay_trade_query_response);
		String code=json.getString("code");//10000是成功
		if(code.equals("10000")) {
			//查询成功
			//买家账号
			String buyer_logon_id=json.getString("buyer_logon_id");
			//买家在支付宝的用户id
			String buyer_user_id=json.getString("buyer_user_id");
			//买家实付金额，单位为元，两位小数。该金额代表该笔交易买家实际支付的金额，不包含商户折扣等金额
			String buyer_pay_amount=json.getString("buyer_pay_amount");
			//买家用户类型。CORPORATE:企业用户；PRIVATE:个人用户。
			String buyer_user_type=json.getString("buyer_user_type");
			//交易中用户支付的可开具发票的金额，单位为元，两位小数。该金额代表该笔交易中可以给用户开具发票的金额
			String invoice_amount=json.getString("invoice_amount");
			//商家订单号
			String out_trade_no1=json.getString("out_trade_no");
			//积分支付的金额，单位为元，两位小数。该金额代表该笔交易中用户使用积分支付的金额，比如集分宝或者支付宝实时优惠等
			String point_amount=json.getString("point_amount");
			//实收金额，单位为元，两位小数。该金额为本笔交易，商户账户能够实际收到的金额
			String receipt_amount=json.getString("receipt_amount");
			//本次交易打款给卖家的时间
			String send_pay_date=json.getString("send_pay_date");
			//交易的订单金额，单位为元，两位小数。该参数的值为支付时传入的total_amount
			String total_amount=json.getString("total_amount");
			//支付宝交易号
			String trade_no1=json.getString("trade_no");
			//交易状态：WAIT_BUYER_PAY（交易创建，等待买家付款）、TRADE_CLOSED（未付款交易超时关闭，或支付完成后全额退款）、TRADE_SUCCESS（交易支付成功）、TRADE_FINISHED（交易结束，不可退款）
			String trade_status=json.getString("trade_status");
			
			String message="买家账号:"+buyer_logon_id
					+"\r\n<br/>买家实付金额:"+buyer_pay_amount
					+"\r\n<br/>商家订单号:"+
					out_trade_no1+"\r\n<br/>实收金额:"
					+receipt_amount+"\r\n<br/>本次交易打款给卖家的时间:"
					+send_pay_date+"\r\n<br/>交易的订单金额,该参数的值为支付时传入的total_amount:"
					+total_amount+"\r\n<br/>支付宝交易号:"+trade_no1;
			if(trade_status.equals("WAIT_BUYER_PAY")) {
				message+="\r\n<br/>交易状态:交易创建，等待买家付款";
			}
			if(trade_status.equals("TRADE_CLOSED")) {
				message+="\r\n<br/>交易状态:未付款交易超时关闭，或支付完成后全额退款";
			}
			if(trade_status.equals("TRADE_SUCCESS")) {
				message+="\r\n<br/>交易状态:交易支付成功";
			}
			if(trade_status.equals("TRADE_FINISHED")) {
				message+="\r\n<br/>交易状态:交易结束，不可退款";
			}
			System.out.println(message);
			request.setAttribute("result", message);
		}else {
			String sub_msg=json.getString("sub_msg");
			//sub_msg交易不存在
			request.setAttribute("result", sub_msg);
		}
		return "alipayTradeQuery";
	}
	
	/**
	 * 支付宝退款
	 * @return
	 * @throws AlipayApiException 
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping(value="/alipayTradeRefund",method=RequestMethod.POST)
	public String alipayTradeRefund(HttpServletRequest request) throws AlipayApiException, UnsupportedEncodingException {
		//获得初始化的AlipayClient
		AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfig.gatewayUrl, AlipayConfig.app_id, AlipayConfig.merchant_private_key, "json", AlipayConfig.charset, AlipayConfig.alipay_public_key, AlipayConfig.sign_type);
		
		//设置请求参数
		AlipayTradeRefundRequest alipayRequest = new AlipayTradeRefundRequest();
		
		//商户订单号，商户网站订单系统中唯一订单号
		String out_trade_no = new String(request.getParameter("WIDTRout_trade_no").getBytes("ISO-8859-1"),"UTF-8");
		//支付宝交易号
		String trade_no = new String(request.getParameter("WIDTRtrade_no").getBytes("ISO-8859-1"),"UTF-8");
		//请二选一设置
		//需要退款的金额，该金额不能大于订单金额，必填
		String refund_amount = new String(request.getParameter("WIDTRrefund_amount").getBytes("ISO-8859-1"),"UTF-8");
		//退款的原因说明
		String refund_reason = request.getParameter("WIDTRrefund_reason");
		//标识一次退款请求，同一笔交易多次退款需要保证唯一，如需部分退款，则此参数必传
		String out_request_no = new String(request.getParameter("WIDTRout_request_no").getBytes("ISO-8859-1"),"UTF-8");
		
		alipayRequest.setBizContent("{\"out_trade_no\":\""+ out_trade_no +"\"," 
				+ "\"trade_no\":\""+ trade_no +"\"," 
				+ "\"refund_amount\":\""+ refund_amount +"\"," 
				+ "\"refund_reason\":\""+ refund_reason +"\"," 
				+ "\"out_request_no\":\""+ out_request_no +"\"}");
		
		//请求
		String result = alipayClient.execute(alipayRequest).getBody();
		JSONObject jsonObject=JSONObject.parseObject(result);
		//json名称
		String alipay_trade_refund_response= jsonObject.get("alipay_trade_refund_response").toString();
		JSONObject json=JSONObject.parseObject(alipay_trade_refund_response);
		String code=json.getString("code");//10000是成功
		if(code.equals("10000")) {
			//查询成功
			//买家账号
			String buyer_logon_id=json.getString("buyer_logon_id");
			//买家在支付宝的用户id
			String buyer_user_id=json.getString("buyer_user_id");
			//本次退款是否发生了资金变化
			String fund_change=json.getString("fund_change");
			//退款总金额
			String refund_fee=json.getString("refund_fee");
			//退款支付时间
			String gmt_refund_pay=json.getString("gmt_refund_pay");
			//商家订单号
			String out_trade_no1=json.getString("out_trade_no");
			//本次退款金额中买家退款金额
			String present_refund_buyer_amount=json.getString("present_refund_buyer_amount");
			//商户实际退款给用户的金额，单位为元，支持两位小数
			String send_back_fee=json.getString("send_back_fee");
			//支付宝交易号
			String trade_no1=json.getString("trade_no");
			String message="买家账号:"+buyer_logon_id
					+"\r\n<br/>本次退款是否发生了资金变化:"+fund_change
					+"\r\n<br/>商家订单号:"+
					out_trade_no1+"\r\n<br/>退款总金额:"
					+refund_fee+"\r\n<br/>退款支付时间:"
					+gmt_refund_pay+"\r\n<br/>本次退款金额中买家退款金额:"
					+present_refund_buyer_amount+"\r\n<br/>支付宝交易号:"+trade_no1
					+"\r\n商户实际退款给用户的金额:"+send_back_fee
					;
			if(fund_change.equals("Y")) {
				//退款成功
				//修改商户订单表退款金额
				OrderEntity order=this.orderServiceFeign.findByOrderId(out_trade_no1);
				order.setWidtrrefundAmount(Double.parseDouble(refund_fee));
				order.setWidtrrefundReason("退款支付时间:"+gmt_refund_pay+" "+refund_reason);
				int upd=this.orderServiceFeign.updateOrderAll(order);
			}
			request.setAttribute("result", message);
		}else {
			String sub_msg=json.getString("sub_msg");
			//sub_msg交易不存在
			request.setAttribute("result", sub_msg);
		}
		return "alipayTradeRefund";//跳到退款成功界面
	}
	

	/**
	 * 支付宝退款查询
	 * @return
	 * @throws AlipayApiException 
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping(value="/alipayTradeFastpayRefundQuery",method=RequestMethod.POST)
	public String alipayTradeFastpayRefundQuery(HttpServletRequest request) throws AlipayApiException, UnsupportedEncodingException {
		//获得初始化的AlipayClient
		AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfig.gatewayUrl, AlipayConfig.app_id, AlipayConfig.merchant_private_key, "json", AlipayConfig.charset, AlipayConfig.alipay_public_key, AlipayConfig.sign_type);
		
		//设置请求参数
		AlipayTradeFastpayRefundQueryRequest alipayRequest = new AlipayTradeFastpayRefundQueryRequest();
		
		//商户订单号，商户网站订单系统中唯一订单号
		String out_trade_no = new String(request.getParameter("WIDRQout_trade_no").getBytes("ISO-8859-1"),"UTF-8");
		//支付宝交易号
		String trade_no = new String(request.getParameter("WIDRQtrade_no").getBytes("ISO-8859-1"),"UTF-8");
		//请二选一设置
		//请求退款接口时，传入的退款请求号，如果在退款请求时未传入，则该值为创建交易时的外部交易号，必填
		String out_request_no = new String(request.getParameter("WIDRQout_request_no").getBytes("ISO-8859-1"),"UTF-8");
		
		alipayRequest.setBizContent("{\"out_trade_no\":\""+ out_trade_no +"\"," 
				+"\"trade_no\":\""+ trade_no +"\","
				+"\"out_request_no\":\""+ out_request_no +"\"}");
		
		//请求
		String result = alipayClient.execute(alipayRequest).getBody();
		JSONObject jsonObject=JSONObject.parseObject(result);
		//json名称
		String alipay_trade_fastpay_refund_query_response= jsonObject.get("alipay_trade_fastpay_refund_query_response").toString();
		JSONObject json=JSONObject.parseObject(alipay_trade_fastpay_refund_query_response);
		String code=json.getString("code");//10000是成功
		if(code.equals("10000")) {
			request.setAttribute("result", "查询成功");
		}else {
			String sub_msg=json.getString("sub_msg");
			request.setAttribute("result", sub_msg);
		}
		return "alipayTradeFastpayRefundQuery";
	}
	
	/**
	 * 交易关闭  支付的时候未成功支付才能交易关闭
	 * @return
	 * @throws AlipayApiException 
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping(value="/alipayTradeClose",method=RequestMethod.POST)
	public String alipayTradeClose(HttpServletRequest request) throws AlipayApiException, UnsupportedEncodingException {
		//获得初始化的AlipayClient
		AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfig.gatewayUrl, AlipayConfig.app_id, AlipayConfig.merchant_private_key, "json", AlipayConfig.charset, AlipayConfig.alipay_public_key, AlipayConfig.sign_type);
		
		//设置请求参数
		AlipayTradeCloseRequest alipayRequest = new AlipayTradeCloseRequest();
		//商户订单号，商户网站订单系统中唯一订单号
		String out_trade_no = new String(request.getParameter("WIDTCout_trade_no").getBytes("ISO-8859-1"),"UTF-8");
		//支付宝交易号
		String trade_no = new String(request.getParameter("WIDTCtrade_no").getBytes("ISO-8859-1"),"UTF-8");
		//请二选一设置
		
		alipayRequest.setBizContent("{\"out_trade_no\":\""+ out_trade_no +"\"," +"\"trade_no\":\""+ trade_no +"\"}");
		
		//请求
		String result = alipayClient.execute(alipayRequest).getBody();
		JSONObject jsonObject=JSONObject.parseObject(result);
		//json名称
		String alipay_trade_close_response= jsonObject.get("alipay_trade_close_response").toString();
		JSONObject json=JSONObject.parseObject(alipay_trade_close_response);
		String code=json.getString("code");//10000是成功
		if(code.equals("10000")) {
			String out_trade_no1=json.getString("out_trade_no");
			String trade_no1=json.getString("trade_no");
			String msg="商户订单号:"+out_trade_no1+"<br/>支付宝交易号:"+trade_no1;
			request.setAttribute("result", msg);
		}else {
			String sub_msg=json.getString("sub_msg");
			request.setAttribute("result", sub_msg);
		}
		return "alipayTradeClose";
	}
}
