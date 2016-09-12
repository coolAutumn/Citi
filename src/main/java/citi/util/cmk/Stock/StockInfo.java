package citi.util.cmk.Stock;

public class StockInfo {
	private String code;
	private String sname;
	private String trade;

	public StockInfo(String code, String sname,String trade) {
		this.code=code;
		this.sname = sname;
		this.trade=trade;
	}

	public String getCode() {
		return code;
	}

	public String getSname() {
		return sname;
	}
	public String getTrade() {
		return trade;
	}
	
}
