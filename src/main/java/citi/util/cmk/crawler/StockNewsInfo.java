package citi.util.cmk.crawler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import citi.util.cmk.Stock.StockNew;


public class StockNewsInfo {
	private String prefix="http://q.stock.sohu.com/news/cn/";
	private String suffix="/rt_news.shtml";
	private String code;
	
	public StockNewsInfo(String code){
		this.code=code;
	}
	
	public List<StockNew> getOneNews() throws IOException{
		String url = prefix+code.substring(code.length()-3, code.length())+"/"+code+suffix;
		return getNewsByURL(url);
	}
	
	private List<StockNew> getNewsByURL(String url) throws IOException{
		List<StockNew> stockNewsList = new ArrayList<StockNew>();
		Document document = Jsoup.connect(url).timeout(10000).get();
		Elements urlElements = document.select("div.newslist > ul > li > a");
		for(Element urlElement:urlElements){
			StockNew stockNew = new StockNew();
			stockNew.setTitle(urlElement.text());
			stockNew.setUrl("http://q.stock.sohu.com"+urlElement.attr("href"));
			stockNewsList.add(stockNew);
		}
		Elements timeElements = document.select("div.newslist > ul > li > span");
		for(int i=0;i<timeElements.size();i++){
			Element timeElement = timeElements.get(i);
			StockNew stockNew = stockNewsList.get(i);
			String time = timeElement.text().substring(1,timeElement.text().length()-1);
			stockNew.setDate(time);
		}
		return stockNewsList;
	}
	
}
