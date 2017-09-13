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
	}
}
