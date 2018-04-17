import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

import org.json.simple.JSONObject;


public class MessageEncoder implements Encoder.Text<Message> {

	@SuppressWarnings("unchecked")
	@Override
	public String encode(Message message) throws EncodeException {
		JSONObject msgjson = new JSONObject();
		
		if("new".equals(message.getMode())) {
            msgjson.put("sentby", message.getFromId());
            msgjson.put("msg", message.getMessage());
            msgjson.put("fromname", message.getFromName());
            msgjson.put("toname", message.getToName());
            msgjson.put("mode", "new");
            
        } else if ("confirm".equals(message.getMode())){
        	msgjson.put("sentby", message.getFromId());
            msgjson.put("msg", message.getMessage());
            msgjson.put("fromname", message.getFromName());
            msgjson.put("toname", message.getToName());
            msgjson.put("mode", "confirm");
            msgjson.put("confNum", message.getUsrHash());
        }
        
        return msgjson.toString();
        
	}
	
	@Override
	public void destroy() {
		// Close resources
	}

	@Override
	public void init(EndpointConfig arg0) {
		// Initialization
	}

	
	
}