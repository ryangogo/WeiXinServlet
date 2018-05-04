package com.eastnet.wechat.pojo;
/**
 * 文本消息
 * 微信后台传过来的数据是一个xml类型的数据
 * 下面几个属性分别为对应的几个节点
 * 
 * @Project:  WeiXinServlet
 * @ClassName: TextMessage.java
 * @Company: JIT Northeast R & D 
 * @Description: TODO
 * @author  cong_jin
 * @date  2017年4月9日
 */
public class TextMessage extends BaseMessage{
	 
	private String Content;//文本消息内容
	private String MsgId;//消息id，64位整数
	
	public String getContent() {
		return Content;
	}
	public void setContent(String content) {
		Content = content;
	}
	public String getMsgId() {
		return MsgId;
	}
	public void setMsgId(String msgId) {
		MsgId = msgId;
	}
	
	
}