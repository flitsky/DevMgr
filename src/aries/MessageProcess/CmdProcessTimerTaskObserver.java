package aries.MessageProcess;

import java.util.Observable;
import java.util.Observer;
import java.util.Timer;
import java.util.TimerTask;

import org.json.JSONObject;

import aries.DeviceManager.Message;

public abstract class CmdProcessTimerTaskObserver extends TimerTask implements Observer {
	private String ResponseMsgID = "";
	private ObservableRespMsg responseMessage;
	private Timer expiredTimer;
	JSONObject recvdReqJO;
	JSONObject sendRespJO;

	public String getMsgID() {
		return ResponseMsgID;
	}

	public CmdProcessTimerTaskObserver(Message msg, ObservableRespMsg observable, int expirationSec) {
		recvdReqJO = new JSONObject(msg.getMsg());
		sendRespJO = new JSONObject();

		observable.addObserver(this);
		this.responseMessage = observable;
		this.ResponseMsgID = msg.getMessageID();

		if (expirationSec > 0) {
			System.out.println("[0000 Observer constructed] observers count : " + responseMessage.countObservers()
					+ " id=" + ResponseMsgID + " will be expired in " + expirationSec + " seconds");
			expiredTimer = new Timer();
			expiredTimer.schedule(this, expirationSec * 1000);
		} else {
			System.out.println("[0000 Observer constructed] observers count : " + responseMessage.countObservers()
					+ " id=" + ResponseMsgID + " will never be expired");
		}

		sendRespJO.put("org", recvdReqJO.getString("dst"));
		sendRespJO.put("dst", recvdReqJO.getString("org"));
		sendRespJO.put("msgid", recvdReqJO.getString("msgid"));
		sendRespJO.put("workcode", recvdReqJO.getString("workcode"));

		CmdProc(recvdReqJO);
	}

	public CmdProcessTimerTaskObserver(Message msg, ObservableRespMsg observable) {
		this(msg, observable, 5); // set default expiration to 5 seconds.
	}

	@Override
	public void update(Observable observable, Object arg) {
		// if ((ObservableRespMsg)observable).getMessageId().equals(ResponseMsgID))
		if (responseMessage.getMessageId().equals(ResponseMsgID)) {
			expiredTimer.cancel();
			System.out.println("[5555 Response is arrived] <----- msg : " + responseMessage.getMessage());
			ResponseProc(responseMessage.getMessage());
			responseMessage.takeMessage();
			RemoveObserver();
			System.out.println("[**** DeleteObserver] observers cnt : " + responseMessage.countObservers());
		}
	}

	@Override
	public void run() {
		if (!ResponseMsgID.equals("")) {
			sendRespJO.put("body", new JSONObject().put("status", 408));
			ExpiredProc();
			RemoveObserver();
			System.out.println("      Response Expired... observers count : " + responseMessage.countObservers());
		}
	}

	protected void RemoveObserver() {
		ResponseMsgID = "";
		responseMessage.deleteObserver(this);
	}

	protected abstract void CmdProc(JSONObject receivedRequest);

	protected abstract void ResponseProc(String msg);

	protected abstract void ExpiredProc();
}
