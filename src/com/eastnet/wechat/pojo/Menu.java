package com.eastnet.wechat.pojo;

/**
 * 各种各样的button的总的封装类
 * 
 * @Project:  WeiXinServlet
 * @ClassName: Menu.java
 * @Company: JIT Northeast R & D 
 * @Description: TODO
 * @author  Administrator
 * @date  2017年4月10日
 */
public class Menu {

	private Button[] button;//一级菜单数组，个数应为1~3个

	public Button[] getButton() {
		return button;
	}

	public void setButton(Button[] button) {
		this.button = button;
	}
	
	//官方 json demo
	/**
	 * {
	    "button": [
	        {
	            "name": "扫码", 
	            "sub_button": [
	                {
	                    "type": "scancode_waitmsg", 
	                    "name": "扫码带提示", 
	                    "key": "rselfmenu_0_0", 
	                    "sub_button": [ ]
	                }, 
	                {
	                    "type": "scancode_push", 
	                    "name": "扫码推事件", 
	                    "key": "rselfmenu_0_1", 
	                    "sub_button": [ ]
	                }
	            ]
	        }, 
	        {
	            "name": "发图", 
	            "sub_button": [
	                {
	                    "type": "pic_sysphoto", 
	                    "name": "系统拍照发图", 
	                    "key": "rselfmenu_1_0", 
	                   "sub_button": [ ]
	                 }, 
	                {
	                    "type": "pic_photo_or_album", 
	                    "name": "拍照或者相册发图", 
	                    "key": "rselfmenu_1_1", 
	                    "sub_button": [ ]
	                }, 
	                {
	                    "type": "pic_weixin", 
	                    "name": "微信相册发图", 
	                    "key": "rselfmenu_1_2", 
	                    "sub_button": [ ]
	                }
	            ]
	        }, 
	        {
	            "name": "发送位置", 
	            "type": "location_select", 
	            "key": "rselfmenu_2_0"
	        },
	        {
	           "type": "media_id", 
	           "name": "图片", 
	           "media_id": "MEDIA_ID1"
	        }, 
	        {
	           "type": "view_limited", 
	           "name": "图文消息", 
	           "media_id": "MEDIA_ID2"
	        }
	    ]
	}
	 */
}
