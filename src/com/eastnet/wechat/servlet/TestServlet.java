package com.eastnet.wechat.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.eastnet.wechat.pojo.TextMessage;
import com.eastnet.wechat.utils.CheckUtil;
import com.eastnet.wechat.utils.MessageUtil;

public class TestServlet extends HttpServlet{
	/**
	 * doget方法是用来做验证的方法，在验证的时候需要从接口中获取几个数据，具体逻辑及方法参考代码
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String signature = request.getParameter("signature");
		String timestamp = request.getParameter("timestamp");
		String nonce = request.getParameter("nonce");
		String echostr = request.getParameter("echostr");
		
		PrintWriter out = response.getWriter();
		boolean a = CheckUtil.checkSignature(signature, timestamp, nonce);
		if(a){
			out.print(echostr);
		}
		
	}
	/**
	 * doPost方法主要用来做逻辑以及功能上的处理
	 * 接收到的信息是xml格式信息
	 * 返回的信息也要是xml格式信息
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		PrintWriter pw = response.getWriter();
		try{
			//以下到if之前的代码都是接收消息用的
			Map<String,String> map = MessageUtil.xmlToMap(request);
			String fromUserName = map.get("FromUserName");//发送方账号
			String toUserName = map.get("ToUserName");//开发者微型号
			String msgType = map.get("MsgType");//类型
			String content = map.get("Content");//内容
			//以下判断中的代码都是用来回复消息的
			//是否是文本消息
			String message = null;
			//接收到的消息类型是文本类型
			if(MessageUtil.MESSAGE_TEXT.equals(msgType)){
				if("1".equals(content)){
					message = MessageUtil.initText(toUserName,fromUserName,"这些是课程介绍");
				}else if("2".equals(content)){
					message = MessageUtil.initText(toUserName,fromUserName,"这些是本人介绍");
				}else if("?".equals(content) || "？".equals(content)){
					message = MessageUtil.initText(toUserName,fromUserName,"调出此菜单");
				}else if("3".equals(content)){
					message = MessageUtil.initNewsMessage(toUserName, fromUserName);
				}else if("4".equals(content)){
					message = MessageUtil.initImageMessage(toUserName, fromUserName);
				}else{
					message = MessageUtil.initText(toUserName,fromUserName,"测试自动回复功能数据");
				}
			//判断事件
			}else if(MessageUtil.MESSAGE_EVENT.equals(msgType)){
				String eventtype = map.get("Event");
				//如果事件为关注
				if(MessageUtil.MESSAGE_SUBSCRIBE.equals(eventtype)){
					message = MessageUtil.initText(toUserName,fromUserName,MessageUtil.menuText());
				}
			}else{
				message = MessageUtil.initText(toUserName,fromUserName,"请正确操作");
			}
			System.out.println(message);
			pw.print(message);
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			pw.close();
		}
		
	}
}
