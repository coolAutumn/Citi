package citi.util.cmk.web;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import citi.util.cmk.FIFO.PinYin;
import citi.util.cmk.Stock.StockInfo;
import citi.util.cmk.dbOp.DBConnection;

public class CodeList {
	DBConnection dbConnection = new DBConnection();
	private ArrayList<StockInfo> stockInfos = new ArrayList<StockInfo>();

	public ArrayList<StockInfo> init() {
		ArrayList<StockInfo> arrayList = new ArrayList<StockInfo>();
		try {
			Connection connection = dbConnection.getCon();
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery("select code,sname,type from company order by code");
			while (resultSet.next()) {
				String code = resultSet.getString(1);
				String sname = resultSet.getString(2);
				String trade=resultSet.getString(3);
				StockInfo temp = new StockInfo(code, sname,trade);
				arrayList.add(temp);
			}
			Collections.sort(arrayList, new Comparator<StockInfo>() {
				public int compare(StockInfo o1, StockInfo o2) {
					return o1.getCode().compareTo(o2.getCode());
				}
			});
			stockInfos = arrayList;
			return arrayList;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public String toJson() throws Exception {
		init();
		String json = "{\"stocks\":[\n";
		for (StockInfo a : stockInfos) {
			System.out.println(a.getSname());
			json += "{\"code\":\"" + a.getCode() + "\"" 
		     + ",\"stockname\":\"" + a.getSname() + "\""
		     + ",\"trade\":\"" + a.getTrade() + "\""
		     + ",\"abb\":\"" + PinYin.converterToFirstSpell(a.getSname()) + "\""
					+ "},\n";
		}
		json = json.substring(0, json.length() - 2);
		json += "\n]}";
		
		return json;
	}
	public static void main(String[] args) throws Exception {
		CodeList codeList=new CodeList();
		System.out.println(codeList.toJson());
	}
}
