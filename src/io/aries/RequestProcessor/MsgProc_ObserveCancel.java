package io.aries.RequestProcessor;

import org.json.JSONObject;

import io.aries.DeviceManager.Message;

public class MsgProc_ObserveCancel extends CmdProcessTimerTaskObserver {

	public MsgProc_ObserveCancel(Message msg, ObservableRespMsg observable) {
		super(msg, observable);
		// TODO Auto-generated constructor stub
	}

	public MsgProc_ObserveCancel(Message msg, ObservableRespMsg observable, int expirationSec) {
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

		// TODO
		// cancel observe
	}

	@Override
	protected void expiredRespProc() {
		sendRespProc(sendRespJO);
	}
}
