package aries.ProcessCMD;

import java.io.FileNotFoundException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.function.Supplier;

import org.apache.log4j.Logger;
import org.json.JSONObject;

import aries.DeviceManager.Message;
import aries.interoperate.Schema0Header;
import aries.interoperate.Schema1Body;

public class ProcessCommand implements Runnable {
	static Logger logger = Logger.getLogger("ProcessCommand.class");

	private BlockingQueue<Message> queueReq;
	private BlockingQueue<Message> queueResp;
	private volatile String command = "";
	private volatile String response = "";

	public ProcessCommand(BlockingQueue<Message> qReq, BlockingQueue<Message> qResp) {
		this.queueReq = qReq;
		this.queueResp = qResp;
	}

	public void PushToRequestQueue(String buf) {
		try {
			queueReq.put(new Message(buf));
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}

	public void PushToResponseQueue(String buf) {
		try {
			queueResp.put(new Message(buf));
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}

	@Override
	public void run() {
		Message msg;
		while (true) {
			try {
				System.out.println(Thread.currentThread().getName() + " Start.  queueReq.size() : " + queueReq.size()
						+ "   queueResp.size() : " + queueResp.size());

				if (this.response.isEmpty() && !queueResp.isEmpty()) {
					if ((msg = queueResp.take()).getMsg() != "") {
						this.response = msg.getMsg();
					}
				}

				if (this.command.isEmpty() && !queueReq.isEmpty()) {
					if ((msg = queueReq.take()).getMsg() != "") {
						this.command = msg.getMsg();
					}
				}

				if (this.command.isEmpty() &&  this.response.isEmpty()) {
					// idle time
					Thread.sleep(3000);
				} else {
					processCommand();
				}
				
				System.out.println(Thread.currentThread().getName() + " End.");
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private void processCommand() {
		System.out.println("command = " + this.command);
		Schema0Header recvdReq = String2JsonObj2EntityX(this.command);
		System.out.println("response = " + this.response);
		Schema0Header recvdResp = String2JsonObj2EntityX(this.response);
		Schema0Header sendRespSchema0Cmd;

		Supplier<Schema0Header> RecvReqSignup = () -> {
			System.out.println("A 스레드 작업 시작 : Make Sign-up Request CMD");
			Schema0Header sendReqSchema0Cmd = new Schema0Header();
			sendReqSchema0Cmd.msgtype = "req";
			sendReqSchema0Cmd.dst = "common";
			sendReqSchema0Cmd.workcode = "signup";
			sendReqSchema0Cmd.body = new Schema1Body();
			sendReqSchema0Cmd.body.provider = recvdReq.body.provider;
			sendReqSchema0Cmd.body.authcode = recvdReq.body.authcode;
			// Thread.sleep(100);
			System.out.println("A 스레드 작업 완료");
			return sendReqSchema0Cmd;
		};

		Supplier<String> SendReqSignup = () -> {
			try {
				System.out.println("C 스레드 작업 시작 : Send Sign-up Request CMD");
				System.out.println("C 스레드 작업 대기 : wait resp signup c2d");
				Thread.sleep(500);
				System.out.println("C 스레드 작업 완료 : Receive Sign-up response");
				return "C 실행";
			} catch (InterruptedException e) {
				e.printStackTrace();
				return "실패";
			}
		};

		Future<String> result2 = CompletableFuture.supplyAsync(RecvReqSignup)
				.thenApply(aResult -> aResult + " A 성공 -> ")
				.thenCombine(CompletableFuture.supplyAsync(SendReqSignup), (a, c) -> a + c);

		try {
			System.out.println(result2.get());
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

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

	@Override
	public String toString() {
		return this.command;
	}
}
