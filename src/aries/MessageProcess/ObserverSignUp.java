package aries.MessageProcess;

import org.json.JSONObject;

import aries.DeviceManager.Message;
import aries.interoperate.Schema0Header;
import aries.interoperate.Schema1Body;
import io.dase.network.DamqRcvConsumer.MsgType;
import io.dase.network.DamqSndProducer;

public class ObserverSignUp extends CmdProcessTimerTaskObserver {
	JSONObject sendResp;

	public ObserverSignUp(Message msg, ObservableRespMsg observable) {
		super(msg, observable);
		// TODO Auto-generated constructor stub
	}

	public ObserverSignUp(Message msg, ObservableRespMsg observable, int expirationSec) {
		super(msg, observable, expirationSec);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void CmdProc(JSONObject recvdReq) {
		System.out.println("[1111 Receive Request signup] =====>");
		System.out.println("[2222 Request process > Make Request] =====>");
		DamqSndProducer sndProducer = DamqSndProducer.getInstance();

		// System.out.println(" >>>>> sndProducer : ["+sendReq.dst+"] str=" + str);
		System.out.println("[3333 Send Request signup] ----->");
		sndProducer.PushToSendQueue("common", recvdReq.getString("msgid"), MsgType.Request, recvdReq.getString("workcode"), recvdReq.getString("body"));
		System.out.println("[4444 Wait Response...] ----->");
	}

	@Override
	protected void ResponseProc(String RespStr) {
		System.out.println("[6666 ResponseProc] str : " + RespStr);
		JSONObject jo = new JSONObject(RespStr);
		Schema0Header cmd = new Schema0Header();
		try {
			cmd.importFromJson(jo);
			Schema0Header sendResp = new Schema0Header();
			sendResp.msgtype = cmd.msgtype;
			sendResp.dst = "app";
			sendResp.workcode = cmd.workcode;
			sendResp.msgid = cmd.msgid;
			sendResp.body = new Schema1Body();
			sendResp.body.status = cmd.body.status;
			sendResp.body.uid = cmd.body.uid;
			sendResp.body.accesstoken = cmd.body.accesstoken;
			Schema1Body msgBody = sendResp.body;
			String str = null;
			try {
				str = msgBody.exportToString();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			MsgType msgType = MsgType.Request;
			if (sendResp.msgtype.equals("res"))
				msgType = MsgType.Response;
			DamqSndProducer sndProducer = DamqSndProducer.getInstance();

			// System.out.println(" >>>>> sndProducer : ["+sendResp.dst+"] str=" + str);
			System.out.println("[7777 Send Response signup] ----->");
			sndProducer.PushToSendQueue(sendResp.dst, sendResp.msgid, msgType, sendResp.workcode, str);
			System.out.println("[8888 Process Done]");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	protected void ExpiredProc() {
		System.out.println("      >>> Response ExpiredProc <<<  ");
		System.out.println("[5555 Send Response signin is expired] ----->");

		DamqSndProducer sndProducer = DamqSndProducer.getInstance();
		// sndProducer.PushToSendQueue(sendResp.dst, sendResp.msgid, MsgType.Response,
		// "", str);
	}
}
