package citi.global.listener.updatelistener.finance;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.StringTokenizer;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.sun.org.apache.xerces.internal.impl.dv.xs.DayDV;

import citi.util.cmk.Stock.StockInfo;
import citi.util.cmk.dbOp.DBConnection;

public class UpdateDailyInfo{
	
/*
	final String sinaAPI="http://hq.sinajs.cn/list=";
	
	final String turnOverAPI = 
			"http://www.iwencai.com/stockpick/search?typed=1&preParams=&ts=1&f=1&qs=result_rewrite"
			+ "&selfsectsn=&querytype=&searchfilter=&tid=stockpick&w=%E6%8D%A2%E6%89%8B%E7%8E%87%EF%BC%8C";	
*/
	final String wyAPI = "http://quotes.money.163.com/service/chddata.html";
	
	//private String code;
	private double todayOpen,yesClose,todayClose,todayMax,todayMin,change,turnOver;
	private long amount;
	private String date,value;
	private boolean isWeekend;
	
	public UpdateDailyInfo(ArrayList<StockInfo> stockInfos) {
	}
/*
	public boolean getTurnOver(String code){
		String url = turnOverAPI + code;
		String turnOver = "0.00";
		Document doc;
		try {
			doc = Jsoup.connect(url).get();
			Elements elements=doc.select("td.up_td > div > a");
			if (elements.size()<=0){				
				return true;
			}
			Element ele=elements.first();
			turnOver = ele.text();
			return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}	
		finally {
			this.turnOver=Double.parseDouble(turnOver);
		}
		
	}

	public boolean getInfo(String code) {
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
*/	
	public boolean getInfo(String code) {
		try {
			Date date=new Date();
			Calendar calendar=Calendar.getInstance();
			calendar.setTime(date);
			int y=calendar.get(Calendar.YEAR);
			int m=calendar.get(Calendar.MONTH)+1;
			int d=calendar.get(Calendar.DAY_OF_MONTH);
			int wd=calendar.get(Calendar.DAY_OF_WEEK);
			if (wd==1||wd==7) {
				isWeekend=true;
				return true;
			}
			isWeekend=false;
			String now=y+(m<10?"0":"")+m+(d<10?"0":"")+d;
			/*
			URL url=new URL(wyAPI+"code="+(code.startsWith("0")?"1":"0")+code+"&start="+now+"&end="+now);
			HttpURLConnection conn=(HttpURLConnection) url.openConnection();
			BufferedReader br=new BufferedReader(new InputStreamReader(conn.getInputStream()));
			
			*/
			URL url = new URL(wyAPI);
	        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	        conn.setRequestMethod("POST");
	        conn.setDoInput(true);
	        conn.setDoOutput(true);
	        String params = "code="+(code.startsWith("0")?"1":"0")+code+"&start="+now+"&end="+now;
	        byte[] bypes = params.getBytes();
	        conn.getOutputStream().write(bypes);
	        BufferedReader br=new BufferedReader(new InputStreamReader(conn.getInputStream()));
	        
	        String st=br.readLine();
			 st=br.readLine();
			StringTokenizer stk=new StringTokenizer(st, ",");
			stk.nextToken(); //日期
			stk.nextToken(); //股票代码
			stk.nextToken(); //股票名称
			String todayCloseString=stk.nextToken(); //收盘价
			String todayMaxString=stk.nextToken(); //最高价
			String todayMinString=stk.nextToken(); //最低价
			String todayOpenString=stk.nextToken(); //开盘价
			String yesCloseString=stk.nextToken(); //前收盘
			stk.nextToken();//涨跌额
			String changeString=stk.nextToken();//涨跌幅
			String turnOverString=stk.nextToken();//换手率
			String amountString=stk.nextToken(); //成交量
			stk.nextToken();//成交金额
			stk.nextToken();//总市值
			value=stk.nextToken();//流通市值
			//for (int i=0;i<21;i++) stk.nextToken();
			//this.date=stk.nextToken(); //日期
			this.date=now;//日期
			todayOpen=Double.parseDouble(todayOpenString);
			yesClose=Double.parseDouble(yesCloseString);
			todayClose=Double.parseDouble(todayCloseString);
			todayMax=Double.parseDouble(todayMaxString);
			todayMin=Double.parseDouble(todayMinString);
			if (changeString.equals("None")){
				change=0;
			}
			else{
				change=Double.parseDouble(changeString);
			}
			if (turnOverString.equals("None")){
				turnOver=0;
			}
			else{
				turnOver=Double.parseDouble(turnOverString);
			}
			amount=Long.parseLong(amountString);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return true;
	}
	public boolean infoDAO(String code) {
		if (isWeekend) return true;
		try{
			
			DBConnection dbConnection=new DBConnection();
			Connection connection = dbConnection.getCon();
			Statement statement = connection.createStatement();
			String sqlSelect="select date from finance where code='"+code+"' and date='"+date+"';";
			ResultSet resultSet=statement.executeQuery(sqlSelect);
			if (resultSet.next()){
				System.out.println("Info: "+date+" "+code+":already updated");	
				//String sqlInsert="insert into finance values('"+date+"',"+todayOpen+","+todayMax+","+todayMin+","+todayClose+","+change+","+amount+",'"+code+"',null,null,"+turnOver+","+value+");";				
				//System.out.println(sqlInsert);
			}
			else{
				
				//double change=(todayClose-yesClose)/yesClose*100;
				
				String sqlInsert="insert into finance values('"+date+"',"+todayOpen+","+todayMax+","+todayMin+","+todayClose+","+change+","+amount+",'"+code+"',null,null,"+turnOver+","+value+");";
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
	
}
