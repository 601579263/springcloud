package com.spc.api.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.spc.api.service.OrderService;
import com.spc.base.ArchivesLog;
import com.spc.dao.OrderDao;
import com.spc.entity.OrderEntity;

import lombok.extern.slf4j.Slf4j;
@Slf4j
@RestController
public class OrderServiceImpl implements OrderService {
	@Autowired
	private OrderDao orderDao;

	@Override
	@ArchivesLog(operationType="增加商户订单",operationName="增加商户订单")
	@Transactional
	public Integer insertOrderAll(@RequestBody OrderEntity orderEntity) {
		return this.orderDao.insertOrderAll(orderEntity);
	}

	
	@Override
	@ArchivesLog(operationType="修改商户订单",operationName="修改商户订单")
	@Transactional
	public Integer updateOrderAll(@RequestBody OrderEntity orderEntity) {
		// TODO Auto-generated method stub
		return this.orderDao.updateOrderAll(orderEntity);
	}

	@Override
	@ArchivesLog(operationType="查询商户订单",operationName="查询商户订单")
	@Transactional
	public List<OrderEntity> queryOrderList() {
		// TODO Auto-generated method stub
		return this.orderDao.queryOrderList();
	}


	@Override
	public OrderEntity findByOrderId(@RequestParam("widoutTradeNo") String widoutTradeNo) {
		// TODO Auto-generated method stub
		return this.orderDao.findByID(widoutTradeNo);
	}

}
