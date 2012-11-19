package com.tdam2012.grupo8.networking;

import java.io.StringReader;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import com.tdam2012.grupo8.R;
import com.tdam2012.grupo8.base.Preferences;
import com.tdam2012.grupo8.data.ActionsRegistryRepository;
import com.tdam2012.grupo8.data.ActionsRegistryRepository.ActionEnum;
import com.tdam2012.grupo8.data.ContactsRepository;
import com.tdam2012.grupo8.entities.ActionRegistry;
import com.tdam2012.grupo8.entities.Contact;
import com.tdam2012.grupo8.ui.SmsWebConversationActivity;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.widget.Toast;

public class ReceiveMessageService extends AsyncTask<Object, Void, String> {

	public static final String TIMESTAMP_FORMAT = "dd/MM/yyyy HH:mm:ss";
	
	private Context context;
	private String username;
	private String password;
	private Date lastUpdate;
	
	private SharedPreferences preferences; 
	
	@Override
	protected String doInBackground(Object... params) {
		
		context = (Context)params[0];
		
		preferences = context.getSharedPreferences(Preferences.PREFERENCE_USER, context.MODE_PRIVATE);
		
		username = preferences.getString(Preferences.PREFERENCE_USER_NAME, "");
		password = preferences.getString(Preferences.PREFERENCE_USER_PASSWORD, "");
		lastUpdate = new Date(preferences.getLong(Preferences.PREFERENCE_SYNC_LAST_TIME, 0l));
				
		String xml = generateXmlReceiveMessage();
		String response = MessageSenderService.post(xml);
		
		return response;
	}

	@Override
	protected void onPostExecute(String result) {
		
		String message = MessageSenderService.processRequest(result);		
		if(message == null) {
			
			ActionsRegistryRepository registryRep = new ActionsRegistryRepository(context);	
			ContactsRepository contactRep = new ContactsRepository(context);
			
			SimpleDateFormat format = new SimpleDateFormat(TIMESTAMP_FORMAT);
			
			try {
				StringReader reader = new StringReader(result);
				InputSource input = new InputSource(reader);
				
				DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
					 
				DocumentBuilder builder = factory.newDocumentBuilder();
				Document document = builder.parse(input);
				
				// Node: action
				Element root = document.getDocumentElement();
				
				// Node: messages-list
				Node list = root.getChildNodes().item(0);
				
				// Nodes: message
				NodeList messagesList = list.getChildNodes();
				
				Node current;
				ActionRegistry reg;
				Contact contact;
				
				for(int i = 0; i < messagesList.getLength(); i++) {
					
					current = messagesList.item(i);
					
					String from = current.getAttributes().getNamedItem("from").getTextContent();
					String timestamp = current.getAttributes().getNamedItem("timestamp").getTextContent();
					String content = current.getTextContent();
									
					contact = contactRep.getContactByUsername(from);
					
					reg = new ActionRegistry();
			       	reg.setAction(ActionEnum.RECEIVED_MESSAGE_WEB);
			       	reg.setContactId(contact.getId());
			       	reg.setContactName(contact.getName());
			       	reg.setContactPhoneNumber(from);
			       	reg.setMessage(content);
			       	reg.setDate(format.parse(timestamp));
			       
			        registryRep.insertRegistration(reg);
			        generateNotification(reg.getContactId(), reg.getContactName(), reg.getContactPhoneNumber(), content);
				}
				
			} catch (Exception e) {
				
			}

	        SharedPreferences.Editor editor = preferences.edit();
	        editor.putLong(Preferences.PREFERENCE_SYNC_LAST_TIME, new Date().getTime());
	        editor.commit();
		}
		else {
			//Toast.makeText(context, message, Toast.LENGTH_LONG).show();
		}
	}
	
	private String generateXmlReceiveMessage() {

		String timestamp = new SimpleDateFormat(TIMESTAMP_FORMAT).format(lastUpdate); 
		
		String xml = "<action id=\"REQUEST_RANDOM_VALUE\" name=\"get-messages\">" +
						"<action-detail>" +
							"<auth username=\"" + username + "\" key=\"" + password + "\"></auth>" +
							"<filter type=\"timestamp\">" + timestamp + "</filter>" +
						"</action-detail>" +
					"</action>";
		
		return xml;
	}
	
	private void generateNotification(long contactId, String contactName, String contactUsername, String message) {
		
		NotificationManager manager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
		Notification notification = new Notification(R.drawable.ic_launcher, contactName, System.currentTimeMillis());
		
		Intent intent = new Intent(context, SmsWebConversationActivity.class);
		intent.putExtra(SmsWebConversationActivity.CONTACT_USERNAME_KEY, contactUsername);
		intent.putExtra(SmsWebConversationActivity.CONTACT_ID_KEY, contactId);
		intent.putExtra(SmsWebConversationActivity.CONTACT_NAME_KEY, contactName);
		
		PendingIntent pending = PendingIntent.getActivity(context, 0, intent, 0);	
		
		notification.setLatestEventInfo(context, "Nuevo sms web", message, pending);
		manager.notify(0, notification);
	}
}
