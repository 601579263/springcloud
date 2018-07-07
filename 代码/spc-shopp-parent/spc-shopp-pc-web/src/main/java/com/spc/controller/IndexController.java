package com.spc.controller;

import java.util.LinkedHashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.spc.base.ResponseBase;
import com.spc.constants.Constants;
import com.spc.entity.UserEntity;
import com.spc.feign.MemBerServiceFeign;
import com.spc.utils.CookieUtil;
import com.spc.utils.ExportExcel;
import com.spc.utils.ImportExcel;

@Controller
public class IndexController {

	private static final String INDEX="index";
	@Autowired
	private MemBerServiceFeign memBerServiceFeign;
	
	/**
	 * 主页
	 * @return
	 */
	@RequestMapping(value="/",method=RequestMethod.GET)
	public String index(HttpServletRequest request) {
		//1.从cookie中获取token信息
		String token=CookieUtil.getUid(request,Constants.COOKIE_MEMBER_TOKEN);
		//2.如果cookie存在token,调用会员服务接口,使用token查询用户信息
		if(!StringUtils.isEmpty(token)) {
			ResponseBase base=memBerServiceFeign.findByToken(token);
			if(base.getCode().equals(Constants.HTTP_RES_CODE_200)) {
				LinkedHashMap linkedHashMap=(LinkedHashMap) base.getData();
				String username=(String) linkedHashMap.get("username");
				request.setAttribute("username",username);
			}
		}
		return INDEX;
	}
	
	/**
	 * 使用导出工具类,把数据库数据导出到E盘的book.xls文件中：
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/save")
	public String save() throws Exception
	{
	    String sheetName="用户列表";//sheet页名称
	    String titleName="用户标题";//标题名称
	    String[] headers = { "用户id", "用户名称", "密码", "电话号码", "邮箱", "创建时间","修改时间"};
	    List<UserEntity> dataSet = memBerServiceFeign.queryUserList();
	    String resultUrl="D:\\book.xls";
	    String pattern="yyyy-MM-dd HH:mm:ss";
	    ExportExcel.exportExcel(sheetName, titleName, headers, dataSet, resultUrl, pattern);
	    return "success";
	}
	
	/**
	 * 使用导入工具类,把E盘的book.xls文件中的数据导入到数据库中：
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/importExcel")
	public String importExcel() throws Exception
	{
	    String originUrl="D:\\book.xls";
	    int startRow=2;
	    int endRow=0;
	    List<UserEntity>  userList = (List<UserEntity>) ImportExcel.importExcel(originUrl, startRow, endRow, UserEntity.class);
	    for (UserEntity user : userList) {
	        //service.insertBook(book);
	        //根据id判断是否存在 如果存在修改数据 如果不存在增加
	    	Long id=Long.parseLong(user.getId()+"");
	    	ResponseBase response=this.memBerServiceFeign.findUserById(id);
	    	if(response.getCode().equals(Constants.HTTP_RES_CODE_200)) {
	    		//查询到了
	    		int upd=this.memBerServiceFeign.updateUserAll(user);
	    	}else {
	    		int add= memBerServiceFeign.insertUserAll(user);
	    	}
	       
	    }

	    return "success";
	}
}
