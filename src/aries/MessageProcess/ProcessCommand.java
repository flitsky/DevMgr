package aries.MessageProcess;

import java.io.FileNotFoundException;

import org.apache.log4j.Logger;
import org.json.JSONObject;

import aries.DeviceManager.Message;
import aries.DeviceManager.ObservableRespMsg;
import aries.interoperate.Schema0Header;

public class ProcessCommand implements Runnable {
	static Logger logger = Logger.getLogger("ProcessCommand.class");

	private ObservableRespMsg observableRespMsg;

	public ProcessCommand(ObservableRespMsg obsRes) {
		this.observableRespMsg = obsRes;
	}

	@Override
	public void run() {
		Message msg;
		while (true) {
			try {
//				if (this.request.isEmpty() && !queueReq.isEmpty()) {
//					if ((msg = queueReq.take()).getMsg() != "") {
//						System.out.println(" req  msg.getMsg() = " + msg.getMsg());
//						this.request = msg.getMsg();
//						processCommand();
//					} else {
//						Thread.sleep(3000);
//					}
//				}

				System.out.println(Thread.currentThread().getName() + " End.");
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

//	private void processCommand() {
//
//		if (!this.response.isEmpty()) {
//			Schema0Header recvdResp = String2JsonObj2EntityX(this.response);
//			Schema0Header sendResp = new Schema0Header();
//			if (recvdResp.workcode.equals("signup")) {
//				System.out.println("[3333 Receive Response signup] <-----");
//				this.response = "";
//				sendResp.msgtype = recvdResp.msgtype;
//				sendResp.dst = "app";
//				sendResp.workcode = recvdResp.workcode;
//				sendResp.msgid = recvdResp.msgid;
//				sendResp.body = new Schema1Body();
//				sendResp.body.expiresin = recvdResp.body.expiresin;
//				sendResp.body.uid = recvdResp.body.uid;
//				sendResp.body.accesstoken = recvdResp.body.accesstoken;
//				sendResp.body.status = recvdResp.body.status;
//
//				System.out.println("   received response.... process and send response ");
//
//				Schema1Body msgBody = sendResp.body;
//				String str = null;
//				try {
//					str = msgBody.exportToString();
//				} catch (Exception e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//				MsgType msgType = MsgType.Request;
//				if (sendResp.msgtype.equals("res"))
//					msgType = MsgType.Response;
//				System.out.println("[4444 Send Response signup] <=====");
//				DamqSndProducer sndProducer = DamqSndProducer.getInstance();
//				sndProducer.PushToSendQueue(sendResp.dst, sendResp.msgid, msgType, sendResp.workcode, str);
//			}
//		}
//
//		if (!this.request.isEmpty()) {
//			Schema0Header recvdReq = String2JsonObj2EntityX(this.request);
//			Schema0Header sendReq = new Schema0Header();
//			// System.out.println("[processCommand] recvd workcode ? " + recvdReq.workcode +
//			// " request = " + this.request);
//			if (recvdReq.workcode.equals("signup")) {
//				System.out.println("[1111 Receive Request signup] =====>");
//				this.request = "";
//				sendReq.msgtype = recvdReq.msgtype;
//				sendReq.dst = "common";
//				sendReq.workcode = recvdReq.workcode;
//				sendReq.msgid = recvdReq.msgid;
//				sendReq.body = new Schema1Body();
//				sendReq.body.provider = recvdReq.body.provider;
//				sendReq.body.authcode = recvdReq.body.authcode;
//
//				Schema1Body msgBody = sendReq.body;
//				String str = null;
//				try {
//					str = msgBody.exportToString();
//				} catch (Exception e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//				MsgType msgType = MsgType.Request;
//				if (sendReq.msgtype.equals("res"))
//					msgType = MsgType.Response;
//				DamqSndProducer sndProducer = DamqSndProducer.getInstance();
//				System.out.println("[2222 Send Request signup] ----->");
//				// System.out.println(" >>>>> sndProducer : ["+sendReq.dst+"] str=" + str);
//				sndProducer.PushToSendQueue(sendReq.dst, sendReq.msgid, msgType, sendReq.workcode, str);
//			}
//		}
//	}

	static Schema0Header JsonObj2EntityX(JSONObject receivedJsonObj) {
		Schema0Header recvSchema0Cmd = new Schema0Header();
		// Import from jsonObject
		try {
			recvSchema0Cmd.importFromJson(receivedJsonObj);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return recvSchema0Cmd;
	}

	static Schema0Header String2JsonObj2EntityX(String str) {
		if (str.isEmpty()) {
			return null;
		}
		JSONObject JsonObj = new JSONObject(str);
		Schema0Header recvSchema0Cmd = new Schema0Header();
		// Import from jsonObject
		try {
			recvSchema0Cmd.importFromJson(JsonObj);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return recvSchema0Cmd;
	}

//	@Override
//	public String toString() {
//		return this.request;
//	}
}
