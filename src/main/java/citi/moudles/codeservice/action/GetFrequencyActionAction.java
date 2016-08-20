package citi.moudles.codeservice.action;

import citi.moudles.codeservice.model.FinanceEntity;
import citi.moudles.codeservice.service.CodeService;
import com.opensymphony.xwork2.Action;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by coolAutumn on 7/29/16.
 */
@Component
public class GetFrequencyActionAction implements Action {

    private LinkedHashMap<String, Integer> aMap = new LinkedHashMap<String, Integer>();
    private String code;
    private int day = -111;
    private double step = -111;

    InputStream inputStream;

    @Autowired
    CodeService codeService;

    public ArrayList<Double> filter() throws Exception {
        try {
            ArrayList<Double> arrayList = new ArrayList<Double>();
            if (day == 1) {
                List<FinanceEntity> result = codeService.getFrequency1(code);
                for(FinanceEntity financeEntity : result) {
                    if (!Boolean.getBoolean(financeEntity.getDr()) && !Boolean.getBoolean(financeEntity.getReplay())) {
                        arrayList.add(Double.parseDouble(financeEntity.getChg().toString()));
                    }
                }
            } else {
                List<FinanceEntity> result = codeService.getFrequency2(code);
                int day_count = 0;
                double end_today = 0;
                double end_lastday = 0;
                boolean dr_b = false;
                boolean re_b = false;
                for(FinanceEntity financeEntity : result) {
                    day_count++;
                    if (day_count == 1) {
                        end_lastday = Double.parseDouble(financeEntity.getOpen().toString());
                    }
                    // 周期涨跌幅
                    if (!dr_b && !re_b) {
                        dr_b = Boolean.getBoolean(financeEntity.getDr());
                        re_b = Boolean.getBoolean(financeEntity.getReplay());
                    }
                    if (day_count % day == 0) {
                        end_today = Double.parseDouble(financeEntity.getClose().toString());
                        double rate = (end_today - end_lastday) / end_lastday;
                        if (!dr_b && !re_b) {
                            arrayList.add(rate * 100);
                        }
                        end_lastday = end_today;
                        dr_b = false;
                        re_b = false;
                    }
                }
            }
            return arrayList;
        } catch (Exception e) {
            e.printStackTrace();
            throw (e);
        }
    }

    public void section() throws Exception {
        ArrayList<Double> arrayList = filter();
        LinkedHashMap<String, Integer> aMap = new LinkedHashMap<String, Integer>();
        Collections.sort(arrayList);

        double bigist = arrayList.get(arrayList.size() - 1).doubleValue();
        double smallist = arrayList.get(0).doubleValue();

        int po = (int) Math.ceil((bigist - step / 2) / step);
        int na = (int) Math.ceil((smallist + step / 2) / step) - 1;

        int num = po + Math.abs(na) + 1;
        String[] No = new String[num];
        int[] count = new int[num];

        for (int i = na; i <= po; i++) {
            double floor = getRound(step * i-step/2);
            double ceil = getRound(step * i +step/2);
            No[i-na] = "" + floor + "%~" + ceil + "%";
            for (Double d : arrayList) {
                if (d.doubleValue() <=ceil && d.doubleValue() > floor) {
                    count[i-na]++;

                } else if (d.doubleValue() >= ceil) {
                    break;
                }
            }
        }
        for (int i = 0; i < num; i++) {
            aMap.put(No[i], count[i]);
        }
        this.aMap = aMap;

    }

    public String toJson() throws Exception {
        section();
        String json = "{\"" + code + "\":[\n";
        for (String a : aMap.keySet()) {
            String[] group = a.split("~");
            double i = Double.parseDouble(group[0].replace("%", ""));
            double j = Double.parseDouble(group[1].replace("%", ""));
            json += "{\"floor\":" + i + "," + "\"ceil\":" + j + "," + "\"count\":" + aMap.get(a).intValue() + "},\n";
        }
        json = json.substring(0, json.length() - 2);
        json += "\n]}";
        System.out.println(json);
        return json;
    }

    public String toTxt() throws Exception{
        section();
        String txt="floor,ceil,avg,num\r\n";
        for (String a : aMap.keySet()) {
            String[] group = a.split("~");
            double i = Double.parseDouble(group[0].replace("%", ""));
            double j = Double.parseDouble(group[1].replace("%", ""));
            txt+=i+","+j+","+getRound((i+j)/2)+","+aMap.get(a).intValue()+"\r\n";
        }
        txt=txt.substring(0,txt.length()-2);

        return txt;
    }

    public double getRound(double d) {
        return ((double)Math.round(d*100))/100;
    }

    public String execute() throws Exception {
        code = ServletActionContext.getRequest().getParameter("code");
        if(code == null || ServletActionContext.getRequest().getParameter("day") == null ||
                ServletActionContext.getRequest().getParameter("step") == null ){
            inputStream = new ByteArrayInputStream("paramslack".getBytes());
            return SUCCESS;
        }
        day  = Integer.parseInt(ServletActionContext.getRequest().getParameter("day"));
        step  = Double.parseDouble(ServletActionContext.getRequest().getParameter("step"));

        inputStream = new ByteArrayInputStream(toJson().getBytes());
        return SUCCESS;
    }

    public InputStream getInputStream() {
        return inputStream;
    }
}
