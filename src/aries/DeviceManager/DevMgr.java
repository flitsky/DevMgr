package aries.DeviceManager;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import aries.ProcessCMD.ProcessCommand;
import io.dase.network.DamqRcvConsumer;

public class DevMgr extends DamqRcvConsumer {
	private static final Logger logger = LoggerFactory.getLogger(DevMgr.class);

	public DevMgr() {
		super();
	}

	public DevMgr(ModuleType m) {
		super(m);
	}

	protected void MainProc(String org, String dst, String dateTime, String msgId, String msgType, String workCode,
			String msgBody) {
		logger.debug("DevMgr begins.");
		// DamqMsg msg = null;

		BlockingQueue<Message> queueReq = new ArrayBlockingQueue<>(10);
		BlockingQueue<Message> queueResp = new ArrayBlockingQueue<>(100);
		Thread processCommand = new Thread(new ProcessCommand(queueReq, queueResp));
		processCommand.start();

		while (true) {
			try {
				String rcvBuf = rcvQueue.take().getMsg();
				if (rcvBuf.equals("{\"command\":\"lbxjtyf\"}"))
					break;

				JSONObject jo = new JSONObject(rcvBuf);
				String destination = jo.getString("destination").toLowerCase();
				String uuid = jo.getString("uuid").toLowerCase();
				String msgbody = jo.getString("msgbody").toLowerCase();

				logger.debug("rcvQueue.size() : " + rcvQueue.size());
				logger.debug("dest : " + destination);
				logger.debug("uuid : " + uuid);
				logger.debug("body : " + msgbody);

				// [a2d] recv req msg : sign up
				// [d2c] make req msg and send
				// [c2d] recv resp
				// [d2a] make resp msg and send
				Message msg = new Message(msgbody);
				if (destination == "devmgr") {
					queueResp.put(msg);
				} else {
					queueReq.put(msg);
				}

				Thread.yield();
			} catch (Exception e) {
				if (e instanceof InterruptedException) {
					logger.error("error: " + e.getMessage());
				} else {
					logger.error("err0r: " + e.getMessage());
				}
				continue;
			}
		}
		logger.debug("EngineMain ends.");
	}

	protected void ExceptionProc(Exception e) {
		if (e instanceof InterruptedException) {
			logger.error("error: " + e.getMessage());
		} else {
			logger.error("err0r: " + e.getMessage());
		}
	}
}
