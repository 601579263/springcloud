package com.spc.entity;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;
/**
 * 支付测试订单类
 * @author 60157
 *
 */
@Getter
@Setter
public class OrderEntity {

	private String widoutTradeNo;//商户订单号主键

    private String widsubject;//订单名称

    private Double widtotalAmount;//付款金额

    private String widbody;//商品描述

    private String widtqtradeNo;//支付宝交易号

    private Double widtrrefundAmount;//退款金额

    private String widtrrefundReason;//退款原因

    private String widtroutRequestNo;//退款请求号
}
