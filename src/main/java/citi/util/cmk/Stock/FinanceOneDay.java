package citi.util.cmk.Stock;

public class FinanceOneDay {
	private String date;
	private double open;
	private double close;
	private double high;
	private double low;

	public FinanceOneDay(String date, double open, double close, double low, double high) {
		this.setDate(date);
		this.setOpen(open);
		this.setClose(close);
		this.setHigh(high);
		this.setLow(low);
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public double getOpen() {
		return open;
	}

	public void setOpen(double open) {
		this.open = open;
	}

	public double getClose() {
		return close;
	}

	public void setClose(double close) {
		this.close = close;
	}

	public double getHigh() {
		return high;
	}

	public void setHigh(double high) {
		this.high = high;
	}

	public double getLow() {
		return low;
	}

	public void setLow(double low) {
		this.low = low;
	}

}
