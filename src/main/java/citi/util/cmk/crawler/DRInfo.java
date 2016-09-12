package citi.util.cmk.crawler;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class DRInfo {
	private String prefix = "http://q.stock.sohu.com/cn/";
	private String suffix = "/fhsp.shtml";
	private String code;
	
	public DRInfo(String code) {
		this.code=code;
		// TODO Auto-generated constructor stub
	}
	public  List<String> getOneDayList() throws IOException{
		String url=prefix + code + suffix;
		return getDayList(url);
	}
	private List<String> getDayList(String stockURL) throws IOException{
		List<String> dayList=new ArrayList<String>();
		Document doc=Jsoup.connect(stockURL).get();
		Elements elements=doc.select("td.bgGray3");
		Element firstElement=elements.first();
		if(!"预案公告日".equals(firstElement.text())){
			Element secondElement=elements.get(1);
			dayList.add(secondElement.text());
		}
		int count=0;
		for(Element element:elements){
			count++;
			if(count>2){
				if(!element.text().contains("除权")){
					if(element.text()!=null && element.text().trim().length()>0){
						dayList.add(element.text());
					}
				}
			}
		}
		return dayList;
	}
}
