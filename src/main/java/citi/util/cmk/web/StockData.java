package citi.util.cmk.web;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import citi.util.cmk.Stock.FinanceOneDay;
import citi.util.cmk.dbOp.DBConnection;

public class StockData {
	private DBConnection dbConnection = new DBConnection();
	private String code;
	private ArrayList<FinanceOneDay> arrayList = new ArrayList<FinanceOneDay>();

	public StockData(String code) {
		this.code = code;
	}

	public ArrayList<FinanceOneDay> init() {
		try {
			Connection connection = dbConnection.getCon();
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(
					"select date,open,close,low,high from finance where code='" + code + "' order by date desc;");
			while (resultSet.next()) {
				String date = resultSet.getString(1);
				double open = resultSet.getDouble(2);
				double close = resultSet.getDouble(3);
				double low = resultSet.getDouble(4);
				double high = resultSet.getDouble(5);
				FinanceOneDay financeOneDay = new FinanceOneDay(date, open, close, low, high);
				arrayList.add(financeOneDay);
			}
			if (arrayList.size() == 0) {
				return null;
			}
			return arrayList;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public String toJson() {
		String json = "noInfo";
		if (init() != null) {
			json = "{\"stock\":\"" + code + "\",\"data\":[\n";
			for (FinanceOneDay a : arrayList) {
				json += "{\"date\":\"" + a.getDate() + "\"" + ",\"open\":" + a.getOpen() + "" + ",\"close\":"
						+ a.getClose() + "" + ",\"low\":" + a.getLow() + "" + ",\"high\":" + a.getHigh() + "" + "},\n";
			}
			json = json.substring(0, json.length() - 2);
			json += "\n]}";
		}
		System.out.println(json);
		return json;
	}
	

	public static void main(String[] args) throws IOException {
		StockData aData = new StockData("600066");
		String data = aData.toJson();

		File file = new File("./pakage/new.txt");
		if (!file.exists()) {
			file.createNewFile();
		}
		FileWriter fileWritter = new FileWriter(file, true);
		BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
		bufferWritter.write(data);
		bufferWritter.close();
		System.out.println("Done");
	}
}
