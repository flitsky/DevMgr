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
		System.out.println("Done!");
	}
	
	public boolean insertDevice() {
		try {
			conn = getConn();
			stmt = conn.createStatement();
			
			String sql = "";
			stmt.executeUpdate(sql);
		} catch(Exception e) {
			e.printStackTrace();
		}
		return true;
	}
}
