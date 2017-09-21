package io.dase.network;

import java.util.concurrent.BlockingQueue;
import java.net.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DamqRcvProducer implements Runnable {
  private static final Logger logger = LoggerFactory.getLogger(DamqRcvProducer.class);
  private BlockingQueue<DamqMsg> recvQueue;
  DamqSndProducer sndProducer = DamqSndProducer.getInstance();
  
  public DamqRcvProducer(BlockingQueue<DamqMsg> q) {
    this.recvQueue = q;
  }
  
  public void run() {
    logger.debug("DamqRcvProducer begins.");
    
    byte[] rcvBuf = new byte[131072];
    DatagramSocket udpSocket = null;
    
    try {      
      udpSocket = new DatagramSocket(DamqRcvConsumer.ModulePort[DamqRcvConsumer.myModuleType]);
    } catch (Exception e) {
      logger.error("Error : " + e.getMessage());
    }
    
    while (true) {
      try {
        DatagramPacket packet = new DatagramPacket(rcvBuf, rcvBuf.length);
        udpSocket.setSoTimeout(15000);
        udpSocket.receive(packet);
        udpSocket.setSoTimeout(0);
        String rcvStr = new String(rcvBuf, 0, packet.getLength());
        recvQueue.put(new DamqMsg(rcvStr));
        if (sndProducer.IsTerminateSignal(rcvStr)) {
          logger.debug("rcvProducer: SIG_TERM");
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
    logger.debug("DamqRcvProducer ends.");  
  }
}
