package Aries.DeviceManager;

import java.io.FileNotFoundException;

import org.apache.log4j.Logger;
import org.json.JSONObject;

import Aries.DAO.AriesDAO;
import Aries.interoperate.Schema0Cmd;
import Aries.interoperate.Schema1Body;
import Aries.model.TbDevice;

public class DevMgr {
	static Logger logger = Logger.getLogger("DevMgr.class");
	
	public static void main(String[] args) {
		final AriesDAO ariesDAO = new AriesDAO();
		TbDevice device = new TbDevice();
		
		//-- 1.[Recv REQ] Device&Resource List
		//{"type":"req","dir":"a2d","work_code":"discovery","body":{"uid":"bb321fac-03e7-439c-b57b-35546216701c"}}
		
		//-- 2.[Send Resp] Device&Resource List
		//{"type":"res","dir":"a2d","work_code":"discovery","body":{"uid":"bb321fac-03e7-439c-b57b-35546216701c"}}
		
		//-- 1.[Send REQ] Discovery Device
		//{"type":"req","dir":"d2c","work_code":"dis_dev","body":{}}
		Schema0Cmd sendReqSchema0Cmd = new Schema0Cmd();
		sendReqSchema0Cmd.type = "req";
		sendReqSchema0Cmd.direction = "d2c";
		sendReqSchema0Cmd.work_code = "dis_dev";
		sendCommand(sendReqSchema0Cmd);

		//-- 2.[Recv Resp] Discovery Device Result
		//{"type":"res","dir":"c2d","work_code":"dis_dev","body":{"status":200,"devices":[{"dev_id":"c31e8fa3-b524-0e6b-2489-77760c3ca37b","dev_name":"THU Light","spec_ver":"ocf.1.1.0","dev_type":["oic.wk.d", "oic.d.light"],"host_addr":"coap:\/\/[fe80::c4a8:5af:7d0e:f40e%25wlan0]:49244"}]}}
		JSONObject receivedJsonObj = recvCommand(1);
		Schema0Cmd recvSchema0Cmd = JsonObj2EntityX(receivedJsonObj);
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

		//-- 3.[Send REQ] Discovery Resource
		//{"type":"req","dir":"d2c","work_code":"dis_res","body":{"dev_id":"6341cb6f-2179-55a3-3732-c3ffbad1be68"}}
		sendReqSchema0Cmd = new Schema0Cmd();
		sendReqSchema0Cmd.type = "req";
		sendReqSchema0Cmd.direction = "d2c";
		sendReqSchema0Cmd.work_code = "dis_dev";
		sendReqSchema0Cmd.body = new Schema1Body();
		sendReqSchema0Cmd.body.device_id = "6341cb6f-2179-55a3-3732-c3ffbad1be68";
		sendCommand(sendReqSchema0Cmd);

		//-- 4.[Recv Resp] Discovery Resource Result
		//{"type":"res","dir":"c2d","work_code":"dis_res","body":{"status":200,"resources":[{"res_id":"/oic/route/6341cb6f-2179-55a3-3732-c3ffbad1be68/power/0","res_type":["oic.r.switch.binary"],"res_uri":"/oic/route/6341cb6f-2179-55a3-3732-c3ffbad1be68/power/0","res_isobserve":true,"res_dev_id":"6341cb6f-2179-55a3-3732-c3ffbad1be68"}]}}
		receivedJsonObj = recvCommand(2);
		recvSchema0Cmd = JsonObj2EntityX(receivedJsonObj);
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
	}
	
	static void sendCommand(Schema0Cmd cmd) {
		// Export jsonObject
		try {
			JSONObject JsonObj4Req = cmd.exportToJson();
			// check exported data
			logger.debug(" send cmd = " + JsonObj4Req.toString());
			
			// send command to ...
			// to do ...
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	static JSONObject recvCommand(int num) {
		JSONObject receivedJsonObj;
		String buffer = null;
		
		switch (num) {
		case 1:
			buffer = "{\"type\":\"res\",\"dir\":\"c2d\",\"work_code\":\"dis_dev\",\"body\":{\"status\":200,\"devices\":[{\"dev_id\":\"c31e8fa3-b524-0e6b-2489-77760c3ca37b\",\"dev_name\":\"THU Light\",\"spec_ver\":\"ocf.1.1.0\",\"dev_type\":[\"oic.wk.d\", \"oic.d.light\"],\"host_addr\":\"coap:\\/\\/[fe80::c4a8:5af:7d0e:f40e%25wlan0]:49244\"}]}}";
		case 2:
			buffer = "{\"type\":\"res\",\"dir\":\"c2d\",\"work_code\":\"dis_res\",\"body\":{\"status\":200,\"resources\":[{\"res_id\":\"/oic/route/6341cb6f-2179-55a3-3732-c3ffbad1be68/power/0\",\"res_type\":[\"oic.r.switch.binary\"],\"res_uri\":\"/oic/route/6341cb6f-2179-55a3-3732-c3ffbad1be68/power/0\",\"res_isobserve\":true,\"res_dev_id\":\"6341cb6f-2179-55a3-3732-c3ffbad1be68\"}]}}";
		}
		receivedJsonObj = new JSONObject(buffer);
		logger.debug(" received cmd = " + receivedJsonObj.toString());
		
		return receivedJsonObj;
	}
	
	static Schema0Cmd JsonObj2EntityX(JSONObject receivedJsonObj) {
		Schema0Cmd recvSchema0Cmd = new Schema0Cmd();
		//Import jsonObject
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
}
