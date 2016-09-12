package citi.util.cmk.FIFO;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Statement;
import java.util.StringTokenizer;

import citi.util.cmk.dbOp.DBConnection;

public class ImportFinanceData {
	public static void import1() throws Exception {
		DBConnection dbConnection = new DBConnection();
		Connection connection = dbConnection.getCon();
		Statement statement = connection.createStatement();
		File dir = new File("./pakage");
		File[] files = dir.listFiles();
		String file = null;
		for (File f : files) {
			if (!f.getName().equalsIgnoreCase(".DS_Store")) {
				file = "./pakage/" + f.getName();
				System.out.println(file);
				BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
				String line = null;
				while ((line = bufferedReader.readLine()) != null) {
					int count = 0;
					StringTokenizer sqlTokenizer = new StringTokenizer(line, ",", true);
					String sql = "";
					while (sqlTokenizer.hasMoreElements()) {
						String token = (String) sqlTokenizer.nextElement();
						if (!token.equals(",")) {
							if (count == 5) {
								BigDecimal bigDecimal=new BigDecimal(token);
								token = String.valueOf(bigDecimal.movePointRight(2));
							}
							sql += "'" + token + "'";
						} else {
							count++;
							sql += token;
						}
					}
					sql = sql.replaceAll(",,", ",null,");
					sql = sql.replaceAll(",,", ",null,");
					sql = "insert into finance values(" + sql + ");";
					sql = sql.replace("null,null,null", "'" + f.getName().substring(0, 6) + "'" + ",null,null");
					System.out.println(sql);
					statement.execute(sql);
				}
				bufferedReader.close();
				f.delete();
			}
		}
		System.out.println("finance finished");
	}
	public static void import2() throws Exception {
		DBConnection dbConnection = new DBConnection();
		Connection connection = dbConnection.getCon();
		Statement statement = connection.createStatement();
		File dir = new File("./pakage");
		File[] files = dir.listFiles();
		String file = null;
		for (File f : files) {
			if (!f.getName().equalsIgnoreCase(".DS_Store")) {
				file = "./pakage/" + f.getName();
				System.out.println(file);
				BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
				String line = null;
				bufferedReader.readLine();
				while ((line = bufferedReader.readLine()) != null) {
					String[] temp = line.split(",");
					String sql = "update finance set value='" + temp[14] + "' where code='" + temp[1].replace("'", "") + "' and date='"
							+ temp[0] + "'; ";
					System.out.println(sql);
					statement.execute(sql);
				}
				bufferedReader.close();
				f.delete();
			}
		}
		System.out.println("finance finished");
	}
	public static void main(String[] args) {
		try {
			import2(); 
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
