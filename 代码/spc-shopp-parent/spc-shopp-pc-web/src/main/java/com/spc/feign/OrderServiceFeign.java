package com.spc.feign;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.spc.api.service.OrderService;
import com.spc.entity.OrderEntity;
import com.spc.fallback.OrderFallback;
@FeignClient(value="spc-shopp-member",fallback=OrderFallback.class)
public interface OrderServiceFeign{
	//增加所有
	@RequestMapping("/order/insertOrderAll")
	Integer insertOrderAll(@RequestBody OrderEntity orderEntity);
	
	//修改所有
	@RequestMapping("/order/updateOrderAll")
	Integer updateOrderAll(@RequestBody OrderEntity orderEntity);
	//查询所有
	@RequestMapping("/order/queryOrderList")
	List<OrderEntity> queryOrderList();
	//查询单个对象
	@RequestMapping("/order/findByOrderId")
	OrderEntity findByOrderId(@RequestParam("widoutTradeNo") String widoutTradeNo);
}
