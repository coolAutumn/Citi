package citi.global.listener.updatelistener.finance;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import Stock.News;
import Stock.NewsInfo;
import Stock.StockInfo;
import dbOp.DBConnection;
import web.CodeList;

public class UpdateDailyNews implements Runnable{

	private DBConnection dbConnection = new DBConnection();
	
	final private String prefix="http://vip.stock.finance.sina.com.cn/corp/go.php/vCB_AllBulletin/stockid/";
	final private String suffix=".phtml";	
	
	//private String code;
	private ArrayList<StockInfo> stockInfos;
	private NewsInfo newsInfo;
	
	public UpdateDailyNews(ArrayList<StockInfo> stockInfos) {
		// TODO Auto-generated constructor stub
		this.stockInfos=stockInfos;
	}

	public NewsInfo getNews(String code){				
		String url=prefix+code+suffix;							
		return getInfo(url);
	}
	
	private NewsInfo getInfo(String url){
		Document doc;
		try {
			doc = Jsoup.connect(url).timeout(10000).get();
			Elements elements=doc.select("div.datelist > ul");
			String []infos=elements.first().text().split(" ");
			List<String> dateList=new ArrayList<String>();
			for(String str:infos){
				dateList.add(str.substring(0,10));
			}
			Elements elements2=doc.select("div.datelist > ul >a");
			List<News> newsList=new ArrayList<News>();
			for(Element ele:elements2){
				News news=new News();
				news.setTitle(ele.text());
				news.setUrl("http://vip.stock.finance.sina.com.cn/"+ele.attr("href"));
				newsList.add(news);
			}
			NewsInfo newsInfo=new NewsInfo();
			newsInfo.setDateList(dateList);
			newsInfo.setNewsList(newsList);
			return newsInfo;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}		
	}

	public boolean news(String code) {
		try {
			Connection connection = dbConnection.getCon();
			Statement statement = connection.createStatement();
			NewsInfo newsInfo = getNews(code);
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			
				ResultSet resultSet = statement.executeQuery(
						"select ptime from news where code='" + code + "' order by ptime desc limit 0,1");
				resultSet.next();
				Date date1 = resultSet.getDate(1);				
				List<String> dateList = newsInfo.getDateList();
				List<News> newsList = newsInfo.getNewsList();
				for (int i = 0; i < dateList.size(); i++) {
					String dates = dateList.get(i);
					Date date3 = dateFormat.parse(dates);
					String sql="insert into news(ptime,title,link,code) values(" + "'" + dateList.get(i)
						+ "'," + "'" + newsList.get(i).getTitle() + "'," + "'" + newsList.get(i).getUrl() + "',"
						+ "'" + code + "'" + ")";
					if (date3.after(date1)) {
						statement.execute(sql);
					}
					else if(dateFormat.format(1).equals(dateFormat.format(date3))){
						ResultSet resultSet_temp=statement.executeQuery("select link from news where code='"+code+"'"
								+ " and ptime='"+dateFormat.format(date3)+"'");
						while(resultSet_temp.next()){
							String link=resultSet_temp.getString(1);
							if(!link.equals(newsList.get(i).getUrl())){
								statement.execute(sql);
							}
						}
					}
				
			}
			System.out.println("News: "+code+": updated successfully");
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return false;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub		
		
		for (StockInfo stockInfo : stockInfos) {
			String code=stockInfo.getCode();
			while ((newsInfo=getNews(code))==null){}									
			news(code);
		}
		System.out.println("News:update finished");
	}
	
	public static void main(String[] args) {
		CodeList codeList=new CodeList();
		ArrayList<StockInfo> stockInfos=codeList.init();
		Thread thread=new Thread(new UpdateDailyNews(stockInfos));
		thread.start();
	}

}