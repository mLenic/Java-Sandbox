public class Message {
	private String fromId;
	private String toId;
    private String message;
    private String fromName;
    private String toName;
    
    private String mode;
    private String usrHash;
    

	public String getFromId() {
		return fromId;
	}

	public void setFromId(String fromId) {
		this.fromId = fromId;
	}

	public String getToId() {
		return toId;
	}

	public void setToId(String toId) {
		this.toId = toId;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getFromName() {
		return fromName;
	}

	public void setFromName(String fromName) {
		this.fromName = fromName;
	}

	public String getToName() {
		return toName;
	}

	public void setToName(String toName) {
		this.toName = toName;
	}
	
	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}
	
	public String getUsrHash() {
		return usrHash;
	}

	public void setUsrHash(String usrHash) {
		this.usrHash = usrHash;
	}
	
    @Override
    public String toString() {
        return super.toString();
    }

   
}
