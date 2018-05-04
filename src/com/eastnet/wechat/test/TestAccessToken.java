package com.eastnet.wechat.test;

import com.eastnet.wechat.pojo.AccessToken;
import com.eastnet.wechat.utils.WeiXinUtil;

import net.sf.json.JSONObject;

public class TestAccessToken {
	
	public static void main(String[] args){
		AccessToken token = WeiXinUtil.getAccessToken();
		/*System.out.println("access_token是：" + acc.getToken());
		System.out.println("expiresIn是：" + acc.getExpiresIn());*/
		
		/*String path = "D:/qita.jpg";
		try {
			String media_id = WeiXinUtil.upload(path, token.getToken(), "image");
			System.out.println(media_id);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		
		String menu = JSONObject.fromObject(WeiXinUtil.initMenu()).toString();
		int result = WeiXinUtil.createMenu(token.getToken(), menu);
		if(result == 0){
			System.out.println("创建菜单成功");
		}else{
			System.out.println(result);
		}
		
	}
}
