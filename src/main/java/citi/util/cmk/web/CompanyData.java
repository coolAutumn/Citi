package citi.util.cmk.web;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import citi.util.cmk.Stock.Company;
import citi.util.cmk.dbOp.DBConnection;

public class CompanyData {
	private String code;
	private DBConnection dbConnection = new DBConnection();
	private Company company = null;

	public CompanyData(String code) {
		this.code = code;
	}

	public Company init() {
		try {
			Connection connection = dbConnection.getCon();
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery("select * from company where code='" + code + "'");
			if (resultSet.next()) {
				String sname = resultSet.getString(2); // 股票名称
				String ccname = resultSet.getString(3); // 中文名
				String ecname = resultSet.getString(4); // 英文名
				String area = resultSet.getString(5); // 所属地域
				String oname = resultSet.getString(6); // 曾用名
				String trade = resultSet.getString(7); // 所属行业
				String mbusiness = resultSet.getString(8); // 主营行业
				String pname = resultSet.getString(9); // 产品名称
				String link = resultSet.getString(10); // 公司网址
				String value=resultSet.getString(11);
				company = new Company();
				company.setSname(sname);
				company.setCcname(ccname);
				company.setEcname(ecname);
				company.setArea(area);
				company.setOname(oname);
				company.setTrade(trade);
				company.setMbusiness(mbusiness);
				company.setPname(pname);
				company.setLink(link);
				company.setValue(value);
				return company;
			} else {
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public String toJson() throws Exception {
		init();
		String json = "{\n";
		json += "\"code\":\"" + code + "\",\n";
		json += "\"stockname\":\"" + company.getSname() + "\",\n";
		json += "\"chinesename\":\"" + company.getCcname() + "\",\n";
		json += "\"englishname\":\"" + company.getEcname() + "\",\n";
		json += "\"area\":\"" + company.getArea() + "\",\n";
		json += "\"oldname\":\"" + company.getOname() + "\",\n";
		json += "\"trade\":\"" + company.getTrade() + "\",\n";
		json += "\"mainbussiness\":\"" + company.getMbusiness() + "\",\n";
		json += "\"productname\":\"" + company.getPname() + "\",\n";
		json += "\"link\":\"" + company.getLink() + "\"\n";
		json+="\"marketvalue\":\"" + company.getValue() + "\"\n";
		json += "}";
		return json;
	}

	public static void main(String[] args) throws Exception {
		CompanyData companyData = new CompanyData("600066");
		System.out.println(companyData.toJson());
	}
}
