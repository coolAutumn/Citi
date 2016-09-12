package citi.util.cmk.web;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;

import citi.util.cmk.dbOp.DBConnection;

public class Frequency {
	private DBConnection dbConnection = new DBConnection();
	private LinkedHashMap<String, Integer> aMap = new LinkedHashMap<String, Integer>();
	private String code = "600104";
	private int day = 1;
	private double step = 1;
	private ArrayList<String> dateList;

	public Frequency() {
	}

	public Frequency(String code, int day, double step) {
		this.code = code;
		this.day = day;
		this.step = step;
	}

	public ArrayList<Double> filter() throws Exception {
		try {
			ArrayList<Double> arrayList = new ArrayList<Double>();
			Connection connection = dbConnection.getCon();
			Statement statement = connection.createStatement();
			if (day == 1) {
				dateList=new ArrayList<String>();
				ResultSet resultSet = statement
						.executeQuery("select date,chg,dr,replay from finance where code='" + code + "' order by date");
				while (resultSet.next()) {
					if (!resultSet.getBoolean(3) && !resultSet.getBoolean(4)) {
						dateList.add(resultSet.getString(1));
						double d=resultSet.getDouble(2);
						if(d>-10.50&&d<10.50){
						arrayList.add(d);
						}
					}
				}
			} else {
				dateList=new ArrayList<String>();
				ResultSet resultSet = statement.executeQuery(
						"select date,open,close,dr,replay from finance where code='" + code + "' order by date");
				int day_count = 0;
				double end_today = 0;
				double end_lastday = 0;
				boolean dr_b = false;
				boolean re_b = false;
				while (resultSet.next()) {
					day_count++;
					if (day_count == 1) {
						end_lastday = resultSet.getDouble(2);
					}
					// 周期涨跌幅
					if (!dr_b && !re_b) {
						dr_b = resultSet.getBoolean(4);
						re_b = resultSet.getBoolean(5);
					}
					if (day_count % day == 0) {
						end_today = resultSet.getDouble(3);
						double rate = (end_today - end_lastday) / end_lastday;
						if (!dr_b && !re_b) {
							dateList.add(resultSet.getString(1));
							arrayList.add(rate * 100);
						}
						end_lastday = end_today;
						dr_b = false;
						re_b = false;
					}
				}
			}
			return arrayList;
		} catch (Exception e) {
			e.printStackTrace();
			throw (e);
		}
	}
	public ArrayList<String> getDate() {
		return dateList;
	}

	public void section() throws Exception {
		ArrayList<Double> arrayList = filter();
		LinkedHashMap<String, Integer> aMap = new LinkedHashMap<String, Integer>();
		Collections.sort(arrayList);

		double bigist = arrayList.get(arrayList.size() - 1).doubleValue();
		double smallist = arrayList.get(0).doubleValue();

		int po = (int) Math.ceil((bigist - step / 2) / step);
		int na = (int) Math.ceil((smallist + step / 2) / step) - 1;

		int num = po + Math.abs(na) + 1;
		String[] No = new String[num];
		int[] count = new int[num];

		for (int i = na; i <= po; i++) {
			double floor = getRound(step * i-step/2);
			double ceil = getRound(step * i +step/2);
			No[i-na] = "" + floor + "%~" + ceil + "%";
			for (Double d : arrayList) {
				if (d.doubleValue() <=ceil && d.doubleValue() > floor) {
					count[i-na]++;
					
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

	public String toJson() throws Exception {
		section();
		String json = "{\"" + code + "\":[\n";
		for (String a : aMap.keySet()) {
			String[] group = a.split("~");
			double i = Double.parseDouble(group[0].replace("%", ""));
			double j = Double.parseDouble(group[1].replace("%", ""));
			json += "{\"floor\":" + i + "," + "\"ceil\":" + j + "," + "\"count\":" + aMap.get(a).intValue() + "},\n";
		}
		json = json.substring(0, json.length() - 2);
		json += "\n]}";
		System.out.println(json);
		return json;
	}
	
	public String toTxt() throws Exception{
		section();
		String txt="floor,ceil,avg,num\r\n";
		for (String a : aMap.keySet()) {
			String[] group = a.split("~");
			double i = Double.parseDouble(group[0].replace("%", ""));
			double j = Double.parseDouble(group[1].replace("%", ""));
			txt+=i+","+j+","+getRound((i+j)/2)+","+aMap.get(a).intValue()+"\r\n";
		}
		txt=txt.substring(0,txt.length()-2);
		System.out.println(txt);
		return txt;
	}
	
	public double getRound(double d) {
		return ((double)Math.round(d*100))/100;
	}

	public static void main(String[] args) throws Exception {
		Frequency frequency = new Frequency("600652", 2,0.1);
		frequency.toTxt();
	}
}
