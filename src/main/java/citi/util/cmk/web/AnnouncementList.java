package citi.util.cmk.web;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import citi.util.cmk.Stock.Announcement;
import citi.util.cmk.dbOp.DBConnection;

public class AnnouncementList {
	private DBConnection dbConnection = new DBConnection();
	private String code = null;
	private int limit_day = 700;
	private ArrayList<Announcement> newsArrayList;
	
	public AnnouncementList(String a) {
		this.code=a;
	}

	public AnnouncementList(String code,int limit_day) {
		this.code = code;
		this.limit_day = limit_day;
	}

	public ArrayList<Announcement> init() {
		try {
			ArrayList<Announcement> arrayList = new ArrayList<Announcement>();
			Connection connection = dbConnection.getCon();
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement
					.executeQuery("select * from announcement where code='" + code + "' order by ptime desc;");
			Date now = new Date();
			while (resultSet.next()) {
				Date pDate = resultSet.getDate(2);
				System.out.println(pDate.toString());
				int day = daybetween(pDate, now);
				if (day < limit_day) {
					Announcement announcement = new Announcement();
					announcement.setPtime(pDate);
					announcement.setTitle(resultSet.getString(3));
					announcement.setUrl(resultSet.getString(4));
					arrayList.add(announcement);
				} else {
					break;
				}
			}
			this.newsArrayList = arrayList;
			return arrayList;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public int daybetween(Date smalldate, Date bigdate) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(smalldate);
		long time1 = cal.getTimeInMillis();
		cal.setTime(bigdate);
		long time2 = cal.getTimeInMillis();
		long between_days = (time2 - time1) / (1000 * 3600 * 24);
		return Integer.parseInt(String.valueOf(between_days));
	}

	public String toJson() {
		init();
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String json = "{\"" + code + "\":[\n";
		for (Announcement a : newsArrayList) {
			json += "{\"pubDate\":\"" + format.format(a.getPtime()) + "\"" + ",\"title\":\"" + a.getTitle() + "\""
					+ ",\"url\":\"" + a.getUrl() + "\"" + "},\n";
		}
		json = json.substring(0, json.length() - 2);
		json += "\n]}";
		return json;
	}

	public static void main(String[] args) {
		AnnouncementList announcementList= new AnnouncementList("600418");
		System.out.println(announcementList.toJson());
	}

}
