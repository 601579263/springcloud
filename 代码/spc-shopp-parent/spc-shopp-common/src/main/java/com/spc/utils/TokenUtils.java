package com.spc.utils;

import java.util.UUID;

import com.spc.constants.Constants;

public class TokenUtils {

	public static String getMemberToken() {
		return Constants.TOKEN_MEMBER+"-"+UUID.randomUUID();
	}
}
