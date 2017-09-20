package aries.Socket;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Scanner;

public class UdpClient implements Runnable {

	@Override
	public void run() {
		DatagramSocket socket = null;
		Scanner scanner = new Scanner(System.in);
		while (true) {
			try {
				socket = new DatagramSocket();
				System.out.println("Input CMD:");
				String s = scanner.nextLine();
				if (s.equals("exit"))
					break;
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
			}
		}
		scanner.close();
		System.out.println("Bye~~");
	}
}
