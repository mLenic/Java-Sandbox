
import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


public class MessageDecoder implements Decoder.Text<Message> {
    
	JSONParser parser = new JSONParser();
	
	@Override
    public Message decode(String s) throws DecodeException {

		Object obj = null;
		try {
			obj = parser.parse(s);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		JSONObject jsonObject = (JSONObject) obj;
		
		
    	Message message = new Message();
    	
    	message.setFromId(jsonObject.get("fromId").toString());
    	message.setToId(jsonObject.get("toId").toString());
    	message.setMessage(jsonObject.get("message").toString());
    	message.setMode(jsonObject.get("mode").toString());
    	message.setUsrHash(jsonObject.get("confNum").toString());
    	
    	return message;
    }

    @Override
    public boolean willDecode(String s) {
        return (s != null);
    }

    @Override
    public void init(EndpointConfig endpointConfig) {
        // Custom initialization logic
    }

    @Override
    public void destroy() {
        // Close resources
    }
}