package citi.util.cmk.web;

import java.sql.ResultSet;
import java.sql.Statement;

import citi.util.cmk.dbOp.DBConnection;

public class ForecastData {
	private String date;
	private String codeOrTrade;
	private Double yield;
	private Double risk;
	
	
	public ForecastData(String codeortrade) {
		this.codeOrTrade=codeortrade;
	}
	
	private void init() {
		DBConnection dbConnection = new DBConnection();
		try {
			Statement statement = dbConnection.getCon().createStatement();
			ResultSet resultSet = statement.executeQuery(
					"select * from forecast where codeortrade='" + codeOrTrade + "' order by date desc limit 1");
			resultSet.next();
			date=resultSet.getString(2).substring(0, 10);
			yield=new Double(resultSet.getDouble(3));
			risk=new Double(resultSet.getDouble(4));
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public String  toJson() {
		init();
		if(yield==null||risk==null){
			return "error";
		}
		String json = "{\"" + codeOrTrade + "\":\""+codeOrTrade+"\",\n";
		json+="\"date\":\""+date+"\",\n";
		json+="\"yield\":"+yield.doubleValue()+",\n";
		json+="\"risk\":"+risk.doubleValue()+"\n";
		json += "]}";
		System.out.println(json);
		return json;
	}
	public static void main(String[] args) {
		ForecastData forecastData =new ForecastData("600066");
		forecastData.toJson();
	}

}
