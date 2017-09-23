package aries.DeviceManager;

import org.json.JSONObject;

public class MessageJsonObj {
	private JSONObject jsonMsg;

	public MessageJsonObj(JSONObject jsonMessage) {
		this.jsonMsg = jsonMessage;
	}

	public JSONObject getMsg() {
		return jsonMsg;
	}
}
