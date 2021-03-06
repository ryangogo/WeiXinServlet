package com.eastnet.wechat.pojo;

/**
 * 父类button 具有button的属性
 * 
 * @Project:  WeiXinServlet
 * @ClassName: Button.java
 * @Company: JIT Northeast R & D 
 * @Description: TODO
 * @author  cong_jin
 * @date  2017年4月10日
 */
//一级菜单数组，个数应为1~3个
public class Button {

	private String name;//菜单标题，不超过16个字节，子菜单不超过60个字节
	private String type;//菜单的响应动作类型，view表示网页类型，click表示点击类型，miniprogram表示小程序类型
	private Button[] sub_button;//二级菜单数组，个数应为1~5个
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Button[] getSub_button() {
		return sub_button;
	}
	public void setSub_button(Button[] sub_button) {
		this.sub_button = sub_button;
	}
	
	
	
}
