package com.spc.api.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.spc.entity.OrderEntity;
import com.spc.entity.UserEntity;

@RequestMapping("/order")
public interface OrderService {

	//增加所有
	@RequestMapping("/insertOrderAll")
	Integer insertOrderAll(@RequestBody OrderEntity orderEntity);
	
	//修改所有
	@RequestMapping("/updateOrderAll")
	Integer updateOrderAll(@RequestBody OrderEntity orderEntity);
	//查询所有
	@RequestMapping("/queryOrderList")
	List<OrderEntity> queryOrderList();
	//查询单个对象
	@RequestMapping("/findByOrderId")
	OrderEntity findByOrderId(@RequestParam("widoutTradeNo") String widoutTradeNo);
	
}
