package aries.MessageProcess;

import java.util.Observable;
import java.util.Observer;
import java.util.Timer;
import java.util.TimerTask;

import org.json.JSONObject;

import aries.DeviceManager.Message;
import io.dase.network.DamqSndProducer;
import io.dase.network.DamqRcvConsumer.MsgType;

public abstract class CmdProcessTimerTaskObserver extends TimerTask implements Observer {
	private String ResponseMsgID = "";
	private ObservableRespMsg respMsgObservable;
	private Timer expiredTimer;
	JSONObject recvdReqJO;
	JSONObject sendReqJO;
	JSONObject recvdRespJO;
	JSONObject sendRespJO;
	MsgReqType msgReqType;
	private String LogMsgFlow = "";

	public CmdProcessTimerTaskObserver(Message msg, ObservableRespMsg observable) {
		this(msg, observable, 5); // set default expiration to 5 seconds.
	}

	public CmdProcessTimerTaskObserver(Message msg, ObservableRespMsg observable, int expirationSec) {
		this(msg, observable, expirationSec, MsgReqType.OneTime); // set default MsgReqType to OneTime.
	}

	public CmdProcessTimerTaskObserver(Message msg, ObservableRespMsg observable, int expirationSec,
			MsgReqType cmdType) {
		recvdReqJO = new JSONObject(msg.getMsg());
		sendReqJO = new JSONObject();
		sendRespJO = new JSONObject();

		this.msgReqType = cmdType;
		this.ResponseMsgID = msg.getMessageID();
		if (observable != null) {
			observable.addObserver(this);
			this.respMsgObservable = observable;
			System.out.println("observers count : " + respMsgObservable.countObservers());
		}

		if (expirationSec > 0) {
			System.out.println("[0000 Observer constructed] id=" + ResponseMsgID + " will be expired in " + expirationSec + " seconds");
			expiredTimer = new Timer();
			expiredTimer.schedule(this, expirationSec * 1000);
		} else {
			System.out.println("[0000 Observer constructed] id=" + ResponseMsgID + " will never be expired");
		}

		sendRespJO.put("org", recvdReqJO.getString("dst"));
		sendRespJO.put("dst", recvdReqJO.getString("org"));
		sendRespJO.put("msgtype", "res");
		sendRespJO.put("msgid", recvdReqJO.getString("msgid"));
		sendRespJO.put("workcode", recvdReqJO.getString("workcode"));

		LogMsgFlow = "===> [1]" + recvdReqJO.getString("workcode") + " ==> ";
		System.out.println(LogMsgFlow);
		LogMsgFlow = LogMsgFlow + "[2]Request process & Make Request ==> ";
		System.out.println(LogMsgFlow);
		recvdReqProc(recvdReqJO);
	}

	@Override
	public void update(Observable observable, Object arg) {
		// if (respMsgObservable.getMessageId().equals(ResponseMsgID)) {
		if (((ObservableRespMsg) observable).getMessageId().equals(ResponseMsgID)) {
			if (expiredTimer != null)
				expiredTimer.cancel();
			LogMsgFlow = LogMsgFlow + "[5]Response is arrived ==> ";
			System.out.println(LogMsgFlow);
			recvdRespJO = new JSONObject(respMsgObservable.getMessage());
			recvdRespProc(recvdRespJO);
			System.out.println(LogMsgFlow = LogMsgFlow + "[7]Process Done");
			respMsgObservable.takeMessage();
			if (msgReqType == MsgReqType.OneTime) {
				RemoveObserver();
				System.out.println("[**** DeleteObserver] observers cnt : " + respMsgObservable.countObservers());
			}
		}
	}

	@Override
	public void run() {
		if (!ResponseMsgID.equals("")) {
			sendRespJO.put("body", new JSONObject().put("status", 408));
			LogMsgFlow = LogMsgFlow + "[5]Response Expired ";
			System.out.println(LogMsgFlow);
			expiredRespProc();
			RemoveObserver();
			System.out.println("      Response Expired... observers count : " + respMsgObservable.countObservers());
		}
	}

	public String getMsgID() {
		return ResponseMsgID;
	}

	protected void RemoveObserver() {
		ResponseMsgID = "";
		respMsgObservable.deleteObserver(this);
	}

	protected abstract void recvdReqProc(JSONObject receivedRequest);

	protected void sendReqProc(JSONObject sendRequest) {
		LogMsgFlow = LogMsgFlow + "[3]Send Request to " + sendRequest.getString("dst") + "] ==> ";
		System.out.println(LogMsgFlow);
		sendMsgProc(sendRequest);
		LogMsgFlow = LogMsgFlow + "[4]Wait Response ... ";
		System.out.println(LogMsgFlow);
	}

	protected abstract void recvdRespProc(JSONObject msg);

	protected abstract void expiredRespProc();

	protected void sendRespProc(JSONObject sendResponse) {
		LogMsgFlow = LogMsgFlow + "[6]Send Response ";
		System.out.println(LogMsgFlow);
		sendMsgProc(sendResponse);
	}

	private void sendMsgProc(JSONObject sendMsg) {
		DamqSndProducer sndProducer = DamqSndProducer.getInstance();
		MsgType msgType = MsgType.Request;
		if (sendMsg.getString("msgtype").equals("res"))
			msgType = MsgType.Response;
		sndProducer.PushToSendQueue(sendMsg.getString("dst"), sendMsg.getString("msgid"), msgType,
				sendMsg.getString("workcode"), sendMsg.get("body").toString());
	}

	public enum MsgReqType {
		OneTime(0), Constantly(1);
		private int _value;

		MsgReqType(int Value) {
			this._value = Value;
		}

		public int getValue() {
			return _value;
		}
	}
}
