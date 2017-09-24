package aries.MessageProcess;

import java.util.Observable;
import java.util.Observer;

import aries.DeviceManager.Message;
import io.dase.network.DamqRcvProducer;

public abstract class CmdProcessObserver implements Observer {
	private String ResponseMsgID = "";
	private ObservableRespMsg responseMessage;

	public String getMsgID() {
		return ResponseMsgID;
	}

	public CmdProcessObserver(Message msg, ObservableRespMsg observable) {
		// TODO Auto-generated constructor stub
		observable.addObserver(this);
		this.ResponseMsgID = msg.getMessageID();
		CmdProc(msg);
		new Thread(new Runnable() {
			@Override
			public void run() {
				while (!ResponseMsgID.equals("")) {
					try {
						System.out.println("      Waiting Response... id=" + ResponseMsgID);
						Thread.sleep(3000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}).start();
	}

	@Override
	public void update(Observable observable, Object arg) {
		responseMessage = (ObservableRespMsg) observable;
		if (responseMessage.getMessageId().equals(ResponseMsgID)) {
			System.out.println("[5555 Response is arrived] <----- msg : " + responseMessage.getMessage());
			ResponseProc(responseMessage.getMessage());
			responseMessage.takeMessage();
			ResponseMsgID = "";
			observable.deleteObserver(this);
			System.out.println("[**** DeleteObserver]");
		}
	}

	protected abstract void CmdProc(Message msg);

	protected abstract void ResponseProc(String msg);
}
