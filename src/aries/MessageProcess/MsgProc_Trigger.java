package aries.MessageProcess;

import java.util.Arrays;
import java.util.List;

import org.json.JSONObject;

import aries.DeviceManager.Message;
import aries.TriggerManager.TrgMgr;

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
		List<Object> rsrcIDsList = recvdReq.getJSONObject("body").getJSONArray("rsrcids").toList();
		if (rsrcIDsList.size() > 0) {
			rsrcIDsList.toArray(rsrcIDs = new String[rsrcIDsList.size()]);
			System.out.println("recvdReqProc()   rsrcIDs = " + Arrays.toString(rsrcIDs));
			TrgMgr.getInstance().addTrigger("test trigger1", rsrcIDs);
		} else {
			System.out.println("recvdReqProc()   error... rsrcIDsList.size ");
		}

		// sendRespJO.put("dst", recvdReq.getString("org"));
		// sendRespProc(sendRespJO);
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
