package com.spc.entity;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;
/**
 * 用户实体类
 * @author 60157
 *
 */
@Getter
@Setter
public class UserEntity {

	private Integer id;
	private String username;
	private String password;
	private String phone;
	private String email;
	private Date created;
	private Date updated;
	private String openid;//qq登陆唯一id
	private String zfbid;//支付用户登陆账号id
	private String sinaid;//新浪微博用户UIDid
}