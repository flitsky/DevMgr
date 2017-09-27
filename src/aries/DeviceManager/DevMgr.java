package aries.DeviceManager;

import java.io.FileNotFoundException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import aries.MessageProcess.ObservableRespMsg;
import aries.MessageProcess.ObserverDiscoveryDevice;
import aries.MessageProcess.ObserverDiscoveryResource;
import aries.MessageProcess.ObserverGet;
import aries.MessageProcess.ObserverPost;
import aries.MessageProcess.ObserverSignIn;
import aries.MessageProcess.ObserverSignOut;
import aries.MessageProcess.ObserverSignUp;
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
				// error
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
			new ObserverSignUp(msg, ObsResp, 10);
			break;
		case "signin":
			new ObserverSignIn(msg, ObsResp);
			break;
		case "signout":
			new ObserverSignOut(msg, ObsResp);
			break;
		case "dis_dev":
			new ObserverDiscoveryDevice(msg, ObsResp);
			break;
		case "dis_res":
			new ObserverDiscoveryResource(msg, ObsResp);
			break;
		case "get":
			new ObserverGet(msg, ObsResp);
			break;
		case "post":
			new ObserverPost(msg, ObsResp);
			break;
		case "observe":
			// break;
		case "obs_can":
			// break;
		default:
			jo.put("body", new JSONObject().put("status", 404));
			sndProducer.PushToSendQueue(jo.getString("org"), jo.getString("msgid"), MsgType.Response,
					jo.getString("workcode"), jo.get("body").toString());
			break;
		}
	}
}
