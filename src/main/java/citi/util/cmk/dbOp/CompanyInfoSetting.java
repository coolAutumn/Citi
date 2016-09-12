package citi.util.cmk.dbOp;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Map;

import citi.util.cmk.Stock.Company;
import citi.util.cmk.crawler.CompanyInfo;
import citi.util.cmk.crawler.MarketValue;
import citi.util.cmk.dbOp.*;

public class CompanyInfoSetting {
	private DBConnection dbConnection = null;
	private Connection connection = null;
	private Statement statement = null;

	public CompanyInfoSetting() {
		try {
			dbConnection = new DBConnection();
			connection = dbConnection.getCon();
			statement = connection.createStatement();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public boolean companySettingAll() {
		try {
			ResultSet resultSet = statement.executeQuery("select distinct code from company");
			ArrayList<String> arrayList = new ArrayList<String>();
			while (resultSet.next()) {
				arrayList.add(resultSet.getString(1));
			}
			CompanyInfo companyInfo = new CompanyInfo();
			Map<String, Company> map=companyInfo.getCompanyInfo();
			for (String stockCode : map.keySet()) {
				if (!arrayList.contains(stockCode)) {
					System.out.println(stockCode);
					Company company = map.get(stockCode);
					String sql = "insert into company values('" + stockCode + "'," + "'" + company.getSname() + "',"
							+ "'" + company.getCcname() + "'," + "'" + company.getEcname() + "'," + "'"
							+ company.getArea() + "'," + "'" + company.getOname() + "'," + "'" + company.getTrade()
							+ "'," + "'" + company.getMbusiness() + "'," + "'" + company.getPname() + "'," + "'"
							+ company.getLink() + "',null,null" + ");";
					System.out.println(sql);
					statement.execute(sql);
				}
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public String setValue() {
		try {
			String aString = "";
			MarketValue marketValue = new MarketValue();
			Map<String, String> valueMap = marketValue.getValues();
			for (String stockCode : valueMap.keySet()) {
				System.out.println(stockCode + ":" + valueMap.get(stockCode));
				String value=valueMap.get(stockCode);
				statement.execute("update company set value ='"+value.substring(0, value.length()-1)+"' where code='"+stockCode+"'");
			}
			return aString;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}

	public static void main(String[] args) throws IOException {
		CompanyInfoSetting aCompanyInfoSetting=new CompanyInfoSetting();
		aCompanyInfoSetting.companySettingAll();
		aCompanyInfoSetting.setValue();
	}

}
