package aries.DeviceManager;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import aries.ProcessCMD.ProcessCommand;
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

		Message msg = new Message(msgBody);
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
