import java.io.FileNotFoundException;

import org.apache.log4j.Logger;
import org.json.JSONObject;

import DAO.AriesDAO;
import interoperate.Schema0Cmd;
import interoperate.Schema1Body;
import model.TbDevice;

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
		sendReqDiscoveryDevice();
		//-- 2.[Recv Resp] Discovery Device Result
		//{"type":"res","dir":"c2d","work_code":"dis_dev","body":{"status":200,"devices":[{"dev_id":"c31e8fa3-b524-0e6b-2489-77760c3ca37b","dev_name":"THU Light","spec_ver":"ocf.1.1.0","dev_type":["oic.wk.d", "oic.d.light"],"host_addr":"coap:\/\/[fe80::c4a8:5af:7d0e:f40e%25wlan0]:49244"}]}}
		recvRespDiscoveryDevice();
		//-- 3.[Send REQ] Discovery Resource
		//{"type":"req","dir":"d2c","work_code":"dis_res","body":{"dev_id":"6341cb6f-2179-55a3-3732-c3ffbad1be68"}}
		sendReqDiscoveryResource();
		//-- 4.[Recv Resp] Discovery Resource Result
		//{"type":"res","dir":"c2d","work_code":"dis_res","body":{"status":200,"resources":[{"res_id":"/oic/route/6341cb6f-2179-55a3-3732-c3ffbad1be68/power/0","res_type":["oic.r.switch.binary"],"res_uri":"/oic/route/6341cb6f-2179-55a3-3732-c3ffbad1be68/power/0","res_isobserve":true,"res_dev_id":"6341cb6f-2179-55a3-3732-c3ffbad1be68"}]}}
		recvRespDiscoveryResource();
	}
	
	static void sendReqDiscoveryDevice() {
		Schema0Cmd sendReqSchema0Cmd = new Schema0Cmd();
		sendReqSchema0Cmd.type = "req";
		sendReqSchema0Cmd.direction = "d2c";
		sendReqSchema0Cmd.work_code = "dis_dev";
		// Export jsonObject
		try {
			JSONObject JsonObj4Req = sendReqSchema0Cmd.exportToJson();
			// check exported data
			logger.debug(" 1 send request = " + JsonObj4Req.toString());
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	static void recvRespDiscoveryDevice() {
		String buffer = "{\"type\":\"res\",\"dir\":\"c2d\",\"work_code\":\"dis_dev\",\"body\":{\"status\":200,\"devices\":[{\"dev_id\":\"c31e8fa3-b524-0e6b-2489-77760c3ca37b\",\"dev_name\":\"THU Light\",\"spec_ver\":\"ocf.1.1.0\",\"dev_type\":[\"oic.wk.d\", \"oic.d.light\"],\"host_addr\":\"coap:\\/\\/[fe80::c4a8:5af:7d0e:f40e%25wlan0]:49244\"}]}}";		
		//Import From Json
		try {
			JSONObject receivedJsonObj = new JSONObject(buffer);
			logger.debug(" 2 received response json = " + receivedJsonObj.toString());
			// Import jsonObject
			Schema0Cmd recvSchema0Cmd = new Schema0Cmd();
			recvSchema0Cmd.importFromJson(receivedJsonObj);
			JSONObject jsonExportBody = recvSchema0Cmd.exportToJson();
			if (jsonExportBody.toString().equals(receivedJsonObj.toString()))
				logger.debug(" 2 after import = equal ? true ^O^)/");
			else
				logger.error(" 2 after import = " + jsonExportBody.toString());
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	static void sendReqDiscoveryResource() {
		Schema0Cmd sendReqSchema0Cmd = new Schema0Cmd();
		sendReqSchema0Cmd.type = "req";
		sendReqSchema0Cmd.direction = "d2c";
		sendReqSchema0Cmd.work_code = "dis_dev";
		sendReqSchema0Cmd.body = new Schema1Body();
		sendReqSchema0Cmd.body.device_id = "6341cb6f-2179-55a3-3732-c3ffbad1be68";
		// Export jsonObject
		try {
			JSONObject JsonObj4Req = sendReqSchema0Cmd.exportToJson();
			// check exported data
			logger.debug(" 1 send request = " + JsonObj4Req.toString());
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	static void recvRespDiscoveryResource() {
		String buffer = "{\"type\":\"res\",\"dir\":\"c2d\",\"work_code\":\"dis_res\",\"body\":{\"status\":200,\"resources\":[{\"res_id\":\"/oic/route/6341cb6f-2179-55a3-3732-c3ffbad1be68/power/0\",\"res_type\":[\"oic.r.switch.binary\"],\"res_uri\":\"/oic/route/6341cb6f-2179-55a3-3732-c3ffbad1be68/power/0\",\"res_isobserve\":true,\"res_dev_id\":\"6341cb6f-2179-55a3-3732-c3ffbad1be68\"}]}}";		
		//Import From Json
		try {
			JSONObject receivedJsonObj = new JSONObject(buffer);
			logger.debug(" 2 received response json = " + receivedJsonObj.toString());
			// Import jsonObject
			Schema0Cmd recvSchema0Cmd = new Schema0Cmd();
			recvSchema0Cmd.importFromJson(receivedJsonObj);
			JSONObject jsonExportBody = recvSchema0Cmd.exportToJson();
			if (jsonExportBody.toString().equals(receivedJsonObj.toString()))
				logger.debug(" 2 after import = equal ? true ^O^)/");
			else
				logger.error(" 2 after import = " + jsonExportBody.toString());
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
