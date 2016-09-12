package citi.util.cmk.Stock;

import java.util.Date;

public class Announcement {
	private Date ptime;
	private String title;
	private String url;
	
	public void setPtime(Date date){
		this.ptime=date;
	}
	public Date getPtime(){
		return ptime;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
}
