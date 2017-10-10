package aries.DeviceManager;

import java.util.Scanner;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
					if (s.isEmpty())
						continue;

					JSONObject jo = new JSONObject(s);
					MsgType msgType;
					if (jo.getString("msgtype").equals("res"))
						msgType = MsgType.Response;
					else
						msgType = MsgType.Request;

					ModuleType moduleType = ModuleType.DEVMGR;
					switch (jo.getString("dst")) {
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
						// moduleType error proc
						break;
					}

					System.out.println(" ### sndProducer >>>>> workcode : " + jo.getString("workcode"));
					if (jo.getString("msgid").equals("")) {
						sndProducer.PushToSendQueue(moduleType, msgType, jo.getString("workcode"),
								jo.get("body").toString());
					} else {
						// sndProducer.PushToSendQueue(recvd.dst, recvd.msgid, msgType, recvd.workcode,
						// msg);
						sndProducer.PushToSendQueue(jo.getString("org"), jo.getString("dst"), jo.getString("msgid"),
								msgType, jo.getString("workcode"), jo.get("body").toString());
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
}
