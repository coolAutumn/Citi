package citi.global.listener.updatelistener.finance;

import java.util.ArrayList;

import Stock.Announcement;
import Stock.StockInfo;
import dbOp.AnnouncementSetting;
import dbOp.DRSetting;
import dbOp.ReplaySetting;
import dbOp.StockNewsSetting;

public class UpdateDailyAction implements Runnable{
	
	private ArrayList<StockInfo> stockInfos;
	
	public UpdateDailyAction(ArrayList<StockInfo> stockInfos) {
		// TODO Auto-generated constructor stub
		this.stockInfos=stockInfos;
	}

	public void run() {
		// TODO Auto-generated method stub
		System.out.println("----Update start----");
		System.out.println("--Update stock info start--");
		UpdateDailyInfo updateDailyInfo=new UpdateDailyInfo(stockInfos);
		updateDailyInfo.run();
		System.out.println("--Update stock info finished--");
		System.out.println("--Update annoucement start--");
		AnnouncementSetting announcementSetting=new AnnouncementSetting();
		for (StockInfo stockInfo : stockInfos) {
			announcementSetting.announcement(stockInfo.getCode());
		}
		System.out.println("--Update annoucement finished--");
		System.out.println("--Update DR start--");
		DRSetting drSetting=new DRSetting();
		for (StockInfo stockInfo : stockInfos) {
			drSetting.DR(stockInfo.getCode());
		}
		System.out.println("--Update DR finished--");
		System.out.println("--Update replay start--");
		ReplaySetting replaySetting=new ReplaySetting();
		for (StockInfo stockInfo : stockInfos) {
			replaySetting.replay(stockInfo.getCode());
		}
		System.out.println("--Update replay finished--");
		System.out.println("--Update news start--");
		StockNewsSetting stockNewsSetting=new StockNewsSetting();
		for (StockInfo stockInfo : stockInfos) {
			stockNewsSetting.news(stockInfo.getCode());
		}
		System.out.println("--Update news finished--");
		System.out.println("----All update finished----");
	}

}
