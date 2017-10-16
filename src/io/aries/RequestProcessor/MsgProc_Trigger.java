package io.aries.RequestProcessor;

import java.util.Arrays;
import java.util.List;

import org.json.JSONObject;

import io.aries.DeviceManager.Message;
import io.aries.TriggerManager.TrgMgr;

public class MsgProc_Trigger extends CmdProcessTimerTaskObserver {

	public MsgProc_Trigger(Message msg, ObservableRespMsg observable) {
		super(msg, observable);
		// TODO Auto-generated constructor stub
	}

	public MsgProc_Trigger(Message msg, ObservableRespMsg observable, int expirationSec) {
		super(msg, observable, expirationSec);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void recvdReqProc(JSONObject recvdReq) {
		String[] rsrcIDs;
		String triggerName;
		List<Object> rsrcIDsList = recvdReq.getJSONObject("body").getJSONArray("rsrcids").toList();
		if (rsrcIDsList.size() > 0) {
			rsrcIDsList.toArray(rsrcIDs = new String[rsrcIDsList.size()]);
			System.out.println("recvdReqProc()   rsrcIDs = " + Arrays.toString(rsrcIDs));
			triggerName = recvdReq.getJSONObject("body").getString("triggername");
			if (TrgMgr.getInstance().addTrigger(triggerName, rsrcIDs)) {
				sendRespJO.put("body", new JSONObject().put("status", 200));
			} else {
				sendRespJO.put("body", new JSONObject().put("status", 404));
				sendRespJO.put("body", new JSONObject().put("reason", "Trigger Name is already exist"));
			}
		} else {
			System.out.println("recvdReqProc()   error... rsrcIDsList.size ");
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
