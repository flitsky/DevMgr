package DAO;
import java.sql.*;

import org.json.JSONObject;

public class AriesDAO {
	// JDBC driver name and database URL
	static String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	static String DB_URL = "jdbc:mysql://35.187.218.168:3306/Aries?useSSL=false";
	// Database credentials
	static final String USER = "username";
	static final String PASS = "password";
	static Connection conn = null;
	static Statement stmt = null;

	public AriesDAO() {
		createTable();
	}
	
	public static Connection getConn() throws Exception {
		// STEP 2: Register JDBC driver
		Class.forName(JDBC_DRIVER);

		// STEP 3: Open a connection
		System.out.println("Connecting to a selected database...");
		conn = DriverManager.getConnection(DB_URL, USER, PASS);
		System.out.println("Connected database successfully...");
		return conn;
	}

	public static void createTable() {
		try {
			conn = getConn();
			stmt = conn.createStatement();

			System.out.println("Creating table if not exists...");

			String sql = "CREATE TABLE if not exists tb_user (" +
	                "uid VARCHAR(64) NOT NULL" +
	                ", access_token VARCHAR(64) NOT NULL" +
	                ", created_timestamp TEXT" +
	                ", last_timestamp TEXT" +
	                ",     PRIMARY KEY (uid)" +
	                " );";
			stmt.executeUpdate(sql);

			sql = "CREATE TABLE if not exists tb_device (" +
	                "device_id VARCHAR(128) NOT NULL" +
	                ", user_id VARCHAR(64) NOT NULL" +
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
	                ",     FOREIGN KEY (user_id) REFERENCES tb_user(uid)" +
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
	                ", property_name VARCHAR(128) NOT NULL" +
	                ", property_type VARCHAR(128) NOT NULL" +
	                ", property_permission TINYINT(128) NOT NULL" +
	                ", last_value VARCHAR(64) NOT NULL" +
	                ", created_timestamp TEXT" +
	                ", last_timestamp TEXT" +
	                ",     PRIMARY KEY (property_id)" +
	                ",     FOREIGN KEY (resource_id) REFERENCES tb_resource(resource_id)" +
	                " );";
			stmt.executeUpdate(sql);
			
			sql = "CREATE TABLE if not exists tb_service (" +
	                "service_id VARCHAR(128) NOT NULL" +
	                ", user_id VARCHAR(128) NOT NULL" +
	                ", service_name VARCHAR(128) NOT NULL" +
	                ", service_status INT NOT NULL" +
	                ", service_version VARCHAR(32) NOT NULL" +
	                ", script_path VARCHAR(256) NOT NULL" +
	                ", created_timestamp TEXT" +
	                ", last_timestamp TEXT" +
	                ",     PRIMARY KEY (service_id)" +
	                ",     FOREIGN KEY (user_id) REFERENCES tb_user(uid)" +
	                " );";
			stmt.executeUpdate(sql);
			
			sql = "CREATE TABLE if not exists tb_user_setting (" +
	                "service_id VARCHAR(128) NOT NULL" +
	                ", user_setting_name VARCHAR(128) NOT NULL" +
	                ", user_setting_type INT" +
	                ", user_setting_value VARCHAR(64)" +
	                ", user_setting_default_value VARCHAR(64)" +
	                ", created_timestamp TEXT" +
	                ", last_timestamp TEXT" +
	                ",     PRIMARY KEY (service_id)" +
	                ",     FOREIGN KEY (service_id) REFERENCES tb_service(service_id)" +
	                " );";
			stmt.executeUpdate(sql);

			sql = "CREATE TABLE if not exists tb_resource_map (" +
	                "service_id VARCHAR(128) NOT NULL" +
	                ", shortened_resource_name VARCHAR(128) NOT NULL" +
	                ", trigger_status INT" +
	                ", resource_id VARCHAR(128)" +
	                ", created_timestamp TEXT" +
	                ", last_timestamp TEXT" +
	                ",     PRIMARY KEY (service_id)" +
	                ",     FOREIGN KEY (service_id) REFERENCES tb_service(service_id)" +
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
		System.out.println("Done!");
	}
	
	public boolean createDevice(String device_id, String user_id, String device_type, String device_name,
            String user_defined_name, String manufacturer, String server_version,
            String spec_version, Integer device_status, String created_timestamp, String last_timestamp) {
		try {
			conn = getConn();
			stmt = conn.createStatement();
			
			String sql = "INSERT INTO tb_device VALUES('" + device_id + "', '" + user_id + "', '" + device_type + "', '"
	                + device_name + "', '" + user_defined_name + "', '" + manufacturer + "', '"
	                + server_version + "', '" + spec_version + "', " + device_status + ", '"
	                + created_timestamp + "', '" + last_timestamp + "');";
			stmt.executeUpdate(sql);
		} catch(Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public Object[] readDevice(String device_id) {
		try {
			conn = getConn();
			//stmt = conn.createStatement();

			String sql = "SELECT * FROM tb_device WHERE device_id='" + device_id + "';";
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Object data[] = {rs.getString(0), rs.getString(2),rs.getString(3), rs.getString(4), rs.getString(5),
						rs.getString(6),rs.getString(7), rs.getString(8),rs.getString(9), rs.getString(10)};
			}
			return null; //device class
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public boolean updateDevice(String device_id, String user_defined_name, Integer device_status,
			String last_timestamp) {
		try {
			conn = getConn();
			stmt = conn.createStatement();

			String sql = "UPDATE tb_device SET user_defined_name='" + user_defined_name + "' device_status="
					+ device_status + " last_timestamp='" + last_timestamp + "' WHERE device_id='" + device_id + "';";
			stmt.executeUpdate(sql);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public boolean deleteDevice(String device_id) {
		try {
			conn = getConn();
			stmt = conn.createStatement();

			String sql = "DELETE FROM tb_device WHERE device_id='" + device_id + "';";
			stmt.executeUpdate(sql);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public boolean createResource(String resource_id, String device_id, String resource_type, String resource_uri,
			String resource_name, Integer is_observable, Integer ref_count, String created_timestamp,
			String last_timestamp) {
		try {
			conn = getConn();
			stmt = conn.createStatement();

			String sql = "INSERT INTO tb_resource VALUES('" + resource_id + "', '" + device_id + "', '"
	                + resource_type + "', '" + resource_uri + "', '" + resource_name + "', "
	                + is_observable + ", " + ref_count + ", '"
	                + created_timestamp + "', '" + last_timestamp + "');";
			stmt.executeUpdate(sql);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	
	public boolean readResource(String resource_id) {
		try {
			conn = getConn();
			stmt = conn.createStatement();

			String sql = "SELECT * FROM tb_resource WHERE resource_id='" + resource_id + "';";
			stmt.executeUpdate(sql);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public boolean updateResource(String resource_id, String resource_name, String last_timestamp) {
		try {
			conn = getConn();
			stmt = conn.createStatement();

			String sql = "UPDATE tb_resource SET resource_name='" + resource_name + " last_timestamp='" + last_timestamp + "' WHERE resource_id='" + resource_id + "';";
			stmt.executeUpdate(sql);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public boolean deleteResource(String resource_id) {
		try {
			conn = getConn();
			stmt = conn.createStatement();

			String sql = "DELETE FROM tb_resource WHERE resource_id='" + resource_id + "';";
			stmt.executeUpdate(sql);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	

	public boolean createProperty() {
		try {
			conn = getConn();
			stmt = conn.createStatement();
			
			String sql = "";
			stmt.executeUpdate(sql);
		} catch(Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public boolean create() {
		try {
			conn = getConn();
			stmt = conn.createStatement();
			
			String sql = "";
			stmt.executeUpdate(sql);
		} catch(Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public boolean createService() {
		
		return true;
	}
}
