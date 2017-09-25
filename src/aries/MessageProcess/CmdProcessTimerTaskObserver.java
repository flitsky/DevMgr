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
	private int expirationSeconds;
	JSONObject recvReq;
	JSONObject sendResp;

	public String getMsgID() {
		return ResponseMsgID;
	}

	public CmdProcessTimerTaskObserver(Message msg, ObservableRespMsg observable, int expirationSec) {
		expirationSeconds = expirationSec;

		observable.addObserver(this);
		this.responseMessage = observable;
		this.ResponseMsgID = msg.getMessageID();

		if (expirationSec > 0) {
			System.out.println("[0000 Observer constructed] observers count : " + responseMessage.countObservers()
					+ " id=" + ResponseMsgID + " will be expired in " + expirationSeconds + " seconds");
			expiredTimer = new Timer();
			expiredTimer.schedule(this, expirationSeconds * 1000);
		} else {
			System.out.println("[0000 Observer constructed] observers count : " + responseMessage.countObservers()
					+ " id=" + ResponseMsgID + " will never be expired");
		}

		recvReq = new JSONObject(msg.getMsg());
		sendResp = new JSONObject();
		sendResp.put("org", recvReq.getString("dst"));
		sendResp.put("dst", recvReq.getString("org"));
		sendResp.put("workcode", recvReq.getString("workcode"));

		CmdProc(recvReq);
	}

	public CmdProcessTimerTaskObserver(Message msg, ObservableRespMsg observable) {
		this(msg, observable, 5); // set default expiration to 5 seconds.
	}

	@Override
	public void update(Observable observable, Object arg) {
		// responseMessage = (ObservableRespMsg) observable;
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
			ExpiredProc();
			RemoveObserver();
			System.out.println("      Response Expired... observers count : " + responseMessage.countObservers()
					+ " id=" + ResponseMsgID);
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
