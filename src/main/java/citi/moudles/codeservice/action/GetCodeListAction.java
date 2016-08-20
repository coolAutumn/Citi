package citi.moudles.codeservice.action;

import FIFO.PinYin;
import Stock.StockInfo;
import citi.moudles.codeservice.model.CompanyEntity;
import citi.moudles.codeservice.service.CodeService;
import com.opensymphony.xwork2.Action;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by coolAutumn on 7/29/16.
 */
@Component
public class GetCodeListAction implements Action{

    @Autowired
    public CodeService codeService;

    private ArrayList<StockInfo> stockInfos = new ArrayList<StockInfo>();
    public InputStream inputStream;

    public ArrayList<StockInfo> init() {
        ArrayList<StockInfo> arrayList = new ArrayList<StockInfo>();
        try {
            List<CompanyEntity> result = codeService.getCodeList();
            for(CompanyEntity companyEntity : result){
                String code = companyEntity.getCode();
                String sname = companyEntity.getSname();
                StockInfo temp = new StockInfo(code, sname);
                arrayList.add(temp);
            }
            Collections.sort(arrayList, new Comparator<StockInfo>() {
                public int compare(StockInfo o1, StockInfo o2) {
                    return o1.getCode().compareTo(o2.getCode());
                }
            });
            stockInfos = arrayList;
            return arrayList;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String toJson() throws Exception {
        init();
        String json = "{\"stocks\":[\n";
        for (StockInfo a : stockInfos) {
            json += "{\"code\":\"" + a.getCode() + "\""
                    + ",\"stockname\":\"" + a.getSname() + "\""
                    + ",\"abb\":\"" + PinYin.converterToFirstSpell(a.getSname()) + "\""
                    + "},\n";
        }
        json = json.substring(0, json.length() - 2);
        json += "\n]}";

        return json;
    }

    public String execute() throws Exception {
        inputStream = new ByteArrayInputStream(toJson().getBytes());
        return SUCCESS;
    }

    public InputStream getInputStream() {
        return inputStream;
    }
}
