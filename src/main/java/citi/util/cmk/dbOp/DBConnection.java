package citi.util.cmk.dbOp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.ArrayList;

public class DBConnection {
	private Connection connection = null;
	private String name = "root";
	private String passwd = "Llxfiamlucky";
	public final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	public final String TEST = "org.postgresql.Driver";

	public DBConnection() {
		
	}

	public DBConnection(String name, String passwd) {
		this.name = name;
		this.passwd = passwd;
	}

	public Connection getCon() {
		try {
			Class.forName(JDBC_DRIVER);
			connection = DriverManager.getConnection("jdbc:mysql://114.215.83.57:3306/citi?useUnicode=true&characterEncoding=UTF-8&characterSetResults=UTF-8", name, passwd);
			connection.prepareStatement("set character_set_results=utf8;").execute();
			System.out.println("---------get connection successfully---------");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return connection;
	}

	public static void main(String[] argvs) {
		try {
			System.out.println("begain");
			ArrayList<String> stockList = new ArrayList<String>();
			stockList.add("600418");
			stockList.add("002594");
			stockList.add("000957");
			stockList.add("600104");
			stockList.add("601238");
			stockList.add("600066");
			stockList.add("000625");
			DBConnection dbConnection = new DBConnection();
			Connection connection = dbConnection.getCon();
			Statement statement = connection.createStatement();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
