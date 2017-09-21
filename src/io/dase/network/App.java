package io.dase.network;

import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import aries.DeviceManager.DevMgr;
//import io.dase.network.Damq.ModuleType;
import io.dase.network.DamqRcvConsumer.ModuleType;
import io.dase.network.DamqRcvConsumer.MsgType;

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
					System.out.println("CMD : " + s);
					MsgType msgType;
					if (s.contains("\"msgtype\":\"res\""))
						msgType = MsgType.Response;
					else
						msgType = MsgType.Request;
					String workcode = "";
										
					sndProducer.PushToSendQueue(ModuleType.DEVMGR, msgType, workcode, s);
				}
				scanner.close();
				System.out.println("Bye~~");
				// [Recv Resp] sign up
				// {"org":"common","dst":"devmgr","date":"19800202","msgid":"msgid1234","msgtype":"res","workcode":"signup","body":{"status":200,"uid":"22883d77-4ab8-4b80-b75b-74774868b484","accesstoken":"e00d0d9ec36095d749a350dab04b5a8c1b94e136","expiresin":-1}}
				// [Recv Resp] Discovery Device Result
				// {"org":"common","dst":"devmgr","date":"19800202","msgid":"msgid1234","msgtype":"res","workcode":"dis_dev","body":{"status":200,"devices":[{"dev_id":"c31e8fa3-b524-0e6b-2489-77760c3ca37b","dev_name":"THU
				// Light","spec_ver":"ocf.1.1.0","dev_type":["oic.wk.d",
				// "oic.d.light"],"host_addr":"coap:\/\/[fe80::c4a8:5af:7d0e:f40e%25wlan0]:49244"}]}}}

				// {"org":"common","dst":"devmgr","date":"19800202","msgid":"msgid1234","msgtype":"res","workcode":"observe","body":{"status":200,"res_id":"/oic/route/6341cb6f-2179-55a3-3732-c3ffbad1be68/power/0","properties":[{"prop_name":"value","prop_type":"boolean","prop_value":[true,false],"read_only":false}]}}}
				// {"org":"common","dst":"devmgr","date":"19800202","msgid":"msgid1234","msgtype":"res","workcode":"observe","body":{"status":200,"res_id":"/oic/route/6341cb6f-2179-55a3-3732-c3ffbad1be68/power/0","properties":[{"prop_name":"value","prop_type":"integer","prop_value":[1004],"read_only":false}]}}}
				// {"org":"common","dst":"devmgr","date":"19800202","msgid":"msgid1234","msgtype":"res","workcode":"observe","body":{"status":200,"res_id":"/oic/route/6341cb6f-2179-55a3-3732-c3ffbad1be68/power/0","properties":[{"prop_name":"value","prop_type":"string","prop_value":["string
				// value"],"read_only":false}]}}}
				// {"org":"common","dst":"devmgr","date":"19800202","msgid":"msgid1234","msgtype":"res","workcode":"observe","body":{"status":200,"res_id":"/oic/route/6341cb6f-2179-55a3-3732-c3ffbad1be68/power/0","properties":[{"prop_name":"value","prop_type":"double","prop_value":[1004.0],"read_only":false}]}}}
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
