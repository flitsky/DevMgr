package io.dase.network;

public class DamqMsg {
  private String msg;
  
  public DamqMsg(String buf) {
    this.msg = buf;
  }
  
  public String getMsg() {
    return msg;
  }
}
