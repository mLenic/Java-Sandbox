import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.websocket.EncodeException;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import org.apache.log4j.Logger;

import project.api.common.Message;
import project.api.common.MessageDecoder;
import project.api.common.MessageEncoder;
import project.api.common.Notice;
import project.api.webactions.MessagingApi;

@ServerEndpoint(value = "/api/messaging/m/sockets/id/{userid}", decoders = {MessageDecoder.class}, encoders = {MessageEncoder.class})
public class MessagingController {
	
	private Session session;
	private static Map<String, Session> userSessionMap = new HashMap<>();
	private static HashMap<String, String> users = new HashMap<>();
	private Logger log = Logger.getLogger(getClass());
	
	private static final String MESSAGE_NOTIFICATION = "msg";
	
	@OnOpen
	public void onOpen(Session session, @PathParam("userid") String userid) {
		this.session = session;
		userSessionMap.put(userid, session);
        users.put(session.getId(), userid);
	}
	
	@OnClose
	public void onClose(Session session, @PathParam("userid") String userid) {
		userSessionMap.remove(userid);
	}
	
	@OnMessage
	public void onMessage(Session session, Message message) {
		/*Handle message:
		*	Read message, determine which user, save to Db, check if user is online and send to him, otherwise dont
		*	Messaging Api is your messaging class that handles saving messages to DB
		*	- added - boolean that marks whether message has been saved to DB or not 
		*/
		boolean added = MessagingApi.handleMessage(message);
		if(added) {
			Session toSession = userSessionMap.get(message.getToId());
			Session fromSession = userSessionMap.get(message.getFromId());
			//User that you are writing to is not currently connected
			if(toSession == null) {
				//Send notification to user if maybe he is using the application
				Notice msgNotice = new Notice();
				msgNotice.setMode(MESSAGE_NOTIFICATION);
				msgNotice.setMessage("New message from " + message.getFromName() + ".");
				msgNotice.setFromname(message.getFromName());
				msgNotice.sendToUser(message.getToId());
				
				
			} else {
				//User is connected right now, send message via websockets - no need for notifications - user is on messaging tab
				try {
					message.setMode("new");
					toSession.getBasicRemote().sendObject(message);
					
				} catch (IOException | EncodeException e) {
					e.printStackTrace();
				} 
			}
			
			try {
				//Sends confirmation of sent message - if it isn't received, message should be classified as not sent
				message.setMode("confirm");
				fromSession.getBasicRemote().sendObject(message);
			} catch (IOException | EncodeException e) {
				e.printStackTrace();
			} 
		}

	}
	
	@OnError
	public void onError(Throwable e) {
		System.out.println("Error occured");
		e.printStackTrace();
	}
}