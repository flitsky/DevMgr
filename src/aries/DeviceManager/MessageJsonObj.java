package aries.DeviceManager;

import org.json.JSONObject;

public class MessageJsonObj {
	private JSONObject jsonMsg;
	private String messageID;

	public MessageJsonObj(String msgId, JSONObject jsonMessage) {
		this.messageID = msgId;
		this.jsonMsg = jsonMessage;
	}

	public JSONObject getMsg() {
		return jsonMsg;
	}

	public String getMessageID() {
		return messageID;
	}
}
