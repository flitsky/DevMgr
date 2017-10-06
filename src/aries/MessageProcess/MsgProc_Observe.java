package aries.MessageProcess;

import org.json.JSONObject;

import aries.DeviceManager.Message;

public class MsgProc_Observe extends CmdProcessTimerTaskObserver {

	public MsgProc_Observe(Message msg, ObservableRespMsg observable) {
		super(msg, observable);
		// TODO Auto-generated constructor stub
	}

	public MsgProc_Observe(Message msg, ObservableRespMsg observable, int expirationSec) {
		super(msg, observable, expirationSec);
		// TODO Auto-generated constructor stub
	}

	public MsgProc_Observe(Message msg, ObservableRespMsg observable, int expirationSec, MsgReqType msgReqType) {
		super(msg, observable, expirationSec, msgReqType);
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
		// save data to DB
	}

	@Override
	protected void expiredRespProc() {
		sendRespProc(sendRespJO);
	}
}
