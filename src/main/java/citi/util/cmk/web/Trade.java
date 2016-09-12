package citi.util.cmk.web;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import citi.util.cmk.FIFO.Temp;
import citi.util.cmk.dbOp.DBConnection;

public class Trade {
	private Connection connection;
	private ArrayList<String> stockList;
	private Statement statement;
	private double step = 0.1;
	private LinkedHashMap<String, Integer> aMap = new LinkedHashMap<String,Integer>();
	private String trade = null;

	public Trade(String trade) {
		this.trade = trade;
		stockList = new ArrayList<String>();
		try {
			connection = new DBConnection().getCon();
			statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery("select code from company where type='" + trade + "'");
			while (resultSet.next()) {
				stockList.add(resultSet.getString(1));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public ArrayList<String> calDate() {
		try {
			ArrayList<String> dateList = new ArrayList<String>();
			for (int i = 0; i < stockList.size(); i++) {
				ResultSet resultSet = statement.executeQuery(
						"select date,dr,replay from finance where code='" + stockList.get(i) + "' order by date;");
				if (i == 0) {
					while (resultSet.next()) {
						if (!resultSet.getBoolean(2) && !resultSet.getBoolean(3)) {
							dateList.add(resultSet.getString(1));
						}
					}
				} else {
					ArrayList<String> temp = new ArrayList<String>();
					while (resultSet.next()) {
						if (!resultSet.getBoolean(2) && !resultSet.getBoolean(3)) {
							temp.add(resultSet.getString(1));
						}
					}
					temp.removeAll(dateList);
					dateList.addAll(temp);
				}
			}
			Collections.sort(dateList);
			return dateList;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public double getRound(double d) {
		return ((double) Math.round(d * 100)) / 100;
	}

	public ArrayList<Double> calNum() {
		try {
			ArrayList<String> dates = calDate();
			ArrayList<Double> syn = new ArrayList<Double>();
			ArrayList<Map<String, Double>> stocksChg = new ArrayList<Map<String, Double>>();
			ArrayList<Map<String, Double>> stocksVal = new ArrayList<Map<String, Double>>();
			for (String s : stockList) {
				Map<String, Double> chg = new HashMap<String, Double>();
				Map<String, Double> val = new HashMap<String, Double>();
				ResultSet resultSet = statement.executeQuery(
						"select date,chg,value,dr,replay from finance where code='" + s + "' order by date;");
				while (resultSet.next()) {
					if (!resultSet.getBoolean(4) && !resultSet.getBoolean(5)) {
						chg.put(resultSet.getString(1), resultSet.getDouble(2));
						val.put(resultSet.getString(1), resultSet.getDouble(3));
					}
				}
				stocksChg.add(chg);
				stocksVal.add(val);
			}
			for (int i = 0; i < dates.size(); i++) {
				double d = 0;
				double totalVal = 0;
				String day = dates.get(i);
				for (int j = 0; j < stockList.size(); j++) {
					if (stocksChg.get(j).get(day) != null) {
						d += stocksChg.get(j).get(day).doubleValue() * stocksVal.get(j).get(day).doubleValue();
						totalVal += stocksVal.get(j).get(day).doubleValue();
					}
				}
				double t=d / totalVal;
				if(t>-10.5&&t<10.5){
					syn.add(getRound(d / totalVal));
				}
			}
			return syn;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public void section() {
		ArrayList<Double> arrayList = calNum();
		LinkedHashMap<String, Integer> aMap = new LinkedHashMap<String, Integer>();
		Collections.sort(arrayList);
		if (arrayList.size() == 0) {
			return;
		}
		double bigist = arrayList.get(arrayList.size() - 1).doubleValue();
		double smallist = arrayList.get(0).doubleValue();

		int po = (int) Math.ceil((bigist - step / 2) / step);
		int na = (int) Math.ceil((smallist + step / 2) / step) - 1;

		int num = po + Math.abs(na) + 1;
		String[] No = new String[num];
		int[] count = new int[num];

		for (int i = na; i <= po; i++) {
			double floor = getRound(step * i - step / 2);
			double ceil = getRound(step * i + step / 2);
			No[i - na] = "" + floor + "%~" + ceil + "%";
			for (Double d : arrayList) {
				if (d.doubleValue() <= ceil && d.doubleValue() > floor) {
					count[i - na]++;
				} else if (d.doubleValue() >= ceil) {
					break;
				}
			}
		}
		for (int i = 0; i < num; i++) {
			aMap.put(No[i], count[i]);
		}
		this.aMap = aMap;
	}

	public String toTxt() {
		section();
		String txt = "floor,ceil,avg,num\r\n";
		for (String a : aMap.keySet()) {
			String[] group = a.split("~");
			double i = Double.parseDouble(group[0].replace("%", ""));
			double j = Double.parseDouble(group[1].replace("%", ""));
			txt += i + "," + j + "," + getRound((i + j) / 2) + "," + aMap.get(a).intValue() + "\r\n";
		}
		txt = txt.substring(0, txt.length() - 2);
		System.out.println(txt);
		if (aMap.size() == 0) {
			return "error";
		}
		return txt;
	}

	public String toJson() throws Exception {
		section();
		String json = "{\"" + trade + "\":[\n";
		for (String a : aMap.keySet()) {
			String[] group = a.split("~");
			double i = Double.parseDouble(group[0].replace("%", ""));
			double j = Double.parseDouble(group[1].replace("%", ""));
			json += "{\"floor\":" + i + "," + "\"ceil\":" + j + "," + "\"count\":" + aMap.get(a).intValue() + "},\n";
		}
		json = json.substring(0, json.length() - 2);
		json += "\n]}";
		if(aMap.size()==0){
			return "error";
		}
		System.out.println(json);
		return json;
	}

	public static void main(String[] args) throws Exception {
		Trade trade = new Trade("互联网+");
		Temp temp=new Temp();
		temp.MyWrite(trade.toTxt(), "./pakage/互联网+.csv");
		Trade trade1 = new Trade("新能源汽车");
		temp.MyWrite(trade1.toTxt(), "./pakage/新能源汽车.csv");
	}

}
