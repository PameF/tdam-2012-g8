package com.tdam2012.grupo8.networking;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

public class MessageSenderService {
		
	protected static String post(String xml) {
		
		String result = "";
		
		try {
			
			HttpClient client = new DefaultHttpClient();
			String serverUrl = "http://10.0.2.2:8080/MessageSender/";
		
			StringEntity se = new StringEntity(xml, HTTP.UTF_8);
		    se.setContentType("text/xml");

			HttpPost post = new HttpPost(serverUrl);
		    post.setHeader("Content-Type","application/soap+xml;charset=UTF-8");
		    post.setEntity(se);
		    
		    HttpResponse response = client.execute(post);
	        HttpEntity re = response.getEntity();

	        result = EntityUtils.toString(re);
	        
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
}
