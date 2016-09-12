package citi.util.cmk.FIFO;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import citi.util.cmk.dbOp.DBConnection;
import citi.util.cmk.web.Frequency;

import java.sql.Statement;


public class Temp {
	

	public void MyWrite(String data, String filename) {
		try {
			File file = new File(filename);
			// if file doesnt exists, then create it
			if (!file.exists()) {
				file.createNewFile();
			}
			// true = append file
			FileWriter fileWritter = new FileWriter(file, false);
			BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
			bufferWritter.write(data);
			bufferWritter.close();
			System.out.println("Done");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void MyReader(String fileName) {
		File file = new File(fileName);
		BufferedReader reader = null;
		try {
			Statement statement = new DBConnection().getCon().createStatement();
			reader = new BufferedReader(new FileReader(file));
			String tempString = null;
			while ((tempString = reader.readLine()) != null) {
				String[] temp = tempString.split(",");
				String sql = "update finance set value='" + temp[14] + "' where code='" + temp[1].replace("'", "") + "' and date='"
						+ temp[0] + "'; ";
				System.out.println(sql);
				statement.execute(sql);
			}
			reader.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		}
	}
	public double getRound(double d) {
		return ((double) Math.round(d * 100)) / 100;
	}
	public static void main(String[] args) throws Exception {
		Frequency rFrequency=new Frequency("600066",1,0.1);
		Temp temp =new Temp();
		temp.MyWrite(rFrequency.toTxt(), "./");
	
		/*try {
			ArrayList<String> stockList = new ArrayList<>();
			stockList.add("600418");
			stockList.add("002594");
			stockList.add("000957");
			stockList.add("600104");
			stockList.add("601238");
			stockList.add("600066");
			stockList.add("000625");
			Temp temp=new Temp();
			for(String a:stockList){
				String data="date,chg\r\n";
				Frequency frequency=new Frequency(a,2,0.1);
				ArrayList<Double> chg=frequency.filter();
				ArrayList<String> date=frequency.getDate();
				for(int i=0;i<chg.size();i++){
					data=data+date.get(i)+","+temp.getRound(chg.get(i).doubleValue())+"\r\n";
				}
				temp.MyWrite(data, "./pakage/"+a+".csv");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}*/
	}

}
