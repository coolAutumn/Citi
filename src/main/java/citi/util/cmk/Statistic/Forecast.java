package citi.util.cmk.Statistic;

import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.mathworks.toolbox.javabuilder.MWArray;
import com.mathworks.toolbox.javabuilder.MWException;
import com.mathworks.toolbox.javabuilder.MWNumericArray;

import citi.util.cmk.dbOp.DBConnection;
import forecast.*;

public class Forecast {
	private Class1 class1;
	private SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");

	public Forecast() throws MWException {
		class1 = new Class1();
	}

	public void getForecast(String codeortrade) {
		try {
			double[] paras=new double[8];
			DBConnection dbConnection = new DBConnection();
			Statement statement = dbConnection.getCon().createStatement();
			ResultSet resultSet=statement.executeQuery("select * from gausspara where codeortrade ='"+codeortrade+"' and date ='"+df.format(new Date())+"';");
			resultSet.next();
			for(int i=0;i<6;i++){
				paras[i]=resultSet.getDouble(i+1);
			}
			resultSet=statement.executeQuery("select * from weibupara where codeortrade ='"+codeortrade+"' and date ='"+df.format(new Date())+"';");
			resultSet.next();
			for(int i=0;i<2;i++){
				paras[i+6]=resultSet.getDouble(i+1);
			}
			Object[] object=class1.random(1,paras[1], paras[1],paras[2],paras[3],paras[4],paras[5],paras[6],paras[7]);
			for(Object o:object){
				MWNumericArray c=(MWNumericArray)o;
				System.out.println(c.getDouble());
			}
			
		} catch (Exception e) {
		e.printStackTrace();
		}
	}
	public static void main(String[] args) throws MWException {
		Forecast forecast=new Forecast();
		forecast.getForecast("600066");
	}

}
