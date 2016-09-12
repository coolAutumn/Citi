package citi.util.cmk.dbOp;

import java.sql.Connection;
import java.sql.Statement;
import java.util.List;

import citi.util.cmk.crawler.DRInfo;
import citi.util.cmk.dbOp.DBConnection;

public class DRSetting {
	private DBConnection dbConnection = null;
	private Connection connection = null;
	private Statement statement = null;

	public DRSetting() {
		try {
			dbConnection = new DBConnection();
			connection = dbConnection.getCon();
			statement = connection.createStatement();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public boolean DR(String stockCode) {
		try {
			DRInfo stockDayInfo = new DRInfo(stockCode);
			List<String> dayList = stockDayInfo.getOneDayList();
			System.out.println(stockCode + ":");
			for (String day : dayList) {
				System.out.println(day);
				statement.executeUpdate(
						"update finance " + "set dr=true " + "where code='" + stockCode + "' and date='" + day + "';");
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public static void main(String[] argvs) {
		DRSetting setting = new DRSetting();
		if (setting.DR("600066")) {
			System.out.println("finishied");
		}
	}
}
