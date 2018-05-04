package com.eastnet.wechat.utils;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import net.sf.json.JSONObject;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import com.eastnet.wechat.pojo.AccessToken;
import com.eastnet.wechat.pojo.Button;
import com.eastnet.wechat.pojo.ClickButton;
import com.eastnet.wechat.pojo.Menu;
import com.eastnet.wechat.pojo.ViewButton;

@SuppressWarnings("deprecation")
public class WeiXinUtil {

	public static final String APPID = "wxf22c4ae214536c3c";
	public static final String APPSECRET = "980b34cc596ced13b4f38111f62756aa";
	public static final String ACCESS_TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
	public static final String UPLOAD_URL = "https://api.weixin.qq.com/cgi-bin/media/upload?access_token=ACCESS_TOKEN&type=TYPE";
	public static final String CREATE_MENU_URL = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN";
	
	
	
	/**
	 * get请求
	 * 
	 * @param url
	 * @return
	 */
	public static JSONObject doGetStr(String url){
		@SuppressWarnings({ "resource" })
		DefaultHttpClient httpClient = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet(url);
		JSONObject jsonObject = null;
		try {
			HttpResponse response = httpClient.execute(httpGet);
			HttpEntity entity = response.getEntity();
			if(entity != null){
				String result = EntityUtils.toString(entity,"UTF-8");
				jsonObject = JSONObject.fromObject(result);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return jsonObject;
	}
	
	/**
	 * post请求
	 * 
	 * @param url
	 * @return
	 */
	public static JSONObject doPostStr(String url,String outoutStr){
		
		@SuppressWarnings({ "resource" })
		DefaultHttpClient httpClient = new DefaultHttpClient();
		HttpPost httpPost = new HttpPost(url);
		JSONObject jsonObject = null;
		try {
			httpPost.setEntity(new StringEntity(outoutStr,"UTF-8"));
			HttpResponse response = httpClient.execute(httpPost);
			String result = EntityUtils.toString(response.getEntity(),"UTF-8");
			jsonObject = JSONObject.fromObject(result);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jsonObject;
	}
	/**
	 * 获取access_token（注意：access——token过期时间为两个小时 ，即expires_in）
	 * 在使用的时候需要判断，如果使用的时间点超过获取的时间点两个小时则需要重新获取
	 * 
	 * @return
	 */
	public static AccessToken getAccessToken(){
		AccessToken token = new AccessToken();
		String url = ACCESS_TOKEN_URL.replace("APPID",APPID)
				.replace("APPSECRET", APPSECRET);
		JSONObject jsonObject = doGetStr(url);
		if(jsonObject != null){
			token.setToken(jsonObject.getString("access_token"));
			token.setExpiresIn(jsonObject.getInt("expires_in"));
		}
		return token;
	}
	/**
	 * 获取图片消息media_id
	 * 
	 * @param filePath
	 * @param accessToken
	 * @param type
	 * @return
	 * @throws Exception
	 */
	public static String upload(String filePath,String accessToken,String type)
			throws Exception{
		File file = new File(filePath);
		if(!file.exists() || !file.isFile()){
			throw new IOException("文件不存在");
		}
		
		String url = UPLOAD_URL.replace("ACCESS_TOKEN",accessToken)
				.replace("TYPE", type);
		URL urlObj = new URL(url);
		
		HttpURLConnection con = (HttpURLConnection)urlObj.openConnection();
		con.setRequestMethod("POST");
		con.setDoInput(true);
		con.setDoOutput(true);
		con.setUseCaches(false);
		
		//设置请求头信息
		con.setRequestProperty("Connection", "Keep-Alive");
		con.setRequestProperty("Charset", "UTF-8");
		//设置边界
		String BOUNDARY = "-------------" + System.currentTimeMillis();
		con.setRequestProperty("Content-Type","multipart/form-data;boundary=" + BOUNDARY);
		
		StringBuilder sb = new StringBuilder();
		sb.append("--");
		sb.append(BOUNDARY);
		sb.append("\r\n");
		sb.append("Content-Disposition:form-data;name=\"file\";filename=\"" + file.getName() + "\"\r\n");
		sb.append("Content-Type:application/octet-stream\r\n\r\n");
		
		byte[] head = sb.toString().getBytes("utf-8");
		
		//获得输出流
		OutputStream out = new DataOutputStream(con.getOutputStream());
		out.write(head);
		
		//文件正文部分
		//把文件以流的形式推送到url当中
		DataInputStream in = new DataInputStream(new FileInputStream(file));
		int bytes = 0;
		byte[] bufferOut = new byte[1024];
		while((bytes = in.read(bufferOut)) != -1){
			out.write(bufferOut,0,bytes);
		}
		in.close();
		
		//结尾部分
		byte[] foot = ("\r\n--" + BOUNDARY + "--\r\n").getBytes("utf-8");
		
		out.write(foot);
		
		out.flush();
		out.close();
		
		StringBuffer buffer = new StringBuffer();
		BufferedReader reader = null;
		String result = null;
		try{
			reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String line = null;
			while((line = reader.readLine())!=null){
				buffer.append(line);
			}
			if(result == null){
				result = buffer.toString();
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(reader != null){
				reader.close();
			}
		}
		JSONObject jsonObject = JSONObject.fromObject(result);
		System.out.println(jsonObject);
		String mediald = jsonObject.getString("media_id");
		return mediald;
	}
	
	/**
	 * 组装菜单
	 * @return
	 */
	public static Menu initMenu(){
		Menu menu = new Menu();
		
		ClickButton button11 = new ClickButton();
		button11.setName("click菜单");
		button11.setType("click");
		button11.setKey("11");
		
		ViewButton viewCar2 = new ViewButton();
		viewCar2.setName("view菜单");
		viewCar2.setType("view");
		viewCar2.setUrl("http://1907504a.nat123.cc/WeiXinServlet/HtmlHome/car_1.html");
		
		
		ClickButton button31 = new ClickButton();
		button31.setName("扫码事件");
		button31.setType("scancode_push");
		button31.setKey("31");
		
		ClickButton button32 = new ClickButton();
		button32.setName("地理位置");
		button32.setType("location_select");
		button32.setKey("32");
		
		ClickButton button33 = new ClickButton();
		button33.setName("调出相册");
		button33.setType("pic_weixin");
		button33.setKey("33");
		
		ClickButton button34 = new ClickButton();
		button34.setName("看看是啥");
		button34.setType("pic_photo_or_album");
		button34.setKey("34");
		
		Button buttonEvent = new Button();
		buttonEvent.setName("事件");
		buttonEvent.setSub_button(new Button[]{button31,button32,button33,button34});
		
		menu.setButton(new Button[]{button11,viewCar2,buttonEvent});
		return menu;
	}
	
	public static int createMenu(String token,String menu){
		int result = 0;
		AccessToken access_token = WeiXinUtil.getAccessToken();
		String url = CREATE_MENU_URL.replace("ACCESS_TOKEN",token);
		JSONObject jsonObject =  doPostStr(url,menu);
		if(jsonObject != null){
			result = jsonObject.getInt("errcode");
		}
		return result;
	}
	
	
	/*ViewButton viewCar1 = new ViewButton();
	viewCar1.setName("汽车一");
	viewCar1.setType("view");
	viewCar1.setUrl("http://1907504a.nat123.cc/WeiXinServlet/HtmlHome/car_before.html");
	
	ViewButton viewCar2 = new ViewButton();
	viewCar2.setName("汽车二");
	viewCar2.setType("view");
	viewCar2.setUrl("http://1907504a.nat123.cc/WeiXinServlet/HtmlHome/car_1.html");
	
	ViewButton viewCar3 = new ViewButton();
	viewCar3.setName("汽车三");
	viewCar3.setType("view");
	viewCar3.setUrl("http://1907504a.nat123.cc/WeiXinServlet/HtmlHome/car_2.html");
	
	
	ViewButton viewCar4 = new ViewButton();
	viewCar4.setName("汽车四");
	viewCar4.setType("view");
	viewCar4.setUrl("http://1907504a.nat123.cc/WeiXinServlet/HtmlHome/car_3.html");
	
	ViewButton viewCar5 = new ViewButton();
	viewCar5.setName("汽车四");
	viewCar5.setType("view");
	viewCar5.setUrl("http://1907504a.nat123.cc/WeiXinServlet/HtmlHome/car_m.html");
	
	
	Button buttonCar = new Button();
	buttonCar.setName("汽车");
	buttonCar.setSub_button(new Button[]{viewCar1,viewCar2,viewCar3,viewCar4,viewCar5});
	
	ViewButton viewToy2 = new ViewButton();
	viewToy2.setName("玩具一");
	viewToy2.setType("view");
	viewToy2.setUrl("http://1907504a.nat123.cc/WeiXinServlet/HtmlHome/toy_form.html");
	
	ViewButton viewToy3 = new ViewButton();
	viewToy3.setName("玩具二");
	viewToy3.setType("view");
	viewToy3.setUrl("http://1907504a.nat123.cc/WeiXinServlet/HtmlHome/toy_success.html");
	
	Button buttonToy = new Button();
	buttonToy.setName("玩具");
	buttonToy.setSub_button(new Button[]{viewToy2,viewToy3});
	
	menu.setButton(new Button[]{buttonCar,buttonToy});*/
	
}
