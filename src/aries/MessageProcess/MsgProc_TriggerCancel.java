package aries.MessageProcess;

import org.json.JSONObject;

import aries.DeviceManager.Message;
import aries.TriggerManager.TrgMgr;

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
		if(TrgMgr.getInstance().deleteTrigger(triggerName)) {
			sendRespJO.put("body", new JSONObject().put("status", 200));
		} else {
			System.out.println("recvdReqProc()   error... that trigger name is not exist. ");
			sendRespJO.put("body", new JSONObject().put("status", 404));
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
