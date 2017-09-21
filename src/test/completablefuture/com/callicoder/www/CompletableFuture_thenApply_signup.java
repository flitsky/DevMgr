package test.completablefuture.com.callicoder.www;

import java.io.FileNotFoundException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import org.json.JSONObject;

import aries.interoperate.Schema0Header;
import aries.interoperate.Schema1Body;

public class CompletableFuture_thenApply_signup {

	public static void main(String[] args) {
		// Create a CompletableFuture
		CompletableFuture<Schema0Header> recvReqSignup = CompletableFuture.supplyAsync(() -> {
			String str = "{\"org\":\"common\",\"dst\":\"devmgr\",\"date\":\"19800202\",\"msgid\":\"msgid1234\",\"msgtype\":\"res\",\"workcode\":\"signup\",\"body\":{\"status\":200,\"uid\":\"22883d77-4ab8-4b80-b75b-74774868b484\",\"accesstoken\":\"e00d0d9ec36095d749a350dab04b5a8c1b94e136\",\"expiresin\":-1}}";
			Schema0Header recvdRespSchema = String2JsonObj2EntityX(str);
			Schema0Header sendReqSchema0Cmd = new Schema0Header();
			sendReqSchema0Cmd.msgtype = "req";
			sendReqSchema0Cmd.dst = "common";
			sendReqSchema0Cmd.workcode = "signup";
			sendReqSchema0Cmd.body = new Schema1Body();
			sendReqSchema0Cmd.body.provider = recvdRespSchema.body.provider;
			sendReqSchema0Cmd.body.authcode = recvdRespSchema.body.authcode;
			return sendReqSchema0Cmd;
		});

		// Attach a callback to the Future using thenApply()
		CompletableFuture<String> greetingFuture = recvReqSignup.thenApply(name -> {
			return "Hello " + name.msgtype;
		});

		// Block and get the result of the future.
		try {
			System.out.println(greetingFuture.get());
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // Hello Rajeev
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

}
