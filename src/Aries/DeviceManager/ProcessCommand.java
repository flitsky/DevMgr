package Aries.DeviceManager;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Scanner;
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

		switch (recvSchema0Cmd.direction) {
		case "a2d":
			if (recvSchema0Cmd.type.equals("req")) {
				if (recvSchema0Cmd.work_code.equals("discovery")) {
					try {
						Schema0Cmd sendReqSchema0Cmd = new Schema0Cmd();
						sendReqSchema0Cmd.type = "req";
						sendReqSchema0Cmd.direction = "d2c";
						sendReqSchema0Cmd.work_code = "dis_dev";
						// Export jsonObject
						JSONObject JsonObj4Req = sendReqSchema0Cmd.exportToJson();
						// check exported data
						logger.debug(" send cmd = " + JsonObj4Req.toString());

						// send command to ...
						// to do ...
						DatagramSocket socket = new DatagramSocket();

						String s = JsonObj4Req.toString();
						byte[] buf = s.getBytes();
						InetAddress address = InetAddress.getByName("localhost");
						DatagramPacket packet = new DatagramPacket(buf, buf.length, address, 5000);
						socket.send(packet);
						socket.close();
					} catch (SocketException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (UnknownHostException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
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

			} else {
				if (recvSchema0Cmd.work_code.equals("dis_dev")) {
					if (recvSchema0Cmd.body.status == 200) {
						logger.debug("Send Discovery Device result to DB.");
					}
				}
			}
			break;
		default:
			break;
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
