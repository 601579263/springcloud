package com.spc.feign;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Component;

import com.spc.api.service.MemberService;
import com.spc.fallback.OrderFallback;
//需要调用服务名称 spring:application:name:service-member
@FeignClient("spc-shopp-member")
public interface MemBerServiceFeign extends MemberService{

}
