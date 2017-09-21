package aries.ProcessCMD;

import java.io.FileNotFoundException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.function.Supplier;

import org.apache.log4j.Logger;
import org.json.JSONObject;

import aries.DAO.DeviceManagerDAO;
import aries.DeviceManager.Message;
import aries.interoperate.Property;
import aries.interoperate.Schema0Cmd;
import aries.interoperate.Schema1Body;
import io.dase.network.DamqSndProducer;
import io.dase.network.DamqRcvConsumer.ModuleType;
import io.dase.network.DamqRcvConsumer.MsgType;

public class ProcessCommand implements Runnable {
	static Logger logger = Logger.getLogger("ProcessCommand.class");

	private BlockingQueue<Message> queueReq;
	private BlockingQueue<Message> queueResp;
	private String command;
	private volatile String response;

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
				if (this.response == "") {
					if ((msg = queueResp.take()).getMsg() != "") {
						this.response = msg.getMsg();
						processCommand();
					}
				}
				if ((msg = queueReq.take()).getMsg() != "") {
					Thread.sleep(30);
					this.command = msg.getMsg();
					System.out.println(Thread.currentThread().getName() + " Start.  queueReq.size() : " + queueReq.size());
					processCommand();
					System.out.println(Thread.currentThread().getName() + " End.");
				} else {
					// idle time
					Thread.sleep(1000);
				}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private void processCommand() {
		System.out.println("command = " + this.command);
		JSONObject receivedJsonObj = new JSONObject(this.command);
		Schema0Cmd recvSchema0Cmd = JsonObj2EntityX(receivedJsonObj);
		System.out.println("response = " + this.response);
		receivedJsonObj = new JSONObject(this.response);
		Schema0Cmd recvSchema0Resp = JsonObj2EntityX(receivedJsonObj);
		Schema0Cmd sendRespSchema0Cmd = new Schema0Cmd();
				
		Supplier<Schema0Cmd> RecvReqSignup = () -> {
			System.out.println("A 스레드 작업 시작 : Make Sign-up Request CMD");
			Schema0Cmd sendReqSchema0Cmd = new Schema0Cmd();
			sendReqSchema0Cmd.msgtype = "req";
			sendReqSchema0Cmd.dst = "common";
			sendReqSchema0Cmd.work_code = "signup";
			sendReqSchema0Cmd.body = new Schema1Body();
			sendReqSchema0Cmd.body.provider = recvSchema0Cmd.body.provider;
			sendReqSchema0Cmd.body.authcode = recvSchema0Cmd.body.authcode;
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

	private void processOldCommand() {
		System.out.println("command = " + this.command);
		JSONObject receivedJsonObj = new JSONObject(this.command);
		Schema0Cmd recvSchema0Cmd = JsonObj2EntityX(receivedJsonObj);
		logger.debug(" [test code] check property type : " + recvSchema0Cmd.body.properties.get(0).propertyValueType);
		logger.debug(" [test code] check property value size : "
				+ recvSchema0Cmd.body.properties.get(0).propertyValue.size());
		logger.debug(" [test code] check property value is empty : "
				+ recvSchema0Cmd.body.properties.get(0).propertyValue.isEmpty());

		JSONObject jsonExportBody;
		try {
			jsonExportBody = recvSchema0Cmd.exportToJson();
			if (jsonExportBody.toString().equals(receivedJsonObj.toString()))
				logger.debug(" [test code] ok.. receivedJson == imported data ");
			else
				logger.error(" [test code] error.. imported data is not equal = " + jsonExportBody.toString());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		switch (recvSchema0Cmd.org) {
		//public static String[] ModuleName = { "app", "ngin", "devmgr", "common" };
		case "app":
			if (recvSchema0Cmd.msgtype.equals("req")) {
				Schema1Body sendReqSchema1Body = new Schema1Body();
				if (recvSchema0Cmd.work_code.equals("discovery")) {
					DamqSndProducer sndProducer = DamqSndProducer.getInstance();
					sndProducer.PushToSendQueue(ModuleType.COMCLNT, MsgType.Request, "dis_dev", "");
				} else if (recvSchema0Cmd.work_code.equals("dis_res")) {
					sendReqSchema1Body.device_id = "6341cb6f-2179-55a3-3732-c3ffbad1be68";
					String s = sendReqSchema1Body.toString();
					DamqSndProducer sndProducer = DamqSndProducer.getInstance();
					sndProducer.PushToSendQueue(ModuleType.COMCLNT, MsgType.Request, "dis_res", s);
				}
			} else {

			}
			break;
		case "ngin":
			if (recvSchema0Cmd.msgtype.equals("req")) {

			} else {

			}
			break;
		case "common":
			if (recvSchema0Cmd.msgtype.equals("req")) {
				switch (recvSchema0Cmd.work_code) {
				case "observe":
					break;
				default:
					break;
				}

			} else {
				switch (recvSchema0Cmd.work_code) {
				case "signup":
					if (recvSchema0Cmd.body.status == 200) {
						logger.debug("Sign-up response store user info...");
						DeviceManagerDAO dmDAO = new DeviceManagerDAO();
						dmDAO.storeUserInfo(recvSchema0Cmd.body);
					}
					break;
				case "signin":
					break;
				case "signout":
					break;
				case "dis_dev":
					if (recvSchema0Cmd.body.status == 200) {
						logger.debug("Send Discovery Device result to DB.");
						DeviceManagerDAO dmDAO = new DeviceManagerDAO();
						dmDAO.storeDiscoveredDevices(recvSchema0Cmd.body);
					}
					break;
				case "dis_res":
					if (recvSchema0Cmd.body.status == 200) {
						logger.debug("Send Discovery Resource result to DB.");
					}
					break;
				case "get":
					break;
				case "post":
					break;
				case "observe":
					if (recvSchema0Cmd.body.status == 200) {
						for (Property prop : recvSchema0Cmd.body.properties) {
							switch (prop.propertyValueType) {
							case "boolean":
								logger.debug("property type is boolean : " + prop.propertyValue);
								break;
							case "integer":
								logger.debug("property type is integer : " + prop.propertyValue);
								break;
							case "double":
								logger.debug("property type is double : " + prop.propertyValue);
								break;
							case "string":
								logger.debug("property type is string : " + prop.propertyValue);
								break;
							default:
								break;
							}
						}
					}
					break;
				case "obs_can":
					break;
				default:
					break;
				}
			}
			break;
		default:
			break;
		}
	}

	static void sendCommand(Schema0Cmd cmd) {
		// Export to jsonObject
		try {
			JSONObject JsonObj4Req = cmd.exportToJson();
			// check exported data
			logger.debug(" >>> sendCommand = " + JsonObj4Req.toString());

			// send command to ...
			String s = JsonObj4Req.toString();
			DamqSndProducer sndProducer = DamqSndProducer.getInstance();
			sndProducer.PushToSendQueue(ModuleType.DEVMGR, MsgType.Request, "signin", s);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	static Schema0Cmd JsonObj2EntityX(JSONObject receivedJsonObj) {
		Schema0Cmd recvSchema0Cmd = new Schema0Cmd();
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

	@Override
	public String toString() {
		return this.command;
	}
}
