package com.eastnet.wechat.utils;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.eastnet.wechat.pojo.Image;
import com.eastnet.wechat.pojo.ImageMessage;
import com.eastnet.wechat.pojo.News;
import com.eastnet.wechat.pojo.NewsMessage;
import com.eastnet.wechat.pojo.TextMessage;
import com.thoughtworks.xstream.XStream;


public class MessageUtil {
	
	//文本消息-text 图片消息-image 语音消息-voice 图文消息-news
	//视频消息-video 链接消息-link 地理位置消息-location
	//事件推送-event 
	   //关注-subscribe 取消关注-unsubscribe 菜单点击-CLICK、VIEW
	public static final String MESSAGE_TEXT = "text";
	public static final String MESSAGE_NEWS = "news";
	public static final String MESSAGE_IMAGE = "image";
	public static final String MESSAGE_VOICE = "voice";
	public static final String MESSAGE_VIDEO = "video";
	public static final String MESSAGE_LINK = "link";
	public static final String MESSAGE_LOCATION = "location";
	public static final String MESSAGE_EVENT = "event";
	public static final String MESSAGE_SUBSCRIBE = "subscribe";
	public static final String MESSAGE_UNSUBSCRIBE = "unsubscribe";
	public static final String MESSAGE_CLICK = "CLICK";
	public static final String MESSAGE_VIEW = "VIEW";
	
	/**
	 * 将接收到的信息xml转换成map
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public static Map<String,String> xmlToMap(HttpServletRequest request) 
			throws Exception{
		Map<String,String> map = new HashMap<String,String>();
		SAXReader saxReader = new SAXReader();
		InputStream in = request.getInputStream();
		Document doc = saxReader.read(in);
		Element root  = doc.getRootElement();
		List<Element> list = root.elements();
		for(Element element : list){
			map.put(element.getName(),element.getText());
		}
		in.close();
		return map;
	}
	/**
	 * 将文本消息转换成xml
	 * @param textMessage
	 * @return
	 */
	public static String textMessageToXml(TextMessage textMessage){
		XStream xStream = new XStream();
		//将xml的根节点替换为“xml”
		xStream.alias("xml",textMessage.getClass());
		return xStream.toXML(textMessage);
	}
	
	/**
	 * 自动回复
	 * @return String
	 */
	public static String initText(String toUserName,String fromUserName,
			String content){
		TextMessage textMessage = new TextMessage();
		//设置该消息是从哪里发出(之前的发送消息者在此时变成了接收消息者，也就是说之前是他给我发消息，
		//现在该我给他回消息了，他变成了接收者，而我变成了消息的发送者)
		textMessage.setFromUserName(toUserName);
		textMessage.setToUserName(fromUserName);
		//文本消息-text 图片消息-image 语音消息-voice
		//视频消息-video 链接消息-link 地理位置消息-location
		//事件推送-event 取消关注-unsubscribe 菜单点击-CLICK、VIEW
		textMessage.setMsgType(MessageUtil.MESSAGE_TEXT);
		textMessage.setCreateTime(new Date().getTime());
		textMessage.setContent(content);
		//将发送给用户的消息转换成为xml格式的消息
		return MessageUtil.textMessageToXml(textMessage);
	}
	
	/**
	 * 关注事件主菜单（当别人关注该微信公众号）
	 * @return String
	 */
	public static String menuText(){
		StringBuffer sb = new StringBuffer();
		sb.append("欢迎您的关注，请按照菜单提示进行操作：\n\n");
		sb.append("1.课程介绍\n");
		sb.append("2.本人介绍\n");
		sb.append("3.图文消息\n\n");
		sb.append("回复？调出此菜单。");
		return sb.toString();
	}
	
	
	/**
	 * 将图文消息转换成xml
	 * 
	 * @param textMessage
	 * @return
	 */
	public static String newsMessageToXml(NewsMessage newsMessage){
		XStream xStream = new XStream();
		//将xml的根节点替换为“xml”
		xStream.alias("xml",newsMessage.getClass());
		//将消息体的根节点替换为item
		xStream.alias("item",new News().getClass());
		return xStream.toXML(newsMessage );
	}
	
	/**
	 * 将图片消息转换成xml
	 * 
	 * @param textMessage
	 * @return
	 */
	public static String imageMessageToXml(ImageMessage imageMessage){
		XStream xStream = new XStream();
		//将xml的根节点替换为“xml”
		xStream.alias("xml",imageMessage.getClass());
		return xStream.toXML(imageMessage );
	}
	
	/**
	 * 图文消息
	 * 
	 * @param toUserName
	 * @param fromUserName
	 * @return
	 */
	public static String initNewsMessage(String toUserName,String fromUserName){
		String message = null;
		List<News> listNews = new ArrayList<News>();
		NewsMessage newsMessage  = new NewsMessage();
		//第一条图文消息
		News news = new News();
		news.setTitle("这是一个图文消息标题");
		news.setDescription("这是一个图文消息描述");
		news.setPicUrl("http://1907504a.nat123.cc/WeiXinServlet/image/text.jpg");
		news.setUrl("www.baidu.com");
		//第二条图文消息
		News news2 = new News();
		news2.setTitle("这是两个图文消息标题");
		news2.setDescription("这是两个图文消息描述");
		news2.setPicUrl("http://1907504a.nat123.cc/WeiXinServlet/image/qita.jpg");
		news2.setUrl("www.baidu.com");
		
		listNews.add(news);
		listNews.add(news2);
		newsMessage.setToUserName(fromUserName);
		newsMessage.setFromUserName(toUserName);
		newsMessage.setMsgType(MESSAGE_NEWS);
		newsMessage.setCreateTime(new Date().getTime());
		newsMessage.setArticles(listNews);
		newsMessage.setArticleCount(listNews.size());
		message = MessageUtil.newsMessageToXml(newsMessage);
		return message;
	}
	/**
	 * 组装图片消息
	 * @param toUserName
	 * @param fromUserName
	 * @return
	 */
	public static String initImageMessage(String toUserName,String fromUserName){
		String message = null;
		Image image = new Image();
		//media需要通过WeiXinUtil.upload(path, token.getToken(), "image");获取
		image.setMediaId("i8P2xKEU54ztKacQOcdFQZRZpPWYiPtpWTOx4_Pe2wvI-LIw5vsuAhVv5bEqFIrZ");
		ImageMessage imageMessage = new ImageMessage();
		imageMessage.setFromUserName(toUserName);
		imageMessage.setToUserName(fromUserName);
		imageMessage.setMsgType(MESSAGE_IMAGE);
		imageMessage.setCreateTime(new Date().getTime());
		imageMessage.setImage(image);
		message = MessageUtil.imageMessageToXml(imageMessage);
		return message;
	}
	
}
