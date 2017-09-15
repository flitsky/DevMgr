package Aries.DeviceManager;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import org.apache.log4j.Logger;

import Aries.socket.UdpClient;
import Aries.socket.UdpListener;

public class DevMgr {
	static Logger logger = Logger.getLogger("DevMgr.class");
	
	public static void main(String[] args) {
		BlockingQueue<Message> queue = new ArrayBlockingQueue<>(10);
		
		Thread udpListener = new Thread(new UdpListener(queue));
		udpListener.start();
		
		Thread processCommand = new Thread(new ProcessCommand(queue));
		processCommand.start();

		// test command input thread
		Thread tUdpClient = new Thread(new UdpClient());
		tUdpClient.start();
		//[Recv Resp] sign up
		//{"type":"res","dir":"c2d","work_code":"signup","body":{"status":200,"uid":"22883d77-4ab8-4b80-b75b-74774868b484","accesstoken":"e00d0d9ec36095d749a350dab04b5a8c1b94e136","expiresin":-1}}
		//[Recv Resp] Discovery Device Result
		//{"type":"res","dir":"c2d","work_code":"dis_dev","body":{"status":200,"devices":[{"dev_id":"c31e8fa3-b524-0e6b-2489-77760c3ca37b","dev_name":"THU Light","spec_ver":"ocf.1.1.0","dev_type":["oic.wk.d", "oic.d.light"],"host_addr":"coap:\/\/[fe80::c4a8:5af:7d0e:f40e%25wlan0]:49244"}]}}
		
		//{"type":"res","dir":"c2d","work_code":"observe","body":{"status":200,"res_id":"/oic/route/6341cb6f-2179-55a3-3732-c3ffbad1be68/power/0","properties":[{"prop_name":"value","prop_type":"boolean","prop_value":[true, false],"read_only":false}]}}
		//{"type":"res","dir":"c2d","work_code":"observe","body":{"status":200,"res_id":"/oic/route/6341cb6f-2179-55a3-3732-c3ffbad1be68/power/0","properties":[{"prop_name":"value","prop_type":"integer","prop_value":[1004],"read_only":false}]}}
		//{"type":"res","dir":"c2d","work_code":"observe","body":{"status":200,"res_id":"/oic/route/6341cb6f-2179-55a3-3732-c3ffbad1be68/power/0","properties":[{"prop_name":"value","prop_type":"string","prop_value":["string value"],"read_only":false}]}}
		//{"type":"res","dir":"c2d","work_code":"observe","body":{"status":200,"res_id":"/oic/route/6341cb6f-2179-55a3-3732-c3ffbad1be68/power/0","properties":[{"prop_name":"value","prop_type":"double","prop_value":[1004.0],"read_only":false}]}}
	}
}
