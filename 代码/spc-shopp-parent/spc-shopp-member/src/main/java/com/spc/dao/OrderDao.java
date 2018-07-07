package com.spc.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.spc.entity.OrderEntity;
import com.spc.entity.UserEntity;


@Mapper
public interface OrderDao {

	@Insert("INSERT  INTO `mb_order`  (`WIDout_trade_no`,\r\n" + 
			"	`WIDsubject`,\r\n" + 
			"	`WIDtotal_amount`,\r\n" + 
			"	`WIDbody`,\r\n" + 
			"	`WIDTQtrade_no`,\r\n" + 
			"	`WIDTRrefund_amount`,\r\n" + 
			"	`WIDTRrefund_reason`,\r\n" + 
			"	`WIDTRout_request_no`) VALUES (#{widoutTradeNo},#{widsubject}, #{widtotalAmount},#{widbody},#{widtqtradeNo},#{widtrrefundAmount},#{widtrrefundReason},#{widtroutRequestNo})")
	Integer insertOrderAll(OrderEntity orderEntity);
	
	@Update("update mb_order set WIDsubject =#{widsubject},WIDTQtrade_no=#{widtqtradeNo},WIDTRrefund_amount=#{widtrrefundAmount},WIDTRrefund_reason=#{widtrrefundReason},WIDTRout_request_no=#{widtroutRequestNo} where WIDout_trade_no=#{widoutTradeNo}")
    Integer updateOrderAll(OrderEntity orderEntity);
	
	@Select("select WIDout_trade_no widoutTradeNo,WIDsubject,WIDtotal_amount widtotalAmount,WIDbody,WIDTQtrade_no widtqtradeNo,WIDTRrefund_amount widtrrefundAmount,WIDTRrefund_reason widtrrefundReason,WIDTRout_request_no widtroutRequestNo from mb_order")
	List<OrderEntity> queryOrderList();
	
	
	@Select("select WIDout_trade_no widoutTradeNo,WIDsubject,WIDtotal_amount widtotalAmount,WIDbody,WIDTQtrade_no widtqtradeNo,WIDTRrefund_amount widtrrefundAmount,WIDTRrefund_reason widtrrefundReason,WIDTRout_request_no widtroutRequestNo from mb_order where WIDout_trade_no =#{widoutTradeNo}")
	OrderEntity findByID(@Param("widoutTradeNo") String widoutTradeNo);
}
