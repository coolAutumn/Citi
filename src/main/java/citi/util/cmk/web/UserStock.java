package citi.util.cmk.web;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import citi.util.cmk.dbOp.DBConnection;

public class UserStock {
	private DBConnection dbConnection = new DBConnection();
	private String phone;
	ArrayList<String> oStock;

	public UserStock(String phone) {
		this.phone = phone;
	}

	private void init() throws Exception {
		oStock = new ArrayList<String>();
		Statement statement = dbConnection.getCon().createStatement();
		ResultSet resultSet = statement.executeQuery("select code from ostock where phone='" + phone + "'");
		while (resultSet.next()) {
			oStock.add(resultSet.getString(1));
		}
	}

	public String toJson() {
		try {
			init();
		} catch (Exception e) {
			e.printStackTrace();
			return "wrong";
		}
		if (oStock.size() == 0) {
			return "nostock";
		}

		String json = "{\"stocks\":[\n";
		for (String a : oStock) {
			json += "{\"code\":\"" + a + "},\n";
		}
		json = json.substring(0, json.length() - 2);
		json += "\n]}";

		return json;
	}
	
	public static void main(String[] args) {
		System.out.println(new UserStock("123456").toJson());
	}

}
