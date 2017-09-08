import java.io.FileNotFoundException;

import org.json.JSONArray;
import org.json.JSONObject;

import DAO.AriesDAO;
import interoperate.C2D11Discovery;
import interoperate.Schema0Cmd;
import model.TbDevice;

public class DevMgr {

	public static void main(String[] args) {
		final AriesDAO ariesDAO = new AriesDAO();
		TbDevice device = new TbDevice();
		
		//-- 1.Send Discovery Device Request
		sendReqDiscoveryDevice();
		//-- 2.Receive Discovery Device Result
		recvRespDiscoveryDevice();
		//-- 3.Send Discovery Resource Request
		sendReqDiscoveryResource();
		//-- 4.Receive Discovery Resource Result
		recvRespDiscoveryResource();
	}
	
	static void sendReqDiscoveryDevice() {
		//{"type":"req","dir":"d2c","work_code":"dis_dev","body":{}}
		Schema0Cmd sendReqSchema0Cmd = new Schema0Cmd();
		sendReqSchema0Cmd.type = "req";
		sendReqSchema0Cmd.direction = "d2c";
		sendReqSchema0Cmd.work_code = "dis_dev";
		// Export jsonObject
		try {
			JSONObject jsonSchema0Cmd = sendReqSchema0Cmd.exportToJson();
			// check modified data
			System.out.println(" 1 send request = " + jsonSchema0Cmd.toString());
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	static void recvRespDiscoveryDevice() {
		//{"type":"res","dir":"c2d","work_code":"dis_dev","body":{"status":200,"devices":[{"dev_id":"c31e8fa3-b524-0e6b-2489-77760c3ca37b","dev_name":"THU Light","spec_ver":"ocf.1.1.0","dev_type":["oic.wk.d", "oic.d.light"],"host_addr":"coap:\/\/[fe80::c4a8:5af:7d0e:f40e%25wlan0]:49244"}]}}
		String buffer = "{\"type\":\"res\",\"dir\":\"c2d\",\"work_code\":\"dis_dev\",\"body\":{\"status\":200,\"devices\":[{\"dev_id\":\"c31e8fa3-b524-0e6b-2489-77760c3ca37b\",\"dev_name\":\"THU Light\",\"spec_ver\":\"ocf.1.1.0\",\"dev_type\":[\"oic.wk.d\", \"oic.d.light\"],\"host_addr\":\"coap:\\/\\/[fe80::c4a8:5af:7d0e:f40e%25wlan0]:49244\"}]}}";		
		//Import From Json
		try {
			JSONObject jsonSchema0Cmd = new JSONObject(buffer);
			System.out.println(" 2 receive cmd = " + jsonSchema0Cmd.toString());
			// Import jsonObject
			Schema0Cmd recvSchema0Cmd = new Schema0Cmd();
			recvSchema0Cmd.importFromJson(jsonSchema0Cmd);
			JSONObject jsonExportBody = recvSchema0Cmd.exportToJson();
			if (jsonExportBody.toString().equals(jsonSchema0Cmd.toString()))
				System.out.println(" 2 after import = equal ? true ^O^)/");
			else
				System.out.println(" 2 after import = " + jsonExportBody.toString());
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	static void sendReqDiscoveryResource() {
		//{"type":"req","dir":"d2c","work_code":"dis_res","body":{"dev_id":"6341cb6f-2179-55a3-3732-c3ffbad1be68"}}
		Schema0Cmd sendReqSchema0Cmd = new Schema0Cmd();
		sendReqSchema0Cmd.type = "req";
		sendReqSchema0Cmd.direction = "d2c";
		sendReqSchema0Cmd.work_code = "dis_dev";
		sendReqSchema0Cmd.body.device_id = "6341cb6f-2179-55a3-3732-c3ffbad1be68";
		// Export jsonObject
		try {
			JSONObject jsonSchema0Cmd = sendReqSchema0Cmd.exportToJson();
			// check modified data
			System.out.println(" 1 send request = " + jsonSchema0Cmd.toString());
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	static void recvRespDiscoveryResource() {
		//{"type":"res","dir":"c2d","work_code":"dis_res","body":{"status":200,"resources":[{"res_id":"/oic/route/6341cb6f-2179-55a3-3732-c3ffbad1be68/power/0","res_type":["oic.r.switch.binary"],"res_uri":"/oic/route/6341cb6f-2179-55a3-3732-c3ffbad1be68/power/0","res_isobserve":true,"res_dev_id":"6341cb6f-2179-55a3-3732-c3ffbad1be68"}]}}
		String buffer = "{\"type\":\"res\",\"dir\":\"c2d\",\"work_code\":\"dis_res\",\"body\":{\"status\":200,\"resources\":[{\"res_id\":\"/oic/route/6341cb6f-2179-55a3-3732-c3ffbad1be68/power/0\",\"res_type\":[\"oic.r.switch.binary\"],\"res_uri\":\"/oic/route/6341cb6f-2179-55a3-3732-c3ffbad1be68/power/0\",\"res_isobserve\":true,\"res_dev_id\":\"6341cb6f-2179-55a3-3732-c3ffbad1be68\"}]}}";		
		//Import From Json
		try {
			JSONObject jsonSchema0Cmd = new JSONObject(buffer);
			System.out.println(" 2 receive cmd = " + jsonSchema0Cmd.toString());
			// Import jsonObject
			Schema0Cmd recvSchema0Cmd = new Schema0Cmd();
			recvSchema0Cmd.importFromJson(jsonSchema0Cmd);
			JSONObject jsonExportBody = recvSchema0Cmd.exportToJson();
			if (jsonExportBody.toString().equals(jsonSchema0Cmd.toString()))
				System.out.println(" 2 after import = equal ? true ^O^)/");
			else
				System.out.println(" 2 after import = " + jsonExportBody.toString());
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
