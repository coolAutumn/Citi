package citi.global.listener.updatelistener.finance;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.StringTokenizer;

import citi.util.cmk.Stock.StockInfo;
import citi.util.cmk.web.CodeList;

public class UpdateDailyTimer implements Runnable{
	
	final String api="http://hq.sinajs.cn/list=sh600066";
	final int getTime=15*3600+30*60;
	//final int getTime=0;
	
	//final int closeTime=15*3600;
	/*
	public int getNow() {
		int time;
		try {
			URL url = new URL(api);
			HttpURLConnection conn=(HttpURLConnection) url.openConnection();
			BufferedReader br=new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String st=br.readLine();

			StringTokenizer stk=new StringTokenizer(st, ",");
			for (int i=0;i<31;i++) stk.nextToken();
			String timeS=stk.nextToken();
			StringTokenizer stkTime=new StringTokenizer(timeS, ":");
			String hS=stkTime.nextToken();
			String mS=stkTime.nextToken();
			String sS=stkTime.nextToken();
			int h=Integer.parseInt(hS);
			int m=Integer.parseInt(mS);
			int s=Integer.parseInt(sS);			
			time=h*3600+m*60+s;
			System.out.println("API time-"+h+":"+m+":"+s);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			time=-1;
		}	
		return time;		
	}
	*/
	public void updateDaily() {
		CodeList codeList=new CodeList();
		ArrayList<StockInfo> stockInfos=codeList.init();
		System.out.println("Timer: got code list");
		
		Thread updateDailyAction=new Thread(new UpdateDailyAction(stockInfos));
		updateDailyAction.start();

	}	
	
	public static void main(String[] args) {
		Thread thread=new Thread(new UpdateDailyTimer());
		thread.start();
	}

	public void run() {
		try{
			//int now;
			//while ((now=getNow())<0){}
			Date date=new Date();
			Calendar calendar=Calendar.getInstance();
			calendar.setTime(date);
			//int day=calendar.get(Calendar.DAY_OF_WEEK);
			int h=calendar.get(Calendar.HOUR_OF_DAY);
			int m=calendar.get(Calendar.MINUTE);
			int s=calendar.get(Calendar.SECOND);
			System.out.println("server time-"+h+":"+m+":"+s);
			int serverNow=h*3600+m*60+s;
			//now=serverNow=14*3600+59*60+50;
			//int getTime=15*3600;
			//if (now>=closeTime){
			if (serverNow>=getTime){
				updateDaily();					
				int sleepMS=(24*3600-serverNow+getTime)*1000;
				System.out.println("Timer: sleep for "+sleepMS+" milliseconds");
				Thread.sleep(sleepMS);
			}
			else {
				int sleepMS=(getTime-serverNow)*1000;
				System.out.println("Timer: sleep for "+sleepMS+" milliseconds");
				Thread.sleep(sleepMS);
			}
			while (true){
				updateDaily();
				int sleepMS=24*3600*1000;
				System.out.println("Timer: sleep for "+sleepMS+" milliseconds");
				Thread.sleep(sleepMS);
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
}
