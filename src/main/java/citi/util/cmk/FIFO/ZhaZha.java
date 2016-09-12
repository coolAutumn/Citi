package citi.util.cmk.FIFO;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import citi.util.cmk.dbOp.AnnouncementSetting;
import citi.util.cmk.dbOp.DBConnection;
import citi.util.cmk.dbOp.DRSetting;
import citi.util.cmk.dbOp.ReplaySetting;
import citi.util.cmk.dbOp.StockNewsSetting;

public class ZhaZha {
	public static void refresh() {
		try {
			AnnouncementSetting announcementSetting=new AnnouncementSetting();
			ReplaySetting replaySetting=new ReplaySetting();
			DRSetting drSetting=new DRSetting();
			StockNewsSetting stockNewsSetting =new StockNewsSetting();
			Statement statement = new DBConnection().getCon().createStatement();
			ArrayList<String> code = new ArrayList<String>();
			ResultSet codeList = statement.executeQuery("select code from company");
			while (codeList.next()) {
				code.add(codeList.getString(1));
			}
			for(String s:code){
				announcementSetting.announcement(s);
				//replaySetting.replay(s);
				//drSetting.DR(s);
				stockNewsSetting.news(s);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("done");
	}
	public static void main(String[] args) {
		refresh();
	}
}
