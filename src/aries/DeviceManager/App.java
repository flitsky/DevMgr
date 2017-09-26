package aries.DeviceManager;

import java.io.FileNotFoundException;
import java.util.Scanner;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import aries.interoperate.Schema0Header;
//import io.dase.network.Damq.ModuleType;
import io.dase.network.DamqRcvConsumer.ModuleType;
import io.dase.network.DamqRcvConsumer.MsgType;
import io.dase.network.DamqSndProducer;

public class App {
	private static final Logger logger = LoggerFactory.getLogger(App.class);
	static int TEST_CODE = 2;

	public static void main(String[] args) {
		logger.debug("Hola");

		try {
			/*
			 * 4가지 중 자신의 module type을 파라미터로 넘겨줍니다. ModuleType.COMCLNT | ModuleType.DEVMGR |
			 * ModuleType.APP | ModuleType.NGIN
			 */
			Runnable damqRun = new DevMgr(ModuleType.DEVMGR);
			Thread damqThread = new Thread(damqRun);
			damqThread.start();

			if (TEST_CODE == 1) {
				// test
				DamqSndProducer sndProducer = DamqSndProducer.getInstance();
				for (int i = 0; i < 10; i++) {
					String buf = String.format("{\"index\":\"%d\",\"value\":\"200 OK\"}", i);
					sndProducer.PushToSendQueue(ModuleType.DEVMGR, MsgType.Request, "signin", buf);
				}
				sndProducer.SendExitSignal();
			} else if (TEST_CODE == 2) {
				// test command input
				DamqSndProducer sndProducer = DamqSndProducer.getInstance();
				Scanner scanner = new Scanner(System.in);
				while (true) {
					System.out.println("Input CMD:");

					String s = scanner.nextLine();
					if (s.equals("exit"))
						break;

					Schema0Header recvd = String2JsonObj2EntityX(s);
					MsgType msgType;
					if (recvd.msgtype.equals("res"))
						msgType = MsgType.Response;
					else
						msgType = MsgType.Request;

					ModuleType moduleType = ModuleType.DEVMGR;
					switch (recvd.dst) {
					case "app":
						moduleType = ModuleType.APP;
						break;
					case "ngin":
						moduleType = ModuleType.NGIN;
						break;
					case "devmgr":
						moduleType = ModuleType.DEVMGR;
						break;
					case "common":
						moduleType = ModuleType.COMCLNT;
						break;
					default:
						break;
					}
					String msg = recvd.body.exportToString();
					System.out.println(" ### sndProducer >>>>> workcode : " + recvd.workcode);
					if (recvd.msgid.equals("")) {
						sndProducer.PushToSendQueue(moduleType, msgType, recvd.workcode, msg);
					} else {
						// PushToSendQueue(String dest, String msgId, MsgType msgType, String workCode,
						// String msgBody)
						// sndProducer.PushToSendQueue(recvd.dst, recvd.msgid, msgType, recvd.workcode,
						// msg);
						sndProducer.PushToSendQueue(recvd.org, recvd.dst, recvd.msgid, msgType, recvd.workcode, msg);
					}
				}
				scanner.close();
				System.out.println("Bye~~");
			}

			damqThread.join();
		} catch (Exception e) {
			if (e instanceof InterruptedException) {
				logger.error("main error: " + e.getMessage());
			} else {
				logger.error("ma1n error: " + e.getMessage());
			}
		}

		logger.debug("Adios");

		System.exit(0);
	}

	static Schema0Header String2JsonObj2EntityX(String str) {
		if (str.isEmpty()) {
			return null;
		}
		JSONObject JsonObj = new JSONObject(str);
		Schema0Header recvSchema0Cmd = new Schema0Header();
		// Import from jsonObject
		try {
			recvSchema0Cmd.importFromJson(JsonObj);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return recvSchema0Cmd;
	}

}
