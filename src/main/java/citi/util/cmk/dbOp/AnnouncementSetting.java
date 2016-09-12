package citi.util.cmk.dbOp;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import citi.util.cmk.Stock.Announcement;
import citi.util.cmk.Stock.AnnouncementsInfo;

import citi.util.cmk.crawler.AnnouncementForStock;

public class AnnouncementSetting {
	private citi.util.cmk.dbOp.DBConnection dbConnection = null;
	private Connection connection = null;
	private Statement statement = null;

	public AnnouncementSetting() {
		try {
			dbConnection = new DBConnection();
			connection = dbConnection.getCon();
			statement = connection.createStatement();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public boolean announcement(String stockCode) {
		try {
			AnnouncementForStock announcementForStock = new AnnouncementForStock(stockCode);
			AnnouncementsInfo announcementsInfo = announcementForStock.getOneInfo();
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			System.out.println(stockCode);
			ResultSet resultSet = statement.executeQuery(
					"select ptime from announcement where code='" + stockCode + "' order by ptime desc limit 0,1");
			Date date1;
			if (resultSet.next()) {
				date1 = resultSet.getDate(1);
			} else {
				date1 = dateFormat.parse("1800-01-01");
			}
			List<String> dateList = announcementsInfo.getDateList();
			List<Announcement> newsList = announcementsInfo.getNewsList();
			for (int i = dateList.size() - 1; i >= 0; i--) {
				String dates = dateList.get(i);
				Date date3 = dateFormat.parse(dates);
				if (date3.after(date1)) {
					statement.execute("insert into announcement(ptime,title,link,code) values(" + "'" + dateList.get(i)
							+ "'," + "'" + newsList.get(i).getTitle() + "'," + "'" + newsList.get(i).getUrl() + "',"
							+ "'" + stockCode + "'" + ")");
				} else if (dateFormat.format(1).equals(dates)) {
					boolean b = false;
					ResultSet resultSet_temp = statement.executeQuery("select link from announcement where code='"
							+ stockCode + "'" + " and ptime='" + dates + "'");
					while (resultSet_temp.next()) {
						String link = resultSet_temp.getString(1);
						if (link.equals(newsList.get(i).getUrl())) {
							b = true;
						}
					}
					if (!b) {
						statement.execute("insert into announcement(ptime,title,link,code) values(" + "'"
								+ dateList.get(i) + "'," + "'" + newsList.get(i).getTitle() + "'," + "'"
								+ newsList.get(i).getUrl() + "'," + "'" + stockCode + "'" + ")");
					}
				}
			}

			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public static void main(String[] args) throws IOException {
		AnnouncementSetting announcementSetting = new AnnouncementSetting();
		if (announcementSetting.announcement("600066")) {
			System.out.println("finished");
		}
	}
}