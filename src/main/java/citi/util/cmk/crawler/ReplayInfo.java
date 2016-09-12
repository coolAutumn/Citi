package citi.util.cmk.crawler;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class ReplayInfo {
	private String prefix = "http://search.10jqka.com.cn/thsft/iFindService/StockCalendar/index/detail?code=";
	private String suffix = "&date=" + getToday();
	private String code;
	
	public ReplayInfo(String code) {
		this.code=code;
	}
	
	public Map<String, String> getOneCloseAndOpen() throws IOException{
		String url=prefix + code + suffix;
		return getByURL(url);
	}

	// 通过股票代码获取停盘和复盘日期
	private Map<String, String> getByURL(String stockURL) throws IOException {
		Map<String, String> dayMap = new HashMap<String, String>();
		Document doc = Jsoup.connect(stockURL).get();
		Elements elements = doc.select("tbody > tr > td[style=min-width:115px]");
		String close = null;
		for (int i = 0; i < elements.size(); i++) {
			Element ele = elements.get(i);
			if (ele.text() != null && ele.text().trim().length() > 0) {
				close = ele.text();
			}
			i++;
			if (ele.text() != null && ele.text().trim().length() > 0) {
				ele = elements.get(i);
				if (close.length() > 0 && ele.text().length() > 0 && close.length() > 0) {

					dayMap.put(close.substring(0, 10), ele.text().substring(0, 10));
				}
			}
		}
		return dayMap;
	}

	private String getToday() {
		Date date = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH);
		int day = calendar.get(Calendar.DAY_OF_MONTH);
		month = month + 1;
		return (year + "-" + month + "-" + day);
	}
	

}
