package citi.util.cmk.dbOp;

import java.sql.Connection;
import java.sql.Statement;
import java.util.Map;

import citi.util.cmk.crawler.ReplayInfo;


public class ReplaySetting {
	private DBConnection dbConnection = null;
	private Connection connection = null;
	private Statement statement = null;
	
	public ReplaySetting() {
		try {
			dbConnection = new DBConnection();
			connection = dbConnection.getCon();
			statement = connection.createStatement();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public boolean replay(String stockCode) {
		try {
			
			ReplayInfo stockDays = new ReplayInfo(stockCode);
			Map<String, String> temp = stockDays.getOneCloseAndOpen();
			System.out.println(stockCode); 
			for (String close : temp.keySet()) {
				statement.executeUpdate("update finance set replay=true " + "where code='" + stockCode + "' "
						+ "and date between '" + close + "' and '" + temp.get(close) + "';");
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public static void main(String[] argvs) {
		ReplaySetting replaySetting = new ReplaySetting();
		if (replaySetting.replay("600066")) {
			System.out.println("successful");
		}
	}

}
