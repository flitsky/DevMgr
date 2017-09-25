package aries.MessageProcess;

import org.json.JSONObject;

import aries.DeviceManager.Message;
import aries.interoperate.Schema0Header;
import aries.interoperate.Schema1Body;
import io.dase.network.DamqRcvConsumer.MsgType;
import io.dase.network.DamqSndProducer;

public class ObserverSignIn extends CmdProcessTimerTaskObserver {

	public ObserverSignIn(Message msg, ObservableRespMsg observable) {
		super(msg, observable);
		// TODO Auto-generated constructor stub
	}

	public ObserverSignIn(Message msg, ObservableRespMsg observable, int expirationSec) {
		super(msg, observable, expirationSec);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void CmdProc(JSONObject recvdReq) {

		System.out.println("[1111 Receive Request signin] =====>");
		System.out.println("[2222 Request process > Make Request] =====>");

		DamqSndProducer sndProducer = DamqSndProducer.getInstance();
		System.out.println("[3333 Send Request signup] ----->");
		sndProducer.PushToSendQueue("common", recvdReq.getString("msgid"), MsgType.Request,
				recvdReq.getString("workcode"), recvdReq.getString("body"));
		System.out.println("[4444 Wait Response...] ----->");
	}

	@Override
	protected void ResponseProc(String RespStr) {
		System.out.println("[6666 ResponseProc] str : " + RespStr);
		JSONObject jo = new JSONObject(RespStr);

		DamqSndProducer sndProducer = DamqSndProducer.getInstance();
		System.out.println("[7777 Send Response signup] ----->");
		sndProducer.PushToSendQueue(sendResp.getString("dst"), jo.getString("msgid"), MsgType.Response, jo.getString("workcode"),
				jo.getString("body"));
		System.out.println("[8888 Process Done]");
	}

	@Override
	protected void ExpiredProc() {
		System.out.println("      >>> Response ExpiredProc <<<  ");
		System.out.println("[5555 Send Response signup is expired] ----->");

		DamqSndProducer sndProducer = DamqSndProducer.getInstance();
		/*sndProducer.PushToSendQueue("common", jo.get("msgid").toString(), MsgType.Request,
				jo.get("workcode").toString(), jo.get("body").toString());*/
	}

}
