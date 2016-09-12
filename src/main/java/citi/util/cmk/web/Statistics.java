package citi.util.cmk.web;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import citi.util.cmk.Statistic.CalStactic;
import citi.util.cmk.dbOp.DBConnection;

public class Statistics {
	private String date;
	private String codeOrTrade;
	ArrayList<Double> gauss;
	ArrayList<Double> weibu;

	public Statistics(String codeOrTrade) {
		this.codeOrTrade = codeOrTrade;
	}

	private void init() {
		DBConnection dbConnection = new DBConnection();
		try {
			Statement statement = dbConnection.getCon().createStatement();
			ResultSet resultSet = statement.executeQuery(
					"select * from gausspara where codeortrade='" + codeOrTrade + "' order by date desc limit 1");
			resultSet.next();
			gauss = new ArrayList<Double>();
			weibu = new ArrayList<Double>();
			date = resultSet.getString(8);
			for (int i = 1; i <= 6; i++) {
				Double double1 = new Double(resultSet.getString(i));
				System.out.println(double1.doubleValue());
				System.out.println(i);
				gauss.add(double1.doubleValue());
			}
			resultSet = statement.executeQuery("select * from weibupara where codeortrade='" + codeOrTrade + "' order by date desc limit 1");
			resultSet.next();
			for (int i = 1; i <= 2; i++) {
				weibu.add(new Double(resultSet.getString(i)));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public String toJson() {
		init();
		if (gauss == null || weibu == null || gauss.size() != 6 || weibu.size() != 2) {
			System.out.println(gauss.size());
			System.out.println(weibu.size());
			return "error";
		}
		ArrayList<Double> ys = new ArrayList<Double>();
		for (int x = -10; x < 11; x++) {
			double ansx = gauss.get(0) * Math.exp(-Math.pow((x - gauss.get(1)) / gauss.get(2), 2))
					+ gauss.get(3) * Math.exp(-Math.pow(((x - gauss.get(4)) / gauss.get(5)), 2));
			ys.add(ansx);
		}
		String json = "{\"date:\":" + date + "\",\n";
		json += "\"codeOrTrade\":\"" + codeOrTrade + "\",\n";
		json += "\"funtionOfGauss\":\"" + CalStactic.GAUSS + "\",\n";
		json += "\"paraOfGauss\":[";
		for (Double d : gauss) {
			json += d.doubleValue() + ",";
		}
		json = json.substring(0, json.length() - 1);
		json += "],\n";
		json += "\"xs\":[";
		for (int x = -10; x < 11; x++) {
			json += x + ",";
		}
		json = json.substring(0, json.length() - 1) + "],\n";
		json += "\"ys\":[";
		for (Double d : ys) {
			json += d.doubleValue() + ",";
		}
		json = json.substring(0, json.length() - 1);
		json += "]}";
		return json;
	}

	public static void main(String[] args) {
		System.out.println(new Statistics("600066").toJson());
	}
}
