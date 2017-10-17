package io.aries.RequestProcessor;

import org.json.JSONObject;

import io.aries.DeviceManager.Message;
import io.aries.TriggerManager.TrgMgr;

public class MsgProc_TriggerCancel extends CmdProcessTimerTaskObserver {

	public MsgProc_TriggerCancel(Message msg, ObservableRespMsg observable) {
		super(msg, observable);
		// TODO Auto-generated constructor stub
	}

	public MsgProc_TriggerCancel(Message msg, ObservableRespMsg observable, int expirationSec) {
		super(msg, observable, expirationSec);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void recvdReqProc(JSONObject recvdReq) {
		String triggerName;
		triggerName = recvdReq.getJSONObject("body").getString("triggername");
		if(TrgMgr.getInstance().removeTrigger(triggerName)) {
			sendRespJO.put("body", new JSONObject().put("status", 200));
		} else {
			sendRespJO.put("body", new JSONObject().put("status", 404));
			sendRespJO.put("body", new JSONObject().put("reason", "Trigger Name is not exist."));
		}

		sendRespJO.put("dst", recvdReq.getString("org"));
		sendRespProc(sendRespJO);
	}

	@Override
	protected void recvdRespProc(JSONObject recvdResp) {
	}

	@Override
	protected void expiredRespProc() {
	}
}
