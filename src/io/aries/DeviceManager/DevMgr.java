package io.aries.DeviceManager;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.aries.RequestProcessor.MsgProc_DiscoveryDevice;
import io.aries.RequestProcessor.MsgProc_DiscoveryResource;
import io.aries.RequestProcessor.MsgProc_Get;
import io.aries.RequestProcessor.MsgProc_Observe;
import io.aries.RequestProcessor.MsgProc_ObserveCancel;
import io.aries.RequestProcessor.MsgProc_Post;
import io.aries.RequestProcessor.MsgProc_SignIn;
import io.aries.RequestProcessor.MsgProc_SignOut;
import io.aries.RequestProcessor.MsgProc_SignUp;
import io.aries.RequestProcessor.MsgProc_Trigger;
import io.aries.RequestProcessor.MsgProc_TriggerCancel;
import io.aries.RequestProcessor.ObservableRespMsg;
import io.aries.RequestProcessor.CmdProcessTimerTaskObserver.MsgReqType;
import io.aries.ResourceManager.RsrcMgr;
import io.aries.TriggerManager.TrgMgr;
import io.dase.network.DamqRcvConsumer;

public class DevMgr extends DamqRcvConsumer {
	private static final Logger logger = LoggerFactory.getLogger(DevMgr.class);
	// BlockingQueue<Message> queueReq = new ArrayBlockingQueue<>(100);
	BlockingQueue<Message> queueResp = new ArrayBlockingQueue<>(100);
	// private ObservableReqMsg ObsReq = new ObservableReqMsg(queueReq);
	private ObservableRespMsg ObsResp = new ObservableRespMsg(queueResp);

	public DevMgr() {
		super();
	}

	public DevMgr(ModuleType m) {
		super(m);
	}

	protected void MainProc(String org, String dst, String dateTime, String msgId, String msgType, String workCode,
			String msgBody) {
		logger.debug("origin:" + org + " & msgid:" + msgId + " & body:" + msgBody);

		// integrate msgBody with header info
		JSONObject jo = new JSONObject();
		jo.put("org", org);
		jo.put("dst", dst);
		jo.put("date", dateTime);
		jo.put("msgid", msgId);
		jo.put("msgtype", msgType);
		jo.put("workcode", workCode);
		jo.put("body", new JSONObject(msgBody));

		Message msg = new Message(msgId, jo.toString());
		try {
			if (msgType.equals("req")) {
				requestProcess(msg);
			} else if (msgType.equals("res")) {
				queueResp.put(msg);
				ObsResp.work();
			} else {
				// msgType error proc
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	protected void ExceptionProc(Exception e) {
		if (e instanceof InterruptedException) {
			logger.error("error: " + e.getMessage());
		} else {
			logger.error("err0r: " + e.getMessage());
		}
	}

	private void requestProcess(Message msg) {
		JSONObject jo = new JSONObject(msg.getMsg());

		switch (jo.getString("workcode")) {
		case "signup":
			new MsgProc_SignUp(msg, ObsResp, 10);
			break;
		case "signin":
			new MsgProc_SignIn(msg, ObsResp);
			break;
		case "signout":
			new MsgProc_SignOut(msg, ObsResp);
			break;
		case "dis_dev":
			new MsgProc_DiscoveryDevice(msg, ObsResp);
			break;
		case "dis_res":
			new MsgProc_DiscoveryResource(msg, ObsResp);
			break;
		case "get":
			new MsgProc_Get(msg, ObsResp);
			break;
		case "post":
			new MsgProc_Post(msg, ObsResp);
			break;
		case "observe":
			new MsgProc_Observe(msg, ObsResp, 5, MsgReqType.Constantly);
			break;
		case "obs_can":
			new MsgProc_ObserveCancel(msg, ObsResp);
			break;
		case "trigger_add":
			new MsgProc_Trigger(msg, null, 0);
			break;
		case "trigger_cancel":
			new MsgProc_TriggerCancel(msg, null, 0);
			break;
		case "test":
			break;
		default:
			jo.put("body", new JSONObject().put("status", 404));
			sndProducer.PushToSendQueue(jo.getString("org"), jo.getString("msgid"), MsgType.Response,
					jo.getString("workcode"), jo.get("body").toString());
			break;
		}
	}
}
