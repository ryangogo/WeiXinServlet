package com.eastnet.wechat.pojo;

import java.util.List;

/**
 * 图文消息信息
 * 
 * @Project:  WeiXinServlet
 * @ClassName: NewsMessage.java
 * @Company: JIT Northeast R & D 
 * @Description: TODO
 * @author  Administrator
 * @date  2017年4月10日
 */

public class NewsMessage extends BaseMessage{

	private int ArticleCount;//图文消息个数，限制为10条以内 
	private List<News> Articles;//消息体
	
	public int getArticleCount() {
		return ArticleCount;
	}
	public void setArticleCount(int i) {
		ArticleCount = i;
	}
	public List<News> getArticles() {
		return Articles;
	}
	public void setArticles(List<News> articles) {
		Articles = articles;
	}
	
	
}
