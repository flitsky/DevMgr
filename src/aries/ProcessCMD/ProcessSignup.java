package aries.ProcessCMD;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.dase.network.DamqMsg;
import io.dase.network.DamqSndProducer;

public class ProcessSignup {

	private ProcessSignup() {
	}

	private static class Singleton {
		private static final ProcessSignup instance = new ProcessSignup();
	}

	public static ProcessSignup getInstance() {
		return Singleton.instance;
	}

	private static final Logger logger = LoggerFactory.getLogger(DamqSndProducer.class);

	public void SendRequest(String buf) {
		try {
			//sendQueue.put(new DamqMsg(buf));
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}
}
