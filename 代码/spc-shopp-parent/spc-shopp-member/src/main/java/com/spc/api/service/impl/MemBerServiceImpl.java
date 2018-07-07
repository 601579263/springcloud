package com.spc.api.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.activemq.command.ActiveMQMessage;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.spc.api.service.MemberService;
import com.spc.base.BaseApiService;
import com.spc.base.BaseRedisService;
import com.spc.base.ResponseBase;
import com.spc.constants.Constants;
import com.spc.dao.MemBerDao;
import com.spc.entity.UserEntity;
import com.spc.mq.RegisterMailboxPoducer;
import com.spc.utils.MD5Util;
import com.spc.utils.TokenUtils;

import lombok.extern.slf4j.Slf4j;
@Slf4j
@RestController
public class MemBerServiceImpl extends BaseApiService implements MemberService{

	@Autowired
	private MemBerDao memBerDao;
	@Autowired
	private RegisterMailboxPoducer registerMailboxPoducer;
	@Value("${messages.queue}")
	private String MESSAGESQUEUE;//消息队列名称
	@Autowired
	private BaseRedisService baseRedisService;
	
	@Override
	public ResponseBase findUserById(@RequestParam("userId") Long userId) {
		UserEntity user=this.memBerDao.findByID(userId);
		if(user==null) {
			return super.setResultError("获取数据为空");
		}
		return super.setResultSuccess(user);
	}
	
	

	@Override
	public ResponseBase registUser(@RequestBody UserEntity entity) {
		String password=MD5Util.MD5(entity.getPassword());
		entity.setPassword(password);
		entity.setCreated(new Date());
		entity.setUpdated(new Date());
		int count=this.memBerDao.insertUser(entity);
		if(count==0) {
			return super.setResultError("注册失败");
		}
		//采用异步方式发送消息
		String email=entity.getEmail();
		String json=this.emailJson(email);
		this.senMsg(json);
		log.info("=====会员服务推送消息到消息服务平台==========json:===="+json);
		return super.setResultSuccess("用户注册成功");
	}
	
	private String emailJson(String email) {
		JSONObject rootJson=new JSONObject();
		JSONObject headerJson=new JSONObject();
		headerJson.put("interfaceType", Constants.MSG_EMAIL);
		JSONObject content=new JSONObject();
		content.put("email", email);
		rootJson.put("header", headerJson);
		rootJson.put("content", content);
		return rootJson.toJSONString();
	}
	
	public void senMsg(String json) {
		ActiveMQQueue activeMQQueue=new ActiveMQQueue(MESSAGESQUEUE);
		registerMailboxPoducer.sendMsg(activeMQQueue, json);
	}



	@Override
	public ResponseBase login(@RequestBody UserEntity entity) {
		//1.验证参数
		String username=entity.getUsername();
		String password=entity.getPassword();
		if(StringUtils.isEmpty(username)) {
			return super.setResultError("用户名不能为空");
		}
		if(StringUtils.isEmpty(password)) {
			return super.setResultError("密码不能为空");
		}
		String newPassWord=MD5Util.MD5(password);//加密
		//2.数据库查找账户密码是否正确
		UserEntity user=this.memBerDao.findByUser(username, newPassWord);
		if(user==null) {
			return super.setResultError("账号密码不正确");
		}
		//3.如果账号密码正确,对应生成token
		String token=TokenUtils.getMemberToken();
		//4.存放redis中 ,key为token, value为userid
		int userid=user.getId();
		baseRedisService.setString(token, userid+"", Constants.TOKEN_MEMBER_TIME);
		//5.直接返回token
		JSONObject jsonObject=new JSONObject();
		jsonObject.put("token", token);
		return super.setResultSuccess(jsonObject);
	}



	@Override
	public ResponseBase findByToken(@RequestParam("token") String token) {
		//1.验证参数
		if(StringUtils.isEmpty(token)) {
			return super.setResultError("token不能为空");
		}
		
		//2.从redis	使用token 查找对应的userid
		String userid=(String) baseRedisService.getString(token);
		if(StringUtils.isEmpty(userid)) {
			return super.setResultError("token无效或者已过期！");
		}
		//3.使用Userid数据库查询用户信息返回给客户端
		Long userId=Long.parseLong(userid);
		UserEntity user=memBerDao.findByID(userId);
		if(user==null) {
			return super.setResultError("未查找到该用户信息");
		}
		user.setPassword("");//防止显示用户密码
		return super.setResultSuccess(user);
	}



	@Override
	public ResponseBase findByOpenIdUser(@RequestParam("openid")String openid) {
		//1.验证参数
		if(StringUtils.isEmpty(openid)) {
			return super.setResultError("openid不能为空");
		}
		//2.查询数据库
		UserEntity user=this.memBerDao.findByOpenIdUser(openid);
		if(user==null) {
			return super.setResultError(Constants.HTTP_RES_CODE_201,"该openid没有关联");
		}
		//3.如果账号密码正确 对应生成token
		String token=TokenUtils.getMemberToken();
		//4.存放redis中 key为token value为userid
		int userid=user.getId();
		baseRedisService.setString(token, userid+"", Constants.TOKEN_MEMBER_TIME);
		//5.直接返回json
		JSONObject jsonObject=new JSONObject();
		jsonObject.put("token",token);
		return super.setResultSuccess(jsonObject);
	}



