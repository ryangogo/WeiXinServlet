package com.eastnet.wechat.utils;

import java.util.Arrays;

public class CheckUtil {
	
	private static final String token = "imooc";
	
	//进行校验
	public static boolean checkSignature(String signature,String timestamp,String nonce){
		return Sha1Util.checkSignature(signature, timestamp, nonce);
	}
}
