package citi.util.cmk.Stock;


import java.util.ArrayList;
import java.util.List;

//单支股票的所有新闻
public class AnnouncementsInfo {
	
	private List<String> dateList=new ArrayList<String>();
	private List<Announcement> newsList=new ArrayList<Announcement>();
	
	public List<String> getDateList() {
		return dateList;
	}
	public void setDateList(List<String> dateList) {
		this.dateList = dateList;
	}
	public List<Announcement> getNewsList() {
		return newsList;
	}
	public void setNewsList(List<Announcement> newsList) {
		this.newsList = newsList;
	}
	
}