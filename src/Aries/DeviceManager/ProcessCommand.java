package Aries.DeviceManager;

import java.io.FileNotFoundException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.concurrent.BlockingQueue;

import org.apache.log4j.Logger;
import org.json.JSONObject;

import Aries.interoperate.Schema0Cmd;
import Aries.interoperate.Schema1Body;

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

		switch (recvSchema0Cmd.direction) {
		case "a2d":
			if (recvSchema0Cmd.type.equals("req")) {
				Schema0Cmd sendReqSchema0Cmd = new Schema0Cmd();
				if (recvSchema0Cmd.work_code.equals("discovery")) {
					sendReqSchema0Cmd.type = "req";
					sendReqSchema0Cmd.direction = "d2c";
					sendReqSchema0Cmd.work_code = "dis_dev";
					// send command ...
					sendCommand(sendReqSchema0Cmd);
				} else if (recvSchema0Cmd.work_code.equals("dis_res")) {
					sendReqSchema0Cmd.type = "req";
					sendReqSchema0Cmd.direction = "d2c";
					sendReqSchema0Cmd.work_code = "dis_res";
					sendReqSchema0Cmd.body = new Schema1Body();
					sendReqSchema0Cmd.body.device_id = "6341cb6f-2179-55a3-3732-c3ffbad1be68";
					// send command ...
					sendCommand(sendReqSchema0Cmd);
				}
			} else {

			}
			break;
		case "e2d":
			if (recvSchema0Cmd.type.equals("req")) {

			} else {

			}
			break;
		case "c2d":
			if (recvSchema0Cmd.type.equals("req")) {
				switch (recvSchema0Cmd.work_code) {
				case "observe":
					break;
				default:
					break;
				}

			} else {
				switch (recvSchema0Cmd.work_code) {
				case "signup":
					break;
				case "signin":
					break;
				case "signout":
					break;
				case "dis_dev":
					if (recvSchema0Cmd.body.status == 200) {
						logger.debug("Send Discovery Device result to DB.");
					}
					break;
				case "dis_res":
					if (recvSchema0Cmd.body.status == 200) {
						logger.debug("Send Discovery Resource result to DB.");
					}
					break;
				case "get":
					break;
				case "post":
					break;
				case "observe":
					break;
				case "obs_can":
					break;
				default:
					break;
				}
			}
			break;
		default:
			break;
		}
	}

	static void sendCommand(Schema0Cmd cmd) {
		// Export to jsonObject
		try {
			JSONObject JsonObj4Req = cmd.exportToJson();
			// check exported data
			logger.debug(" >>> sendCommand = " + JsonObj4Req.toString());

			// send command to ...
			DatagramSocket socket = new DatagramSocket();

			String s = JsonObj4Req.toString();
			byte[] buf = s.getBytes();
			InetAddress address = InetAddress.getByName("localhost");
			DatagramPacket packet = new DatagramPacket(buf, buf.length, address, 5000);
			socket.send(packet);
			socket.close();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	static Schema0Cmd JsonObj2EntityX(JSONObject receivedJsonObj) {
		Schema0Cmd recvSchema0Cmd = new Schema0Cmd();
		// Import from jsonObject
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
