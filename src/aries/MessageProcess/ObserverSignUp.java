package aries.MessageProcess;

import org.json.JSONObject;

import aries.DeviceManager.Message;

public class ObserverSignUp extends CmdProcessTimerTaskObserver {

	public ObserverSignUp(Message msg, ObservableRespMsg observable) {
		super(msg, observable);
		// TODO Auto-generated constructor stub
	}

	public ObserverSignUp(Message msg, ObservableRespMsg observable, int expirationSec) {
		super(msg, observable, expirationSec);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void recvdReqProc(JSONObject recvdReq) {
		sendReqJO = recvdReq;
		sendReqJO.put("dst", "common");
		sendReqProc(sendReqJO);
	}

	@Override
	protected void recvdRespProc(JSONObject recvdResp) {
		sendRespJO.put("body", recvdResp.get("body").toString());
		sendRespProc(sendRespJO);
	}

	@Override
	protected void expiredRespProc() {
		sendRespProc(sendRespJO);
	}
}
