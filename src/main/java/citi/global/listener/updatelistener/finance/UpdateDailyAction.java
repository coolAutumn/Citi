package citi.global.listener.updatelistener.finance;

import java.util.ArrayList;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

import citi.util.cmk.Stock.Announcement;
import citi.util.cmk.Stock.StockInfo;
import citi.util.cmk.dbOp.AnnouncementSetting;
import citi.util.cmk.dbOp.DRSetting;
import citi.util.cmk.dbOp.ParaSetting;
import citi.util.cmk.dbOp.ReplaySetting;
import citi.util.cmk.dbOp.StockNewsSetting;

public class UpdateDailyAction implements Runnable{
	
	private ArrayList<StockInfo> stockInfos;
	
	public UpdateDailyAction(ArrayList<StockInfo> stockInfos) {
		// TODO Auto-generated constructor stub
		this.stockInfos=stockInfos;
	}

	public void run() {
		// TODO Auto-generated method stub
		int count=10;
		System.out.println("----Update start----");
		System.out.println("--Update stock info start--");
		UpdateDailyInfo updateDailyInfo=new UpdateDailyInfo(stockInfos);
		for (StockInfo stockInfo : stockInfos) {
			String code=stockInfo.getCode();
			while (!updateDailyInfo.getInfo(code)){
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}			
			//while (!updateDailyInfo.getTurnOver(code)){}			
			while (!updateDailyInfo.infoDAO(code)){
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
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
		System.out.println("--Update prediction start--");
		try {
			ParaSetting paraSetting=new ParaSetting();
			for (StockInfo stockInfo : stockInfos) {
				paraSetting.paraSetting(stockInfo.getCode());
			}
		} catch (MySQLIntegrityConstraintViolationException e) {
			// TODO Auto-generated catch block
			System.out.println("Already updated");
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		System.out.println("--Update prediction finished--");
		System.out.println("----All update finished----");
	}

}
