package aries.DeviceManager;

public class Message {
	private String msg;
	private String messageID;

	public Message(String str) {
		this.msg = str;
	}

	public String getMsg() {
		return msg;
	}

	public String getMessageID() {
		return messageID;
	}

	public void setMessageID(String msgId) {
		this.messageID = msgId;
	}
}
