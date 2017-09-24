package aries.MessageProcess;

import java.util.Observable;
import java.util.concurrent.BlockingQueue;

import org.json.JSONObject;

import aries.DeviceManager.Message;

public class ObservableRespMsg extends Observable {
	private String msg = "";
	private String messageId = "";
	private BlockingQueue<Message> queueResp;

	public ObservableRespMsg(BlockingQueue<Message> qResp) {
		this.queueResp = qResp;
	}

	private void setMessage(String str) {
		this.msg = str;
		setChanged();
		notifyObservers();
	}

	public String getMessage() {
		return msg;
	}

	private void setMessageId(String msgId) {
		this.messageId = msgId;
	}

	public String getMessageId() {
		return messageId;
	}

	public void work() {
		if (this.msg == "") {
			takeMessage();
		}
	}

	public boolean takeMessage() {
		Message qResp;

		// initialization by take message.
		this.msg = "";

		// take message & check response queue is available
		if (queueResp.isEmpty()) {
		} else {
			try {
				if ((qResp = queueResp.take()).getMsg() != "") {
					setMessageId(qResp.getMessageID());
					setMessage(qResp.getMsg());
				}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return true;
	}
}
