package io.dase.network;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.dase.network.DamqRcvConsumer.*;
//import io.dase.network.Damq.ModuleType;

public class App 
{
  private static final Logger logger = LoggerFactory.getLogger(App.class);
  
  public static void main(String[] args) {   
    logger.debug("Hola");
    
    try {
      /* 4가지 중 자신의 module type을 파라미터로 넘겨줍니다.
       * ModuleType.COMCLNT | ModuleType.DEVMGR | ModuleType.APP | ModuleType.NGIN
       */      
      Runnable damqRun = new DevMgr(ModuleType.DEVMGR);
      Thread damqThread = new Thread(damqRun);    
      damqThread.start();
      
      //test
      DamqSndProducer sndProducer = DamqSndProducer.getInstance();
      for (int i = 0; i < 10; i++) {
        String buf = String.format("{\"destination\":\"%s\",\"uuid\":\"%s\",\"msgbody\":{\"index\":\"%d\",\"value\":\"200 OK\"}}", "devmgr", sndProducer.getUUID(), i);
        sndProducer.PushToSendQueue(buf);
        //logger.debug("test : " + buf);
      }
      
      sndProducer.PushToSendQueue("{\"command\":\"lbxjtyf\"}");
      
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
