package citi.util.cmk.Statistic;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;

import com.mathworks.toolbox.javabuilder.MWException;
import com.mathworks.toolbox.javabuilder.MWNumericArray;

import Huaqi.Class1;
import citi.util.cmk.web.Frequency;
import citi.util.cmk.web.Trade;

public class CalStactic {
	public static final String GAUSS ="ans(x) =  a1*exp(-((x-b1)/c1)^2) + a2*exp(-((x-b2)/c2)^2)";
	public static final String WEIBU ="ans(x) = a*(1+a*b*x)^(-1-1/b)";
	private ArrayList<Double> gaussPara=new ArrayList<Double>();
	private ArrayList<Double> weibuPara=new ArrayList<Double>();
	private Class1 class1;
	
	public CalStactic() throws MWException {
		gaussPara=new ArrayList<Double>();
		weibuPara=new ArrayList<Double>();
		class1 = new Class1();
	}
	
	public ArrayList<Double> getGaussPara() {
		return gaussPara;
	}
	public ArrayList<Double> getWeibuPara() {
		return weibuPara;
	}
	public void getStockPara(String code) {
		gaussPara=new ArrayList<Double>();
		weibuPara=new ArrayList<Double>();
		try {
			File file1 = new File("./Data1/"+code+".csv");
			if (!file1.exists()) {
				File file2=new File("./Data1");
				if(!file2.exists()){
					file2.mkdirs();
				}
			}
			Frequency frequency=new Frequency(code, 1, 0.1);
			file1.createNewFile();
			FileWriter fileWritter = new FileWriter(file1, false);
			BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
			bufferWritter.write(frequency.toTxt());
			bufferWritter.close();
			Object[] result1= class1.huaqi_total(6,file1.getAbsolutePath());
			for(Object a:result1){
				MWNumericArray c=(MWNumericArray)a;
				gaussPara.add(c.getDouble());
			}
			Object[] result2= class1.huaqi_weibu(2,file1.getAbsolutePath());
			for(Object a:result2){
				MWNumericArray c=(MWNumericArray)a;
				weibuPara.add(c.getDouble());
			}
			file1.delete();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void getTradePara(String tradename) {
		gaussPara=new ArrayList<Double>();
		weibuPara=new ArrayList<Double>();
		try {
			File file1 = new File("./Data2/"+tradename+".csv");
			if (!file1.exists()) {
				File file2=new File("./Data2");
				if(!file2.exists()){
					file2.mkdirs();
				}
			}
			Trade trade = new Trade(tradename);
			file1.createNewFile();
			FileWriter fileWritter = new FileWriter(file1, false);
			BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
			bufferWritter.write(trade.toTxt());
			bufferWritter.close();
			Object[] result1= class1.huaqi_total(6,file1.getAbsolutePath());
			for(Object a:result1){
				MWNumericArray c=(MWNumericArray)a;
				gaussPara.add(c.getDouble());
			}
			Object[] result2= class1.huaqi_weibu(2,file1.getAbsolutePath());
			for(Object a:result2){
				MWNumericArray c=(MWNumericArray)a;
				weibuPara.add(c.getDouble());
			}
			file1.delete();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
