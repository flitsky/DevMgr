import java.io.FileNotFoundException;

import org.json.JSONArray;
import org.json.JSONObject;

import DAO.AriesDAO;
import interoperate.C2D11Discovery;
import interoperate.Schema0Cmd;
import model.TbDevice;

public class DM {

	public static void main(String[] args) {
		final AriesDAO ariesDAO = new AriesDAO();
		TbDevice device = new TbDevice();
		
		//-- 1.Send Discovery Device Request
		//{"type":"req","dir":"d2c","work_code":"dis_dev","body":{}}
		Schema0Cmd cmd = new Schema0Cmd();
		cmd.type = "req";
		cmd.direction = "d2c";
		cmd.work_code = "dis_dev";
		// Export jsonObject
		try {
			JSONObject jsonSchema0Cmd;
			jsonSchema0Cmd = cmd.exportToJson();
			// check modified data
			System.out.println(" 1 jsonSchema0Cmd = " + jsonSchema0Cmd.toString());
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		//-- 2.Receive Discovery Device Result
		//{"type":"res","dir":"c2d","work_code":"dis_dev","body":{"status":200,"devices":[{"dev_id":"c31e8fa3-b524-0e6b-2489-77760c3ca37b","dev_name":"THU Light","spec_ver":"ocf.1.1.0","dev_type":"[oic.wk.d, oic.d.light]","host_addr":"coap:\/\/[fe80::c4a8:5af:7d0e:f40e%25wlan0]:49244"}]}}
		String buffer = "{\"type\":\"res\",\"dir\":\"c2d\",\"work_code\":\"dis_dev\",\"body\":{\"status\":200,\"devices\":[{\"dev_id\":\"c31e8fa3-b524-0e6b-2489-77760c3ca37b\",\"dev_name\":\"THU Light\",\"spec_ver\":\"ocf.1.1.0\",\"dev_type\":\"[oic.wk.d, oic.d.light]\",\"host_addr\":\"coap:\\/\\/[fe80::c4a8:5af:7d0e:f40e%25wlan0]:49244\"}]}}";		

		//Import From Json
		try {
			JSONObject jsonSchema0Cmd;
			jsonSchema0Cmd = new JSONObject(buffer);
			// Import jsonObject
			Schema0Cmd cmdschema = new Schema0Cmd();
			cmdschema.importFromJson(jsonSchema0Cmd);
			JSONObject jsonExportBody = cmdschema.exportToJson();
			System.out.println(" 2 jsonExportBody = " + jsonExportBody.toString());
			
			C2D11Discovery discov = new C2D11Discovery();
			discov.importFromJson(jsonSchema0Cmd);
			// Modify some data
			discov.devices.get(0).dev_name = "Modified dev name kkk~";
			System.out.println(" 3 jsonCC = " + jsonSchema0Cmd.toString());
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