	@Override
	public ResponseBase qqLogin(@RequestBody UserEntity user) {
		//1.验证参数
		String openId=user.getOpenid();
		if(StringUtils.isEmpty(openId)) {
			return super.setResultError("opernid不能为空");
		}
		String username = user.getUsername();
		if (StringUtils.isEmpty(username)) {
			return setResultError("用戶名称不能为空!");
		}
		String password = user.getPassword();
		if (StringUtils.isEmpty(password)) {
			return setResultError("密码不能为空!");
		}
		//2.数据库查找账号密码是否正确
		String pwd=MD5Util.MD5(password);
		UserEntity en=this.memBerDao.findByUser(username, pwd);
		if(en==null) {
			return super.setResultError("账号或者密码不正确");
		}
		//3.如果账号密码正确,生成对应token
		String token=TokenUtils.getMemberToken();
		//4.存放到redis中
		int userid=en.getId();
		baseRedisService.setString(token, userid+"", Constants.TOKEN_MEMBER_TIME);
		//5.直接返回token
		JSONObject jsonObject=new JSONObject();
		jsonObject.put("token", token);
		ResponseBase base=super.setResultSuccess(jsonObject);
		//如果code不是200 return
		if(!base.getCode().equals(Constants.HTTP_RES_CODE_200)) {
			return base;
		}
		//6.自动登陆
		ResponseBase resp=this.findByToken(token);
		if(!resp.getCode().equals(Constants.HTTP_RES_CODE_200)) {
			return resp;
		}
		UserEntity us=(UserEntity) resp.getData();
		//修改用户openid
		int uid=us.getId();
		int upd=this.memBerDao.updateByOpenIdUser(openId, uid);
		if(upd<=0) {
			return super.setResultError("qq账号管理失败");
		}
		return base;
		
	}



	@Override
	public List<UserEntity> queryUserList() {
		// TODO Auto-generated method stub
		return this.memBerDao.queryUserList();
	}



	@Override
	public Integer insertUserAll(@RequestBody UserEntity userEntity) {
		// TODO Auto-generated method stub
		return this.memBerDao.insertUserAll(userEntity);
	}



	@Override
	public Integer updateUserAll(@RequestBody UserEntity userEntity) {
		// TODO Auto-generated method stub
		return this.memBerDao.updateUserAll(userEntity);
	}



	@Override
	public UserEntity findUserByZfbid(@RequestParam("zfbid") String zfbid) {
		// TODO Auto-generated method stub
		return this.memBerDao.findUserByZfbid(zfbid);
	}



	@Override
	public ResponseBase zfbLogin(@RequestBody UserEntity user) {
		//1.验证参数
		String zfbId=user.getZfbid();
		if(StringUtils.isEmpty(zfbId)) {
			return super.setResultError("zfbId不能为空");
		}
		String username = user.getUsername();
		if (StringUtils.isEmpty(username)) {
			return setResultError("用戶名称不能为空!");
		}
		String password = user.getPassword();
		if (StringUtils.isEmpty(password)) {
			return setResultError("密码不能为空!");
		}
		//2.数据库查找账号密码是否正确
		String pwd=MD5Util.MD5(password);
		UserEntity en=this.memBerDao.findByUser(username, pwd);
		if(en==null) {
			return super.setResultError("账号或者密码不正确");
		}
		//修改用户支付宝id
		int uid=en.getId();
		int upd=this.memBerDao.updateByZfbidUser(zfbId, uid);
		if(upd<=0) {
			return super.setResultError("支付宝账号管理失败");
		}
		return super.setResultSuccess();
	}



	@Override
	public UserEntity findUserBySinaid(@RequestParam("sinaid") String sinaid) {
		// TODO Auto-generated method stub
		return this.memBerDao.findUserBySinaid(sinaid);
	}



	@Override
	public ResponseBase sinaLogin(@RequestBody UserEntity user) {
		//1.验证参数
		String sinaid=user.getSinaid();
		if(StringUtils.isEmpty(sinaid)) {
			return super.setResultError("sinaid不能为空");
		}
		String username = user.getUsername();
		if (StringUtils.isEmpty(username)) {
			return setResultError("用戶名称不能为空!");
		}
		String password = user.getPassword();
		if (StringUtils.isEmpty(password)) {
			return setResultError("密码不能为空!");
		}
		//2.数据库查找账号密码是否正确
		String pwd=MD5Util.MD5(password);
		UserEntity en=this.memBerDao.findByUser(username, pwd);
		if(en==null) {
			return super.setResultError("账号或者密码不正确");
		}
		//修改用户新浪微博id
		int uid=en.getId();
		int upd=this.memBerDao.updateBySinaidUser(sinaid, uid);
		if(upd<=0) {
			return super.setResultError("新浪微博管理失败");
		}
		return super.setResultSuccess();
	}

}
