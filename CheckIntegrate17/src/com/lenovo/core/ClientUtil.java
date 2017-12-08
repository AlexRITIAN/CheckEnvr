package com.lenovo.core;

import java.io.IOException;
import java.nio.charset.Charset;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

/**
 * 模拟http请求工具类
 * @author yuhao5
 *
 */
public class ClientUtil {
//	private static Logger logger = Logger.getLogger(ClientUtil.class);
	//https://qyapi.weixin.qq.com/cgi-bin/gettoken?corpid=wx94ba1d7a4712e74b&corpsecret=-V3iBCzfULaIEkJisRsdsFnJ0s_2GhmTFc0DbZuOlDKoMwPEWNGjW-nt-Mws-QF8
	//{"access_token":"Y0DuiQAnxnHD0g6_oWv3TTZ4uJRKfxSH7f51f1zYT6sMEb_F3HT5gKcBvFvevoEP","expires_in":7200}
	/**
	 * 模拟http get请求
	 * @param url
	 * 			请求的url
	 * @return
	 * 			响应实体
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public static String get(String url) throws ClientProtocolException, IOException{
		CloseableHttpClient httpClient = HttpClients.createDefault();//创建默认的httpClient
		HttpGet get = new HttpGet(url);//创建get请求
		CloseableHttpResponse response = httpClient.execute(get);//执行get请求
		HttpEntity entity = response.getEntity();//获取响应实体
		String entityStr = EntityUtils.toString(entity,"UTF-8");//将响应实体转换为String,字符集为UTF-8
//		logger.info(entityStr);
		httpClient.close();
		return entityStr;
	}
	
	// "https://qyapi.weixin.qq.com/cgi-bin/message/send?access_token="
	/*{
			   "touser": "UserID1|UserID2|UserID3",
			   "toparty": " PartyID1 | PartyID2 ",
			   "totag": " TagID1 | TagID2 ",
			   "msgtype": "text",
			   "agentid": 1,
			   "text": {
			       "content": "Holiday Request For Pony(http://xxxxx)"
			   },
			   "safe":0
			}*/
	/**
	 * 模拟post请求
	 * @param url
	 * 			请求url
	 * @param postStr
	 * 			请求的数据
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public static String post(String url,String postStr) throws ClientProtocolException, IOException{
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpPost post = new HttpPost(url);
//		logger.info(postStr);
		post.addHeader("Content-type","application/json; charset=utf-8");  
        post.setHeader("Accept", "application/json");  
		post.setEntity(new StringEntity(postStr, Charset.forName("UTF-8")));
		CloseableHttpResponse response = httpClient.execute(post);
		HttpEntity entity = response.getEntity();
		String entityStr = EntityUtils.toString(entity);
//		logger.info(entityStr);
		httpClient.close();
		return entityStr;
	}
}
