package Aries.DeviceManager;

import java.io.FileNotFoundException;
import java.util.concurrent.BlockingQueue;

import org.apache.log4j.Logger;
import org.json.JSONObject;

import Aries.interoperate.Schema0Cmd;

public class ProcessCommand implements Runnable {
	static Logger logger = Logger.getLogger("ProcessCommand.class");

	private BlockingQueue<Message> queue;
	private String command;

	public ProcessCommand(BlockingQueue<Message> q) {
		this.queue = q;
	}

	@Override
	public void run() {
		Message msg;
		while (true) {
			try {
				if ((msg = queue.take()).getMsg() != "") {
					Thread.sleep(30);
					this.command = msg.getMsg();
					System.out.println(Thread.currentThread().getName() + " Start.");
					processCommand();
					System.out.println(Thread.currentThread().getName() + " End.");
				}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private void processCommand() {
		System.out.println("command = " + this.command);
		JSONObject receivedJsonObj = new JSONObject(this.command);
		Schema0Cmd recvSchema0Cmd = JsonObj2EntityX(receivedJsonObj);

		JSONObject jsonExportBody;
		try {
			jsonExportBody = recvSchema0Cmd.exportToJson();
			if (jsonExportBody.toString().equals(receivedJsonObj.toString()))
				logger.debug(" [test code] ok.. receivedJson == imported data ");
			else
				logger.error(" [test code] error.. imported data is not equal = " + jsonExportBody.toString());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	static Schema0Cmd JsonObj2EntityX(JSONObject receivedJsonObj) {
		Schema0Cmd recvSchema0Cmd = new Schema0Cmd();
		// Import jsonObject
		try {
			recvSchema0Cmd.importFromJson(receivedJsonObj);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return recvSchema0Cmd;
	}

	@Override
	public String toString() {
		return this.command;
	}
}
