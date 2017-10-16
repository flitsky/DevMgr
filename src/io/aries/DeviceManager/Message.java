package io.aries.DeviceManager;

public class Message {
	private String msg;
	private String messageID;

	public Message(String msgId, String str) {
		this.messageID = msgId;
		this.msg = str;
	}

	public String getMsg() {
		return msg;
	}

	public String getMessageID() {
		return messageID;
	}
}
