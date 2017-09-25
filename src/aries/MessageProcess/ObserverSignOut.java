package aries.MessageProcess;

import org.json.JSONObject;

import aries.DeviceManager.Message;
import aries.interoperate.Schema0Header;
import aries.interoperate.Schema1Body;
import io.dase.network.DamqRcvConsumer.MsgType;
import io.dase.network.DamqSndProducer;

public class ObserverSignOut extends CmdProcessTimerTaskObserver {

	public ObserverSignOut(Message msg, ObservableRespMsg observable) {
		super(msg, observable);
		// TODO Auto-generated constructor stub
	}

	public ObserverSignOut(Message msg, ObservableRespMsg observable, int expirationSec) {
		super(msg, observable, expirationSec);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void CmdProc(JSONObject recvdReq) {
		System.out.println("[1111 Receive Request signout] =====>");
		System.out.println("[2222 Request process > Make Request] =====>");
		try {
			DamqSndProducer sndProducer = DamqSndProducer.getInstance();
			System.out.println("[3333 Send Request signout] ----->");
			sndProducer.PushToSendQueue("common", recvdReq.getString("msgid"), MsgType.Request,
					recvdReq.getString("workcode"), recvdReq.get("body").toString());
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
			sndProducer.PushToSendQueue("app", jo.getString("msgid"), MsgType.Response,
					jo.getString("workcode"), jo.get("body").toString());
			System.out.println("[8888 Process Done]");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	protected void ExpiredProc() {
		System.out.println("      >>> Response ExpiredProc <<<  ");
		System.out.println("[5555 Send Response signout is expired] -----> body : " + sendRespJO.get("body").toString() + " msgid : " + getMsgID());

		DamqSndProducer sndProducer = DamqSndProducer.getInstance();
		sndProducer.PushToSendQueue(sendRespJO.getString("dst"), sendRespJO.getString("msgid"), MsgType.Response,
				sendRespJO.getString("workcode"), sendRespJO.get("body").toString());
	}
}
