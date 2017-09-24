package aries.MessageProcess;

import java.util.Observable;
import java.util.Observer;

import aries.DeviceManager.Message;
import aries.DeviceManager.ObservableRespMsg;

public abstract class CmdProcessObserver implements Observer {
	private String ResponseMsgID = "";
	private ObservableRespMsg responseMessage;

	public String getMsgID() {
		return ResponseMsgID;
	}
	private void setMsgID(String msgId) {
		this.ResponseMsgID = msgId;
	}
	
	public CmdProcessObserver(Message msg, ObservableRespMsg observable) {
		// TODO Auto-generated constructor stub
		observable.addObserver(this);
		this.ResponseMsgID = msg.getMessageID();
		CmdProc(msg);
	}

	@Override
	public void update(Observable observable, Object arg) {
		responseMessage = (ObservableRespMsg) observable;
		if (responseMessage.getMessageId().equals(ResponseMsgID)) {
			System.out.println("[5555 Response is arrived] <----- msg : " + responseMessage.getMessage());
			ResponseProc(responseMessage.getMessage());
			responseMessage.takeMessage();
			observable.deleteObserver(this);
			System.out.println("[**** DeleteObserver]");
		}
	}

	protected abstract void CmdProc(Message msg);

	protected abstract void ResponseProc(String msg);
}
