package citi.util.cmk.dbOp;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import citi.util.cmk.Statistic.CalStactic;


public class ParaSetting {
	private DBConnection dbConnection;
	private Connection connection;
	private Statement statement;
	private SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
	private CalStactic calStactic;

	public ParaSetting(){
		try {
			dbConnection = new DBConnection();
			connection = dbConnection.getCon();
			statement = connection.createStatement();
			calStactic = new CalStactic();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void paraSetting(String codeOrTrade) throws Exception {
		if (codeOrTrade.length() == 6 && codeOrTrade.matches("[0-9]+")) {
			calStactic.getStockPara(codeOrTrade);
		} else {
			calStactic.getTradePara(codeOrTrade);
		}
		ArrayList<Double> gauss = calStactic.getGaussPara();
		ArrayList<Double> weibu = calStactic.getWeibuPara();
		Date today = new Date();
		String sql = "insert into gausspara values('" + gauss.get(0).doubleValue() + "','" + gauss.get(1).doubleValue()
				+ "','" + gauss.get(2).doubleValue() + "','" + gauss.get(3).doubleValue() + "','"
				+ gauss.get(4).doubleValue() + "','" + gauss.get(5).doubleValue() + "','" + codeOrTrade + "','"
				+ df.format(today) + "')";
		statement.execute(sql);
		sql = "insert into weibupara values('" + weibu.get(0).doubleValue() + "','" + weibu.get(1).doubleValue() + "','"
				+ codeOrTrade + "','" + df.format(today) + "')";
		statement.execute(sql);
	}

	public static void main(String[] args) throws Exception {
		Statement statement = new DBConnection().getCon().createStatement();
		ResultSet resultSet = statement.executeQuery("select code from company");
		ArrayList<String> arrayList = new ArrayList<String>();
		while (resultSet.next()) {
			arrayList.add(resultSet.getString(1));
		}
		resultSet = statement.executeQuery("select distinct type from company");
		while (resultSet.next()) {
			arrayList.add(resultSet.getString(1));
		}
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		resultSet = statement.executeQuery("select codeortrade from weibupara where date='"+df.format(new Date())+"'");
		while (resultSet.next()) {
			arrayList.remove(resultSet.getString(1));
		}
		resultSet = statement.executeQuery("select codeortrade from gausspara where date='"+df.format(new Date())+"'");
		while (resultSet.next()) {
			arrayList.remove(resultSet.getString(1));
		}
		ParaSetting paraSetting = new ParaSetting();
		for (String a : arrayList) {
			System.err.println(a);
			paraSetting.paraSetting(a);
		}
	}
}
