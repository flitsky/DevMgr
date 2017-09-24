package aries.MessageProcess;

import java.util.Observable;

import org.json.JSONObject;

import aries.DeviceManager.Message;
import aries.DeviceManager.ObservableRespMsg;
import aries.interoperate.Schema0Header;
import aries.interoperate.Schema1Body;
import io.dase.network.DamqRcvConsumer.MsgType;
import io.dase.network.DamqSndProducer;

public class ObserverSignUp extends CmdProcessObserver {

	public ObserverSignUp(Message msg, ObservableRespMsg observable) {
		super(msg, observable);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void CmdProc(Message msg) {
		JSONObject jo = new JSONObject(msg.getMsg());
		Schema0Header cmd = new Schema0Header();
		System.out.println("[1111 Receive Request signup] =====>");
		System.out.println("[2222 Request process > Make Request] =====>");
		try {
			cmd.importFromJson(jo);
			Schema0Header sendReq = new Schema0Header();
			sendReq.msgtype = cmd.msgtype;
			sendReq.dst = "common";
			sendReq.workcode = cmd.workcode;
			sendReq.msgid = cmd.msgid;
			sendReq.body = new Schema1Body();
			sendReq.body.provider = cmd.body.provider;
			sendReq.body.authcode = cmd.body.authcode;
			Schema1Body msgBody = sendReq.body;
			String str = null;
			try {
				str = msgBody.exportToString();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			MsgType msgType = MsgType.Request;
			if (sendReq.msgtype.equals("res"))
				msgType = MsgType.Response;
			DamqSndProducer sndProducer = DamqSndProducer.getInstance();

			// System.out.println(" >>>>> sndProducer : ["+sendReq.dst+"] str=" + str);
			System.out.println("[3333 Send Request signup] ----->");
			sndProducer.PushToSendQueue(sendReq.dst, sendReq.msgid, msgType, sendReq.workcode, str);
			System.out.println("[4444 Wait Response...] ----->");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Override
	protected void ResponseProc(String str) {
		System.out.println("[6666 ResponseProc] str : " + str);
	}
}
