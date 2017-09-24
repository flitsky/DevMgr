package aries.MessageProcess;

import org.json.JSONObject;

import aries.DeviceManager.Message;
import aries.interoperate.Schema0Header;
import aries.interoperate.Schema1Body;
import io.dase.network.DamqRcvConsumer.MsgType;
import io.dase.network.DamqSndProducer;

public class ObserverSignOut extends CmdProcessObserver {

	public ObserverSignOut(Message msg, ObservableRespMsg observable) {
		super(msg, observable);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void CmdProc(Message msg) {
		JSONObject jo = new JSONObject(msg.getMsg());
		System.out.println("[1111 Receive Request signout] =====>");
		System.out.println("[2222 Request process > Make Request] =====>");
		try {
			DamqSndProducer sndProducer = DamqSndProducer.getInstance();
			System.out.println("[3333 Send Request signout] ----->");
			sndProducer.PushToSendQueue("common", jo.get("msgid").toString(), MsgType.Request, jo.get("workcode").toString(), jo.get("body").toString());
			System.out.println("[4444 Wait Response...] ----->");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	protected void ResponseProc(String RespStr) {
		System.out.println("[6666 ResponseProc] str : " + RespStr);
		JSONObject jo = new JSONObject(RespStr);
		try {
			DamqSndProducer sndProducer = DamqSndProducer.getInstance();
			// System.out.println(" >>>>> sndProducer : ["+sendResp.dst+"] str=" + str);
			System.out.println("[7777 Send Response signout] ----->");
			sndProducer.PushToSendQueue("app", jo.get("msgid").toString(), MsgType.Response, jo.get("workcode").toString(), jo.get("body").toString());
			System.out.println("[8888 Process Done]");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
