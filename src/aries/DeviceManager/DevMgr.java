package aries.DeviceManager;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import aries.ProcessCMD.ProcessCommand;
import aries.interoperate.Schema0Header;
import aries.interoperate.Schema1Body;
import io.dase.network.DamqRcvConsumer;

public class DevMgr extends DamqRcvConsumer {
	private static final Logger logger = LoggerFactory.getLogger(DevMgr.class);
	BlockingQueue<Message> queueReq = new ArrayBlockingQueue<>(100);
	BlockingQueue<Message> queueResp = new ArrayBlockingQueue<>(100);

	public DevMgr() {
		super();
	}

	public DevMgr(ModuleType m) {
		super(m);

		Thread processCommand = new Thread(new ProcessCommand(queueReq, queueResp));
		processCommand.start();
	}

	protected void MainProc(String org, String dst, String dateTime, String msgId, String msgType, String workCode,
			String msgBody) {
		logger.debug("origin:" + org + " & msgid:" + msgId + " & body:" + msgBody);

		// 헤더 포함해서 던져주기위해서...
		// ===========> 정리 필요
		Schema0Header cmd = new Schema0Header();
		cmd.org = org;
		cmd.dst = dst;
		cmd.date = dateTime;
		cmd.msgid = msgId;
		cmd.msgtype = msgType;
		cmd.workcode = workCode;
		Schema1Body body = new Schema1Body();
		JSONObject JsonObj = new JSONObject(msgBody);
		try {
			body.importFromJson(JsonObj);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		cmd.body = body;
		String msgFull = null;
		try {
			msgFull = cmd.exportToString();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		// <=========== 정리 필요

		// Message msg = new Message(msgBody); // 헤더 포함해서 던져주기위해서...
		Message msg = new Message(msgFull); // 헤더 포함해서 던져주기위해서...
		try {
			if (msgType.equals("res")) {
				queueResp.put(msg);
			} else {
				queueReq.put(msg);
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
}
