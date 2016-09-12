package citi.util.cmk.dbOp;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import citi.util.cmk.Stock.StockNew;
import citi.util.cmk.crawler.StockNewsInfo;

public class StockNewsSetting {
	private DBConnection dbConnection = null;
	private Connection connection = null;
	private Statement statement = null;

	public StockNewsSetting() {
		try {
			dbConnection = new DBConnection();
			connection = dbConnection.getCon();
			statement = connection.createStatement();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public boolean news(String stockCode) {
		try {
			
			StockNewsInfo stockNewsInfo = new StockNewsInfo(stockCode);
			List<StockNew> newsList = stockNewsInfo.getOneNews();
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

			for (int i = newsList.size() - 1; i >= 0; i--) {
				String dates = newsList.get(i).getDate();
				Date date3 = dateFormat.parse(dates);
				if (date3.after(date1)) {
					statement.execute("insert into news(ptime,title,link,code) values(" + "'" + dates + "'," + "'"
							+ newsList.get(i).getTitle() + "'," + "'" + newsList.get(i).getUrl() + "'," + "'"
							+ stockCode + "'" + ")");
					System.out.println(dates);
				} else if (dateFormat.format(1).equals(dates)) {
					boolean b = false;
					ResultSet resultSet_temp = statement.executeQuery(
							"select link from news where code='" + stockCode + "'" + " and ptime='" + dates + "'");
					while (resultSet_temp.next()) {
						String link = resultSet_temp.getString(1);
						if (link.equals(newsList.get(i).getUrl())) {
							b = true;
						}
					}
					if (!b) {
						statement.execute("insert into news(ptime,title,link,code) values(" + "'" + dates + "'," + "'"
								+ newsList.get(i).getTitle() + "'," + "'" + newsList.get(i).getUrl() + "'," + "'"
								+ stockCode + "'" + ")");
						System.out.println(dates);
					}

				}
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public static void main(String[] args) throws Exception {
		StockNewsSetting setting = new StockNewsSetting();
		if (setting.news("600066")) {
			System.out.println("done");
		}
	}

}
