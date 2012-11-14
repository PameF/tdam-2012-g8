package com.tdam2012.grupo8.networking;

import java.io.StringWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import android.content.Context;

public class MessageSender {

	private Context context;
	
	public MessageSender(Context context) {
		this.context = context;
	}
	
	public boolean registerUser(String username, String password) {
		
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
		 
			Document document = builder.newDocument();
			
			Element root = document.createElement("action");
			root.setAttribute("id", "REQUEST_RANDOM_VALUE");
			root.setAttribute("name", "register-user");
			
			document.appendChild(root);
			
			Element details = document.createElement("action-details");
			
			Element user = document.createElement("user");
			user.setAttribute("username", username);
			user.setAttribute("password", password);
			
			details.appendChild(user);
			root.appendChild(details);
			
			//write the content into xml file
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();

			DOMSource source = new DOMSource(document);
			StringWriter sw = new StringWriter();
		    StreamResult result = new StreamResult(sw);
			
		    transformer.transform(source, result);
		}
		catch(Exception e) {
			
		}
		
		return true;
	}
}
