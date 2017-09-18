package io.dase.network;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DevMgr extends DamqRcvConsumer {
  private static final Logger logger = LoggerFactory.getLogger(DevMgr.class);
  
  public DevMgr() {    
    super();
  }
  
  public DevMgr(ModuleType m) {    
    super(m);
  }

  public void MainProc() {
    logger.debug("DevMgr begins.");
    //DamqMsg msg = null;
    
    while (true) {
      try {
        String rcvBuf = rcvQueue.take().getMsg();
        if (rcvBuf.equals("{\"command\":\"lbxjtyf\"}")) break;
        
        JSONObject jo = (JSONObject)JSONValue.parseWithException(rcvBuf);        
        String destination = jo.get("destination").toString().toLowerCase();
        String uuid = jo.get("uuid").toString().toLowerCase();
        String msgbody = jo.get("msgbody").toString().toLowerCase();
        
        logger.debug("dest : " + destination);
        logger.debug("uuid : " + uuid);
        logger.debug("body : " + msgbody);
                
        Thread.yield();
      } catch (Exception e) {
        if (e instanceof InterruptedException) {
          logger.error("error: " + e.getMessage());          
        } else {
          logger.error("err0r: " + e.getMessage());
        }
        continue;
      }
    }
    logger.debug("EngineMain ends.");
  }
}
