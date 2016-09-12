package citi.util.cmk.crawler;

import java.io.IOException;
import java.net.MalformedURLException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.LogFactory;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.DomNodeList;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

import citi.util.cmk.dbOp.DBConnection;

public class MarketValue {

	private List<String> stockList;
	private Map<String, String> urlMap; // 存放每支股票行情对应的URL

	public MarketValue() {
		urlMapInit();
	}

	public Map<String, String> getValues() throws FailingHttpStatusCodeException, MalformedURLException, IOException {
		Map<String, String> valueMap = new HashMap<String, String>(); // key为股票代码，value为总市值
		for (String stockCode : stockList) {
			valueMap.put(stockCode, getValue(stockCode));
		}
		return valueMap;
	}

	private void stockListInit() {
		stockList = new ArrayList<String>();
		DBConnection dbConnection = new DBConnection();
		try {
			Connection connection = dbConnection.getCon();
			Statement statement = connection.createStatement();
			ResultSet resultSet=statement.executeQuery("select code from company");
			while(resultSet.next()){
			stockList.add(resultSet.getString(1));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void urlMapInit() {
		stockListInit();
		urlMap = new HashMap<String, String>();
		/*
		 * http://finance.sina.com.cn/realstock/company/sz000625/nc.shtml
		 */
		String prefix = "http://finance.sina.com.cn/realstock/company/";
		String suffix = "/nc.shtml";
		for (String stockCode : stockList) {
			String url = null;
			if (stockCode.charAt(0) == '6') {
				url = prefix + "sh" + stockCode + suffix;
			} else {
				url = prefix + "sz" + stockCode + suffix;
			}
			urlMap.put(stockCode, url);
		}
	}

	// 根据股票代码获取股票对应的总市值
	private String getValue(String stockCode)
			throws FailingHttpStatusCodeException, MalformedURLException, IOException {
		System.out.println(stockCode);
		LogFactory.getFactory().setAttribute("org.apache.commons.logging.Log",
				"org.apache.commons.logging.impl.NoOpLog");
		WebClient wc = new WebClient(BrowserVersion.CHROME);
		wc.getOptions().setUseInsecureSSL(true);
		wc.getOptions().setJavaScriptEnabled(true); // 启用JS解释器，默认为true
		wc.getOptions().setCssEnabled(false); // 禁用css支持
		wc.getOptions().setThrowExceptionOnScriptError(false); // js运行错误时，是否抛出异常
		wc.getOptions().setTimeout(10000); // 设置连接超时时间 ，这里是10S。如果为0，则无限期等待
		wc.getOptions().setDoNotTrackEnabled(false);
		HtmlPage page = wc.getPage(urlMap.get(stockCode));

		DomNodeList<DomElement> links = page.getElementsByTagName("td");

		DomElement valueDocument = links.get(37); // 该处获取了总市值，若要使用流通市值，把参数从34改为37
		wc.close();
		return valueDocument.asText();
	}
}
