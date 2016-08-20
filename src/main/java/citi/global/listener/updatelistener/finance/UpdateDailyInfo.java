package citi.global.listener.updatelistener.finance;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.StringTokenizer;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import Stock.StockInfo;
import dbOp.DBConnection;

public class UpdateDailyInfo{
	
	final String sinaAPI="http://hq.sinajs.cn/list=";
	final String turnOverAPI = 
			"http://www.iwencai.com/stockpick/search?typed=1&preParams=&ts=1&f=1&qs=result_rewrite"
			+ "&selfsectsn=&querytype=&searchfilter=&tid=stockpick&w=%E6%8D%A2%E6%89%8B%E7%8E%87%EF%BC%8C";		
	
	private ArrayList<StockInfo> stockInfos;
	//private String code;
	private double todayOpen,yesClose,todayClose,todayMax,todayMin,turnOver;
	private int amount;
	private String date;
	
	public UpdateDailyInfo(ArrayList<StockInfo> stockInfos) {
		// TODO Auto-generated constructor stub
		this.stockInfos=stockInfos;
	}

	private String getTurnOver(String code){
		String url = turnOverAPI + code;
		Document doc;
		try {
			doc = Jsoup.connect(url).get();
			Elements elements=doc.select("td.up_td > div > a");
			Element ele=elements.first();
			return ele.text();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}		
	}
	
	private boolean getInfo(String code) {
		try {
			URL url=new URL(sinaAPI+(code.startsWith("0")?"sz":"sh")+code);
			HttpURLConnection conn=(HttpURLConnection) url.openConnection();
			BufferedReader br=new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String st=br.readLine();
			StringTokenizer stk=new StringTokenizer(st, ",");
			stk.nextToken(); //股票名称
			String todayOpenString=stk.nextToken(); //今日开盘
			String yesCloseString=stk.nextToken(); //昨日收盘
			String todayCloseString=stk.nextToken(); //当前价格
			String todayMaxString=stk.nextToken(); //今日最高
			String todayMinString=stk.nextToken(); //今日最低
			stk.nextToken();stk.nextToken();
			String amountString=stk.nextToken(); //成交股数
			for (int i=0;i<21;i++) stk.nextToken();
			date=stk.nextToken(); //日期
			todayOpen=Double.parseDouble(todayOpenString);
			yesClose=Double.parseDouble(yesCloseString);
			todayClose=Double.parseDouble(todayCloseString);
			todayMax=Double.parseDouble(todayMaxString);
			todayMin=Double.parseDouble(todayMinString);
			amount=Integer.parseInt(amountString);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	private boolean infoDAO(String code) {
		try{
			
			DBConnection dbConnection=new DBConnection();
			Connection connection = dbConnection.getCon();
			Statement statement = connection.createStatement();
			String sqlSelect="select date from finance where code='"+code+"' and date='"+date+"';";
			ResultSet resultSet=statement.executeQuery(sqlSelect);
			if (resultSet.next()){
				System.out.println("Info: "+date+" "+code+":already updated");
			}
			else{
				
				double change=(todayClose-yesClose)/yesClose*100;
				
				String sqlInsert="insert into finance values('"+date+"',"+todayOpen+","+todayMax+","+todayMin+","+todayClose+","+change+","+amount+",'"+code+"',null,null,"+turnOver+");";
				//System.out.println(sqlInsert);
				statement.executeUpdate(sqlInsert);
				
				System.out.println("Info: "+date+" "+code+":updated successfully");
			}
		}
		catch(Exception e){
			e.printStackTrace();
			return false;
		}	
		return true;
	}

	public void run() {			
		for (StockInfo stockInfo : stockInfos) {
			String code=stockInfo.getCode();
			while (!getInfo(code)){}			
			String turnOverString;
			while ((turnOverString=getTurnOver(code))==null){}
			turnOver=Double.parseDouble(turnOverString);
			while (!infoDAO(code)){}
						
		}
		System.out.println("Info: update finished");
	}
	
}
