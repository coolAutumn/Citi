package citi.moudles.codeservice.action;


import Stock.FinanceOneDay;
import citi.moudles.codeservice.model.FinanceEntity;
import citi.moudles.codeservice.service.CodeService;
import com.opensymphony.xwork2.Action;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by coolAutumn on 7/29/16.
 */
@Component
public class GetStockDataAction implements Action {
    InputStream inputStream;

    private String code;
    private ArrayList<FinanceOneDay> arrayList = new ArrayList<FinanceOneDay>();

    @Autowired
    CodeService codeService;

    public ArrayList<FinanceOneDay> init() {
        try {
            List<FinanceEntity> result = codeService.getStockData(code);
            for(FinanceEntity financeEntity : result) {
                String date = financeEntity.getDate().toString();
                double open = Double.parseDouble(financeEntity.getOpen().toString());
                double close = Double.parseDouble(financeEntity.getClose().toString());
                double low = Double.parseDouble(financeEntity.getLow().toString());
                double high = Double.parseDouble(financeEntity.getHigh().toString());
                FinanceOneDay financeOneDay = new FinanceOneDay(date, open, close, low, high);
                arrayList.add(financeOneDay);
            }
            if (arrayList.size() == 0) {
                return null;
            }
            return arrayList;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String toJson() {
        String json = "noInfo";
        if (init() != null) {
            json = "{\"stock\":\"" + code + "\",\"data\":[\n";
            for (FinanceOneDay a : arrayList) {
                json += "{\"date\":\"" + a.getDate() + "\"" + ",\"open\":" + a.getOpen() + "" + ",\"close\":"
                        + a.getClose() + "" + ",\"low\":" + a.getLow() + "" + ",\"high\":" + a.getHigh() + "" + "},\n";
            }
            json = json.substring(0, json.length() - 2);
            json += "\n]}";
        }
        System.out.println(json);
        return json;
    }

    public String execute() throws Exception {
        code = ServletActionContext.getRequest().getParameter("code");
        if(code == null){
            inputStream = new ByteArrayInputStream("paramslack".getBytes());
            return SUCCESS;
        }
        inputStream = new ByteArrayInputStream(toJson().getBytes());
        return SUCCESS;
    }

    public InputStream getInputStream() {
        return inputStream;
    }
}
