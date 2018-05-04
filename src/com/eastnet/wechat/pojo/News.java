package com.eastnet.wechat.pojo;
/**
 * 图文消息
 * 
 * @Project:  WeiXinServlet
 * @ClassName: News.java
 * @Company: JIT Northeast R & D 
 * @Description: TODO
 * @author  cong_jin
 * @date  2017年4月10日
 */
public class News {
	
	private String Title;//图文消息标题 
	private String Description;//图文消息描述 
	private String PicUrl;//图片链接，支持JPG、PNG格式，较好的效果为大图360*200，小图200*200 
	private String Url;//点击图文消息跳转链接 
	public String getTitle() {
		return Title;
	}
	public void setTitle(String title) {
		Title = title;
	}
	public String getDescription() {
		return Description;
	}
	public void setDescription(String description) {
		Description = description;
	}
	public String getPicUrl() {
		return PicUrl;
	}
	public void setPicUrl(String picUrl) {
		PicUrl = picUrl;
	}
	public String getUrl() {
		return Url;
	}
	public void setUrl(String url) {
		Url = url;
	}
	
	
}
