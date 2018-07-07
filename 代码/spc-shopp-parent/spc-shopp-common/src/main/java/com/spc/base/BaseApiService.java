package com.spc.base;

import com.spc.constants.Constants;

public class BaseApiService {
	public ResponseBase setResultError(Integer code,String msg) {
		return setResult(code, msg, null);
	}
	//返回失败 可传Msg值
	public ResponseBase setResultError(String msg) {
		return setResult(Constants.HTTP_RES_CODE_500,msg, null);
	}
	//返回成功 有data值
	public ResponseBase setResultSuccess(Object data) {
		return setResult(Constants.HTTP_RES_CODE_200, Constants.HTTP_RES_CODE_200_VALUE, data);
	}
	//返回成功 没有data值
	public ResponseBase setResultSuccess() {
		return setResult(Constants.HTTP_RES_CODE_200, Constants.HTTP_RES_CODE_200_VALUE, null);
	}
	//通用封装
	public ResponseBase setResult(Integer code, String msg, Object data) {
		return new ResponseBase(code, msg, data);
	}
	
	//返回成功 有msg值
	public ResponseBase setResultSuccess(String msg) {
		return setResult(Constants.HTTP_RES_CODE_200, msg,null);
	}
	
}
