package citi.util.cmk.crawler;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import citi.util.cmk.Stock.Company;
import citi.util.cmk.dbOp.DBConnection;

public class CompanyInfo {

	private List<String> stockList;
	private Map<String, String> urlMap;

	public CompanyInfo() {
		urlMapInit();
	}

	public Map<String, Company> getCompanyInfo() throws IOException {
		Map<String, Company> infoMap = new HashMap<String, Company>();
		for (String stockCode : stockList) {
			Company company = getInfoByCode(stockCode);
			infoMap.put(stockCode, company);
		}
		return infoMap;
	}

	private void stockListInit() {
		try {
			stockList = new ArrayList<String>();
			DBConnection dbConnection = new DBConnection();
			Statement tStatement = dbConnection.getCon().createStatement();
			ResultSet resultSet = tStatement.executeQuery("select distinct code from finance");
			while (resultSet.next()) {
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
		 * http://stockpage.10jqka.com.cn/600418/company/
		 */
		String urlPrefix = "http://stockpage.10jqka.com.cn/";
		String urlSuffix = "/company/";
		for (String stockCode : stockList) {
			String temp = urlPrefix + stockCode + urlSuffix;
			urlMap.put(stockCode, temp);
		}
	}

	private Company getInfoByCode(String stockCode) throws IOException {
		Company company = new Company();
		String url = urlMap.get(stockCode);
		Document doc = Jsoup.connect(url).timeout(10000).get();

		Elements nameElements = doc.select("div.m_header > h1 > a > strong");
		Element nameElement = nameElements.first();

		Elements infoElements = doc.select("table.m_table > tbody > tr > td > span");
		Element ccnameElement = infoElements.get(0);
		Element areaElement = infoElements.get(1);
		Element ecnameElement = infoElements.get(2);
		Element tradeElement = infoElements.get(3);
		Element onameElement = infoElements.get(4);
		Element linkElement = infoElements.get(5);
		Element mbusinessElement = infoElements.get(6);
		Element pnameElement = infoElements.get(7);

		company.setSname(nameElement.text());
		company.setCcname(ccnameElement.text());
		company.setArea(areaElement.text());
		company.setEcname(ecnameElement.text());
		company.setTrade(tradeElement.text());
		company.setOname(onameElement.text());
		company.setLink(linkElement.text());
		company.setMbusiness(mbusinessElement.text());
		company.setPname(pnameElement.text());

		return company;
	}

}
