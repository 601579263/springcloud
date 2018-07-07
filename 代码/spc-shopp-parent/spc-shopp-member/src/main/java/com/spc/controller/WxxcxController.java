package com.spc.controller;

import java.io.File;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * 微信小程序上传图片调用接口
 * @author 60157
 *
 */
@RequestMapping("/wxxcx")
@RestController
public class WxxcxController {

	@RequestMapping(value="/test")
	public String test(HttpServletRequest request,
            @RequestParam("file") MultipartFile file) throws IllegalStateException, IOException {
	        //如果文件不为空，写入上传路径
	        if(!file.isEmpty()) {
	            //上传文件路径
	            String path = request.getServletContext().getRealPath("/images/");
	            System.out.println("path="+path);
	            //上传文件名
	            String filename = file.getOriginalFilename();
	            System.out.println("filename="+filename);
	            File filepath = new File(path,filename);
	            //判断路径是否存在，如果不存在就创建一个
	            if (!filepath.getParentFile().exists()) { 
	                filepath.getParentFile().mkdirs();
	            }
	            //将上传文件保存到一个目标文件当中
	            file.transferTo(new File(path + File.separator + filename));
	            return filename;
	        } else {
	            return "error";
	        }
	}
}
