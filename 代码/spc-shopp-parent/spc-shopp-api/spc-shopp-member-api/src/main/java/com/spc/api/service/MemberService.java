package com.spc.api.service;

import java.util.List;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.spc.base.ResponseBase;
import com.spc.entity.UserEntity;
@RequestMapping("/member")
public interface MemberService {

	//使用userid查询用户信息
	@RequestMapping("/findUserById")
	public ResponseBase findUserById(@RequestParam("userId")Long userId);
	//注册接口
	@RequestMapping("/registUser")
	public ResponseBase registUser(@RequestBody UserEntity entity);
	//登陆
	@RequestMapping("/login")
	public ResponseBase login(@RequestBody UserEntity entity);
	//使用token查询
	@RequestMapping("/findByToken")
	public ResponseBase findByToken(@RequestParam("token") String token);
	//使用openid查询用户信息
	@RequestMapping("/findByOpenIdUser")
	public ResponseBase findByOpenIdUser(@RequestParam("openid") String openid);
	//使用qq登陆
	@RequestMapping("/qqLogin")
	public ResponseBase qqLogin(@RequestBody UserEntity user);
	//查询所有用户信息
	@RequestMapping("/queryUserList")
	public List<UserEntity> queryUserList();
	//增加所有
	@RequestMapping("/insertUserAll")
	Integer insertUserAll(@RequestBody UserEntity userEntity);
	//修改所有
	@RequestMapping("/updateUserAll")
	Integer updateUserAll(@RequestBody UserEntity userEntity);

	//使用zfbid查询用户信息
	@RequestMapping("/findUserByZfbid")
	public UserEntity findUserByZfbid(@RequestParam("zfbid")String zfbid);
	//使用支付宝登陆
	@RequestMapping("/zfbLogin")
	public ResponseBase zfbLogin(@RequestBody UserEntity user);
	
	//使用sinaid查询用户信息
	@RequestMapping("/findUserBySinaid")
	public UserEntity findUserBySinaid(@RequestParam("sinaid")String sinaid);
	//使用新浪微博登陆
	@RequestMapping("/sinaLogin")
	public ResponseBase sinaLogin(@RequestBody UserEntity user);
		
}
