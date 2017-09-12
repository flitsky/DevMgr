package Aries.socket;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.concurrent.BlockingQueue;

import Aries.DeviceManager.Message;

public class UdpListener implements Runnable {
	DatagramSocket socket = null;
	private BlockingQueue<Message> queue;
	
	public UdpListener(BlockingQueue<Message> q) {
		this.queue = q;
	}
	
	@Override
	public void run() {
		try {
			socket = new DatagramSocket(5000);
			while (true) {
				byte[] buf = new byte[32768];
				DatagramPacket packet = new DatagramPacket(buf, buf.length);
				socket.receive(packet);
				System.out.println(packet.getAddress()+": "+new String(buf));
				
				Message msg = new Message(new String(buf));
				queue.put(msg);
				System.out.println("Produced " + msg.getMsg());
			}
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		socket.close();
	}
}
