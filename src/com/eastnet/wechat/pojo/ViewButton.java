package com.eastnet.wechat.pojo;
/**
 * view类型的菜单
 * 
 * @Project:  WeiXinServlet
 * @ClassName: ViewButton.java
 * @Company: JIT Northeast R & D 
 * @Description: TODO
 * @author  Administrator
 * @date  2017年4月10日
 */
public class ViewButton extends Button{

	private String url;//网页链接，用户点击菜单可打开链接，不超过1024字节。t
	                   //ype为miniprogram时，不支持小程序的老版本客户端将打开本url。

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	
	
}
