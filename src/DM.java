import java.io.FileInputStream;
import java.io.InputStream;

import org.json.JSONObject;

import interoperate.C2D11Discovery;

public class DM {

	public static void main(String[] args) {
		final AriesDAO ariesDAO = new AriesDAO();
		
		C2D11Discovery discov = new C2D11Discovery();
		testfuncImportFromCCJson(discov);
	}

	static void testfuncImportFromCCJson(C2D11Discovery discov) {
		try {
			// [ dev import ] -->
			InputStream is = new FileInputStream("C2D_resDiscovery(dev).json");
			byte[] buffer = new byte[is.available()];
			is.read(buffer);
			is.close();
			JSONObject jsonCC = new JSONObject(new String(buffer, "UTF-8"));
			// Import jsonObject
			discov.importFromJson(jsonCC);
			// Modify some data
			discov.devices.get(0).dev_name = "Modified dev name kkk~";

			// [ rsrc import ] -->
			is = new FileInputStream("C2D_resDiscovery(rsrc).json");
			buffer = new byte[is.available()];
			is.read(buffer);
			is.close();
			jsonCC = new JSONObject(new String(buffer, "UTF-8"));
			// JSONObject.toString
			System.out.println("  jsonCC = " + jsonCC.toString());
			// Import jsonObject
			discov.importFromJson(jsonCC);
			// Modify some data
			discov.resource.get(0).res_uri = "/Modified/rsrc/uri/kkk~";

			// Export jsonObject
			jsonCC = discov.exportToJson();
			// check modified data
			System.out.println("  jsonCC = " + jsonCC.toString());

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
