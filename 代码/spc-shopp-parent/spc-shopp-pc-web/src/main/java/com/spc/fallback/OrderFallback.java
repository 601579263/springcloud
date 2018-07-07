package com.spc.fallback;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.spc.base.BaseApiService;
import com.spc.base.ResponseBase;
import com.spc.entity.OrderEntity;
import com.spc.entity.UserEntity;
import com.spc.feign.MemBerServiceFeign;
import com.spc.feign.OrderServiceFeign;


/**
 * 服务降级  返回内容随你定
 * @author 60157
 *
 */
@Component //注册到容器里面去
public class OrderFallback  implements OrderServiceFeign {


	@Override
	public Integer insertOrderAll( OrderEntity orderEntity) {
		OrderEntity or=new OrderEntity();
		return 0;
	}
	
	@Override
	public Integer updateOrderAll( OrderEntity orderEntity) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<OrderEntity> queryOrderList() {
		List<OrderEntity> list=new ArrayList<OrderEntity>();
		OrderEntity or=new OrderEntity();
		or.setWidbody("数据异常");
		or.setWidoutTradeNo("数据异常");
		or.setWidsubject("数据异常");
		or.setWidtotalAmount(0.0);
		or.setWidtqtradeNo("数据异常");
		or.setWidtrrefundAmount(0.0);
		or.setWidtrrefundReason("数据异常");
		or.setWidtroutRequestNo("数据异常");
		list.add(or);
		return list;
	}
	
	
	@Override
	public OrderEntity findByOrderId(String widoutTradeNo) {
		OrderEntity or=new OrderEntity();
		or.setWidbody("");
		or.setWidoutTradeNo("");
		or.setWidsubject("");
		or.setWidtotalAmount(0.0);
		or.setWidtqtradeNo("");
		or.setWidtrrefundAmount(0.0);
		or.setWidtrrefundReason("");
		return or;
	}

	

}
