package citi.util.cmk.crawler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import citi.util.cmk.Stock.Announcement;
import citi.util.cmk.Stock.AnnouncementsInfo;

public class AnnouncementForStock {
	private String prefix = "http://vip.stock.finance.sina.com.cn/corp/go.php/vCB_AllBulletin/stockid/";
	private String suffix = ".phtml";
	private String code;

	public AnnouncementForStock(String code) {
		this.code=code;
	}
	
	public AnnouncementsInfo getOneInfo() throws IOException{
		String url=prefix + code + suffix;
		return getInfo(url);
	}

	private AnnouncementsInfo getInfo(String url) throws IOException {
		Document doc = Jsoup.connect(url).timeout(10000).get();
		Elements elements = doc.select("div.datelist > ul");
		String[] infos = elements.first().text().split(" ");
		List<String> dateList = new ArrayList<String>();
		String dayRegex = "(\\d){4}\\-(\\d){2}\\-(\\d){2}";
		for (String str : infos) {
			if (str.length() > 10) {
				if (str.substring(0, 10).matches(dayRegex)) {
					dateList.add(str.substring(0, 10));
				}
			}
		}
		Elements elements2 = doc.select("div.datelist > ul >a");
		List<Announcement> newsList = new ArrayList<Announcement>();
		for (Element ele : elements2) {
			Announcement announcement = new Announcement();
			announcement.setTitle(ele.text());
			announcement.setUrl("http://vip.stock.finance.sina.com.cn/" + ele.attr("href"));
			newsList.add(announcement);
		}
		AnnouncementsInfo announcementsInfo = new AnnouncementsInfo();
		announcementsInfo.setDateList(dateList);
		announcementsInfo.setNewsList(newsList);
		return announcementsInfo;
	}
}
