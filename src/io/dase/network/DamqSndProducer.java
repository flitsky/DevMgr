package io.dase.network;

import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.net.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.uuid.EthernetAddress;
import com.fasterxml.uuid.Generators;
import com.fasterxml.uuid.NoArgGenerator;

import io.dase.network.DamqRcvConsumer.*;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

public class DamqSndProducer {
  
  private DamqSndProducer () {}
  private static class Singleton {
    private static final DamqSndProducer instance = new DamqSndProducer();
  }
  public static DamqSndProducer getInstance () {
    return Singleton.instance;
  }
  
  private static final Logger logger = LoggerFactory.getLogger(DamqSndProducer.class);
  private BlockingQueue<DamqMsg> sendQueue;
  private boolean isInitialized = false;
  
  public void InitDamqSndProducer(BlockingQueue<DamqMsg> q) {
    logger.error("InitDamqSndProducer");
    this.sendQueue = q;
    isInitialized = true;
  }
  
  public String GetModuleAddress(String moduleName) {
    String val = "";
    
    if (moduleName.equals("ngin")) {
      val = DamqRcvConsumer.ModuleAddress[ModuleType.NGIN.getValue()];
    } else if (moduleName.equals("devmgr")) {
      val = DamqRcvConsumer.ModuleAddress[ModuleType.DEVMGR.getValue()];
    } else if (moduleName.equals("comclnt")) {
      val = DamqRcvConsumer.ModuleAddress[ModuleType.COMCLNT.getValue()];
    } else if (moduleName.equals("app")) {
      val = DamqRcvConsumer.ModuleAddress[ModuleType.APP.getValue()];
    }
    
    return val;     
  }
  
  public int GetModulePort(String moduleName) {
    int val = 0;
    
    if (moduleName.equals("ngin")) {
      val = DamqRcvConsumer.ModulePort[ModuleType.NGIN.getValue()];
    } else if (moduleName.equals("devmgr")) {
      val = DamqRcvConsumer.ModulePort[ModuleType.DEVMGR.getValue()];
    } else if (moduleName.equals("comclnt")) {
      val = DamqRcvConsumer.ModulePort[ModuleType.COMCLNT.getValue()];
    } else if (moduleName.equals("app")) {
      val = DamqRcvConsumer.ModulePort[ModuleType.APP.getValue()];
    }
    
    return val;    
  }
  
  public void PushToSendQueue(String buf) {
    try {
      sendQueue.put(new DamqMsg(buf));
    } catch (Exception e) {
      logger.error(e.getMessage());
    }    
  }
  
  public String getUUID() {
    NoArgGenerator gen = Generators.timeBasedGenerator();
    gen = Generators.timeBasedGenerator(EthernetAddress.fromInterface());
    return gen.generate().toString();
  }  
}
