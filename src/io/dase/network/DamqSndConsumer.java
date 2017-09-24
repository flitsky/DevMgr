package io.dase.network;

import java.util.concurrent.BlockingQueue;
import java.net.*;

import org.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
        JSONObject jo = new JSONObject(sendStr);        
        String destination = jo.get("dst").toString().toLowerCase();        
        sendBuf = sendStr.getBytes();
        InetAddress destAddress = InetAddress.getByName(sndProducer.GetModuleAddress(destination));
        int destPort = sndProducer.GetModulePort(destination);                         
        DatagramPacket packet = new DatagramPacket(sendBuf, sendBuf.length, destAddress, destPort);
        System.out.println(" ###### sendConsumer   sendStr : " + sendStr + " destAddress : " + destAddress + " destPort : " +destPort);
        udpSocket.send(packet);
        if (sndProducer.IsTerminateSignal(sendStr)) {
          logger.debug("sndConsumer: SIG_TERM");
          break;
        }    
        Thread.sleep(0);
      } catch (Exception e) {
        if (e instanceof InterruptedException) {
          logger.error("3rror : " + e.getMessage());
        } else if (e instanceof SocketException) {
          logger.error("err0r : " + e.getMessage());  
        } else {
          logger.error("err0r : " + e.getMessage());
        }
        logger.debug("gocha!");
        continue;
      }       
    } 
    
    udpSocket.close();
    logger.debug("DamqSndConsumer ends.");
  }
}
