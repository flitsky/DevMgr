import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.*;

import org.json.JSONObject;

import interoperate.C2D11Discovery;

public class DM {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("Hello world!");

		C2D11Discovery discov = new C2D11Discovery();
		testfuncImportFromCCJson(discov);
		testfuncInsertData();

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

	static void testfuncInsertData() {
		// JDBC driver name and database URL
		final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
		// final String DB_URL = "jdbc:mysql://localhost/STUDENTS?useSSL=false";
		final String DB_URL = "jdbc:mysql://35.187.218.168:3306/Aries?useSSL=false";

		// Database credentials
		final String USER = "username";
		final String PASS = "password";

		Connection conn = null;
		Statement stmt = null;
		try {
			// STEP 2: Register JDBC driver
			Class.forName("com.mysql.jdbc.Driver");

			// STEP 3: Open a connection
			System.out.println("Connecting to a selected database...");
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			System.out.println("Connected database successfully...");

			// STEP 4: Execute a query
			System.out.println("Creating table in given database...");
			stmt = conn.createStatement();

			String sql = "CREATE TABLE if not exists tb_device (" +
	                "device_id VARCHAR(128) NOT NULL" +
	                ", device_type VARCHAR(128) NOT NULL" +
	                ", device_name VARCHAR(128) NOT NULL" +
	                ", user_defined_name VARCHAR(128) NOT NULL" +
	                ", manufacturer VARCHAR(128)" +
	                ", server_version VARCHAR(32)" +
	                ", spec_version VARCHAR(32)" +
	                ", device_status INTEGER NOT NULL" +
	                ", created_timestamp TEXT" +
	                ", last_timestamp TEXT" +
	                ",     PRIMARY KEY (device_id)" +
	                " );";
			stmt.executeUpdate(sql);
			
			sql = "CREATE TABLE if not exists tb_resource (" +
	                "resource_id VARCHAR(128) NOT NULL" +
	                ", device_id VARCHAR(128) NOT NULL" +
	                ", resource_type VARCHAR(128) NOT NULL" +
	                ", resource_uri VARCHAR(128) NOT NULL" +
	                ", resource_name VARCHAR(128)" +
	                ", is_observable TINYINT NOT NULL" +
	                ", ref_count SMALLINT NOT NULL" +
	                ", created_timestamp TEXT" +
	                ", last_timestamp TEXT" +
	                ",     PRIMARY KEY (resource_id)" +
	                ",     FOREIGN KEY (device_id) REFERENCES tb_device(device_id)" +
	                " );";
			stmt.executeUpdate(sql);
			
			sql = "CREATE TABLE if not exists tb_property (" +
	                "property_id VARCHAR(128) NOT NULL" +
	                ", resource_id VARCHAR(128) NOT NULL" +
	                ", property_name VARCHAR(128)" +
	                ", property_type VARCHAR(128) NOT NULL" +
	                ", property_permission TINYINT(128) NOT NULL" +
	                ", last_value VARCHAR(64) NOT NULL" +
	                ", created_timestamp TEXT" +
	                ", last_timestamp TEXT" +
	                ",     PRIMARY KEY (property_id)" +
	                ",     FOREIGN KEY (resource_id) REFERENCES tb_resource(resource_id)" +
	                " );";
			stmt.executeUpdate(sql);
			
			System.out.println("Created table in given database...");
		} catch (SQLException se) {
			// Handle errors for JDBC
			se.printStackTrace();
		} catch (Exception e) {
			// Handle errors for Class.forName
			e.printStackTrace();
		} finally {
			// finally block used to close resources
			try {
				if (stmt != null)
					conn.close();
			} catch (SQLException se) {
			} // do nothing
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException se) {
				se.printStackTrace();
			} // end finally try
		} // end try
		System.out.println("Goodbye!");
	}
}
