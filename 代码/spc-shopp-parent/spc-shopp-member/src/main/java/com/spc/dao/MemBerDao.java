package com.spc.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.spc.entity.UserEntity;



@Mapper
public interface MemBerDao {

	@Select("select  id,username,password,phone,email,created,updated from mb_user where id =#{userId}")
	UserEntity findByID(@Param("userId") Long userId);

	@Insert("INSERT  INTO `mb_user`  (username,password,phone,email,created,updated) VALUES (#{username}, #{password},#{phone},#{email},#{created},#{updated});")
	Integer insertUser(UserEntity userEntity);
	

	@Select("select  id,username,password,phone,email,created,updated from mb_user where username =#{username} and password =#{password}")
	UserEntity findByUser(@Param("username") String username,@Param("password")String password);
	
	@Select("select  id,username,password,phone,email,created,updated ,openid from mb_user where openid =#{openid}")
	UserEntity findByOpenIdUser(@Param("openid") String openid);
	@Update("update mb_user set openid=#{openid} where id=#{userId}")
    Integer updateByOpenIdUser(@Param("openid") String openid,@Param("userId") Integer userId);

	@Select("select id,username,password,phone,email,created,updated from mb_user")
	List<UserEntity> queryUserList();
	
	@Insert("INSERT  INTO `mb_user`  (id,username,password,phone,email,created,updated) VALUES (#{id},#{username}, #{password},#{phone},#{email},#{created},#{updated});")
	Integer insertUserAll(UserEntity userEntity);
	@Update("update mb_user set username =#{username},password=#{password},phone=#{phone},email=#{email},created=#{created},updated=#{updated} where id=#{id}")
    Integer updateUserAll(UserEntity userEntity);
	
	
	@Select("select  id,username,password,phone,email,created,updated,zfbid from mb_user where zfbid =#{zfbid}")
	UserEntity findUserByZfbid(@Param("zfbid") String zfbid);
	
	@Update("update mb_user set zfbid=#{zfbid} where id=#{userId}")
    Integer updateByZfbidUser(@Param("zfbid") String zfbid,@Param("userId") Integer userId);


	@Select("select  id,username,password,phone,email,created,updated,zfbid,sinaid from mb_user where sinaid =#{sinaid}")
	UserEntity findUserBySinaid(@Param("sinaid") String sinaid);
	
	@Update("update mb_user set sinaid=#{sinaid} where id=#{userId}")
    Integer updateBySinaidUser(@Param("sinaid") String sinaid,@Param("userId") Integer userId);

	
	
}
