package io.dase.network;

import java.util.concurrent.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
  
//import io.dase.network.*;

public abstract class DamqRcvConsumer implements Runnable {
  public enum ModuleType {
    APP(0), NGIN(1), DEVMGR(2), COMCLNT(3);
    
    private int _value;

    ModuleType(int Value) {
        this._value = Value;
    }

    public int getValue() {
            return _value;
    }
  }  
  public static String[] ModuleAddress = { "127.0.0.1", "127.0.0.1", "127.0.0.1", "127.0.0.1" };
  public static int[] ModulePort = { 5001, 5002, 5003, 5004 };
  
  private static final Logger logger = LoggerFactory.getLogger(DamqRcvConsumer.class);
  public static int myModuleType = 0;
  
  // 수신 Producer 핸들
  Runnable rcvProducerRun = null;
  Thread rcvProducer = null;  
  
  // 수신 Consumer
  // 지금 보시는 이 this class 가 수신 consumer 입니다.
  // 추상메소드 abstract void MainProc() 를 구현하세요.  

  // 송신 Producer
  // Singleton 이니까 필요한 메소드를 알아서 그냥 땡겨쓰세요.
  protected DamqSndProducer sndProducer = DamqSndProducer.getInstance();
  
  // 송신 Consumer 핸들
  Runnable sndConsumerRun = null;
  Thread sndConsumer = null;
  
  // 수신큐
  protected BlockingQueue<DamqMsg> rcvQueue = new ArrayBlockingQueue<>(16384); 
  // 송신큐
  protected BlockingQueue<DamqMsg> sndQueue = new ArrayBlockingQueue<>(16384);  
  
  public DamqRcvConsumer() {
    myModuleType = ModuleType.NGIN.getValue();   
  }
  
  public DamqRcvConsumer(ModuleType m) {
    myModuleType = m.getValue();     
  }

  protected void StartJobs() {
    rcvProducerRun = new DamqRcvProducer(rcvQueue);
    rcvProducer = new Thread(rcvProducerRun);
    rcvProducer.start();
    
    sndProducer.InitDamqSndProducer(sndQueue);

    sndConsumerRun = new DamqSndConsumer(sndQueue);
    sndConsumer = new Thread(sndConsumerRun);
    sndConsumer.start(); 
  }
  
  public void run() {
    logger.debug("DamqRcvConsumer begins.");
    StartJobs();
    
    MainProc();      
    
    try {
      rcvProducer.join();
      sndConsumer.join();
    } catch (Exception e) {
      if (e instanceof InterruptedException) {
        logger.error("3rror: " + e.getMessage());          
      } else {
        logger.error("3rr0r: " + e.getMessage());
      }
    }      
    logger.debug("All threads joined.");
    logger.debug("DamqRcvConsumer ends.");
  }
  
  public abstract void MainProc();
}
