package io.dase.network;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.concurrent.BlockingQueue;
import java.util.Base64;
import java.util.UUID;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.dase.network.DamqMsg;
import io.dase.network.DamqRcvConsumer;
import io.dase.network.DamqRcvConsumer.ModuleType;
import io.dase.network.DamqRcvConsumer.MsgType;

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
  private String strKey = "aY8a87U"; 
  
  public void InitDamqSndProducer(BlockingQueue<DamqMsg> q) {
    logger.error("InitDamqSndProducer");
    this.sendQueue = q;
  }
  
  public String GetModuleAddress(String moduleName) {
    String val = "";
    
    if (moduleName.equals("ngin")) {
      val = DamqRcvConsumer.ModuleAddress[DamqRcvConsumer.ModuleType.NGIN.getValue()];
    } else if (moduleName.equals("devmgr")) {
      val = DamqRcvConsumer.ModuleAddress[ModuleType.DEVMGR.getValue()];
    } else if (moduleName.equals("common")) {
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
    } else if (moduleName.equals("common")) {
      val = DamqRcvConsumer.ModulePort[ModuleType.COMCLNT.getValue()];
    } else if (moduleName.equals("app")) {
      val = DamqRcvConsumer.ModulePort[ModuleType.APP.getValue()];
    }
    
    return val;    
  }
  
  public void PushToSendQueue(ModuleType dest, MsgType msgType, String workCode, String msgBody) {   
    try {
      // json 문자열을 JSONObject 를 사용하지 않고 String 으로 조립해준다.
      // 키값들이 코드에서 정의된 순서대로 정렬되지 않기 때문이다.
      // json.org 의 specification 에 "An object is an unordered set of name/value pairs" 라고 나와있다.
      // Human readable 로 만들어주려면 눈으로 읽기 편하게 만들어줘야지.
      // 이 개새끼들은 지네들 편하려고 개판으로 만들어놓았다.
      String buf = String.format("{\"org\":\"%s\",\"dst\":\"%s\",\"date\":\"%s\",\"msgid\":\"%s\",\"msgtype\":\"%s\",\"workcode\":\"%s\",\"body\":%s}"
                                , DamqRcvConsumer.ModuleName[DamqRcvConsumer.myModuleType]
                                , DamqRcvConsumer.ModuleName[dest.getValue()]
                                , new SimpleDateFormat("yyyyMMddHHmmss").format(new Date())
                                , UUID.randomUUID().toString()
                                , DamqRcvConsumer.MsgTypeName[msgType.getValue()]
                                , workCode
                                , msgBody);
      
      System.out.println(" ###### sendQueue.put DamqMsg ");
      sendQueue.put(new DamqMsg(buf));
      
    } catch (Exception e) {
      logger.error(e.getMessage());
    }
  }

  public void PushToSendQueue(String dest, String msgId, MsgType msgType, String workCode, String msgBody) {   
    try {
      String buf = String.format("{\"org\":\"%s\",\"dst\":\"%s\",\"date\":\"%s\",\"msgid\":\"%s\",\"msgtype\":\"%s\",\"workcode\":\"%s\",\"body\":%s}"
                                , DamqRcvConsumer.ModuleName[DamqRcvConsumer.myModuleType]
                                , dest
                                , new SimpleDateFormat("yyyyMMddHHmmss").format(new Date())
                                , msgId
                                , DamqRcvConsumer.MsgTypeName[msgType.getValue()]
                                , workCode
                                , msgBody);
      
      System.out.println(" ###########  sendQueue.put DamqMsg  ##########");
      sendQueue.put(new DamqMsg(buf));
      
    } catch (Exception e) {
      logger.error(e.getMessage());
    }
  }

  public void PushToSendQueue(String org, String dest, String msgId, MsgType msgType, String workCode, String msgBody) {   
    try {
      String buf = String.format("{\"org\":\"%s\",\"dst\":\"%s\",\"date\":\"%s\",\"msgid\":\"%s\",\"msgtype\":\"%s\",\"workcode\":\"%s\",\"body\":%s}"
                                , org
                                , dest
                                , new SimpleDateFormat("yyyyMMddHHmmss").format(new Date())
                                , msgId
                                , DamqRcvConsumer.MsgTypeName[msgType.getValue()]
                                , workCode
                                , msgBody);
      
      System.out.println(" ###########  sendQueue.put DamqMsg  ##########");
      sendQueue.put(new DamqMsg(buf));
      
    } catch (Exception e) {
      logger.error(e.getMessage());
    }
  }
  private boolean CheckHash(String buf) {
    try {
      JSONObject jo = new JSONObject(buf);
      String stamp = jo.get("stamp").toString();
      String value = jo.get("value").toString();      
      SecretKeySpec key = new SecretKeySpec(strKey.getBytes("UTF-8"), "Blowfish");  
      Cipher cipher = Cipher.getInstance("Blowfish");
      if (cipher == null || key == null) {
        throw new Exception("Invalid key or cypher");
      }
      cipher.init(Cipher.ENCRYPT_MODE, key);
      
      String encText = new String(Base64.getEncoder().encodeToString(cipher.doFinal(stamp.getBytes("UTF-8")))).substring(2, 11);
      // 종료 메시지 조건
      // 3분 이내로 발급된 메시지
      // 스탬프의 암호화 값 일치
      if (encText.equals(value) && (Instant.now().toEpochMilli() - Long.parseLong(stamp)) < 180000) return true;      
    } catch (Exception e) {
      logger.error(e.getMessage());
    }
    return false;
  }
  
  public boolean IsTerminateSignal(String buf) {
    try {
      JSONObject jo = new JSONObject(buf);
    
      if (jo.get("workcode").toString().equals("terminate") &&
          jo.get("org").toString().equals(DamqRcvConsumer.ModuleName[DamqRcvConsumer.myModuleType]) &&
          jo.get("msgtype").toString().equals("req") &&           
          CheckHash(jo.get("body").toString())) {
        return true;
      }
    } catch (Exception e) {
      logger.error(e.getMessage());
    }
    
    return false;
  }
  
  public void SendExitSignal() {
    JSONObject jo = null;
    JSONObject joBody = null;
    
    try {
      jo = new JSONObject();
      joBody = new JSONObject();      
      jo.put("org", DamqRcvConsumer.ModuleName[DamqRcvConsumer.myModuleType]);
      jo.put("dst", DamqRcvConsumer.ModuleName[DamqRcvConsumer.myModuleType]);
      jo.put("date", new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
      jo.put("msgid", UUID.randomUUID().toString());
      jo.put("msgtype", DamqRcvConsumer.MsgTypeName[DamqRcvConsumer.MsgType.Request.getValue()]);
      jo.put("workcode", "terminate");
      
      String ooo = Long.toString(Instant.now().toEpochMilli());
      
      SecretKeySpec key = new SecretKeySpec(strKey.getBytes("UTF-8"), "Blowfish");  
      Cipher cipher = Cipher.getInstance("Blowfish");
      if (cipher == null || key == null) {
        throw new Exception("Invalid key or cypher");
      }
      cipher.init(Cipher.ENCRYPT_MODE, key);
      joBody.put("stamp", ooo);
      joBody.put("value", new String(Base64.getEncoder().encodeToString(cipher.doFinal(ooo.getBytes("UTF-8")))).substring(2, 11));      
      jo.put("body", joBody);
      sendQueue.put(new DamqMsg(jo.toString()));
    } catch (Exception e) {
      logger.error(e.getMessage());
    }
  }
}
