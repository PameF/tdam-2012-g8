package com.tdam2012.grupo8.networking;

import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class MessageSenderService {
		
	protected static String post(String xml) {
		
		String result = "";
		
		try {
			
			HttpClient client = new DefaultHttpClient();
			String serverUrl = "http://192.168.1.5:8080/MessageSender/";
		
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
	
	protected static String processRequest(String xml) {
		
		String message = null;
				
		try {
			StringReader reader = new StringReader(xml);
			InputSource input = new InputSource(reader);
			
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
				 
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = builder.parse(input);
			
			Element root = document.getDocumentElement();
			String type = root.getAttribute("type");
			
			if(type.compareTo("error") == 0) {
				Node detail = root.getChildNodes().item(0);
				detail = detail.getAttributes().getNamedItem("description");
				message = detail.getTextContent();
			}
			
		} catch (Exception e) {
			message = e.getMessage();
		}
		
		return message;
	}
	
	public static boolean checkConnectivity(Context context) {
		
		ConnectivityManager cm = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
		
		return activeNetwork.isConnectedOrConnecting();
	}
}
