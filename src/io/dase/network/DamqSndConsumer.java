package io.dase.network;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.concurrent.BlockingQueue;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//import io.dase.network.DamqRcvConsumer.*;

public class DamqSndConsumer implements Runnable {
  private static final Logger logger = LoggerFactory.getLogger(DamqSndConsumer.class);
  private BlockingQueue<DamqMsg> sendQueue;
  DamqSndProducer sndProducer = DamqSndProducer.getInstance();
  
  public DamqSndConsumer(BlockingQueue<DamqMsg> sq) {
    this.sendQueue = sq;   
  } 
  
  public void run() {
    logger.debug("DamqSndConsumer begins.");
    
    byte[] sendBuf = new byte[131072];
    DatagramSocket udpSocket = null;
    
    try {
      udpSocket = new DatagramSocket();
    } catch (Exception e) {
      logger.error("Error : " + e.getMessage());
    } 
    
    while (true) {
      try {
        String sendStr = sendQueue.take().getMsg();        
        if (sendStr.equals("")) {
          continue;
        } else if (sendStr.equals("{\"command\":\"lbxjtyf\"}")) {
          logger.debug("DamqSndConsumer : terminate signal has arrived."); 
          sendBuf = sendStr.getBytes();
          InetAddress destAddress = InetAddress.getByName(DamqRcvConsumer.ModuleAddress[DamqRcvConsumer.myModuleType]);
          int destPort = DamqRcvConsumer.ModulePort[DamqRcvConsumer.myModuleType];                         
          DatagramPacket packet = new DatagramPacket(sendBuf, sendBuf.length, destAddress, destPort);
          udpSocket.send(packet);          
          break;
        } else {
        	logger.debug(" check sendStr : " + sendStr);
          JSONObject jo = new JSONObject(sendStr);
          String destination = jo.getString("destination").toLowerCase();        
          sendBuf = sendStr.getBytes();
          InetAddress destAddress = InetAddress.getByName(sndProducer.GetModuleAddress(destination));
          int destPort = sndProducer.GetModulePort(destination);                         
          DatagramPacket packet = new DatagramPacket(sendBuf, sendBuf.length, destAddress, destPort);
          udpSocket.send(packet);
        }
        Thread.yield(); 
      } catch (Exception e) {
        if (e instanceof InterruptedException) {
          logger.error("3rror : " + e.getMessage());
        } else if (e instanceof SocketException) {
          logger.error("err0r : " + e.getMessage());  
        } else {
          logger.error("err0r : " + e.getMessage());
        }
        continue;
      }       
    }
    
    udpSocket.close();
    logger.debug("DamqSndConsumer ends.");
  }
}
