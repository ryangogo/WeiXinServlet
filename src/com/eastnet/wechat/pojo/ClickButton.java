package com.eastnet.wechat.pojo;

/**
 * click类型的菜单
 * 
 * @Project:  WeiXinServlet
 * @ClassName: ClickButton.java
 * @Company: JIT Northeast R & D 
 * @Description: TODO
 * @author  Administrator
 * @date  2017年4月10日
 */
public class ClickButton extends Button{
	
	private String key;//菜单KEY值，用于消息接口推送，不超过128字节
					//如果有两个相同的类型，如click，通过key来判断
	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}
	
	
	
	
}
