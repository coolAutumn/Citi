package citi.moudles.codeservice.action;

import citi.util.cmk.web.ForecastData;
import com.opensymphony.xwork2.Action;
import org.apache.struts2.ServletActionContext;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

/**
 * Created by coolAutumn on 9/13/16.
 */
public class GetForecastDataAction implements Action {

    public InputStream inputStream;
    public String codeOrTrade;

    public String execute() throws Exception {
        codeOrTrade = ServletActionContext.getRequest().getParameter("codeortrade");
        if(codeOrTrade == null){
            inputStream = new ByteArrayInputStream("paramslack".getBytes());
            return SUCCESS;
        }
        inputStream = new ByteArrayInputStream(new ForecastData(codeOrTrade).toJson().getBytes());
        ServletActionContext.getResponse().addHeader("Access-Control-Allow-Origin","*");
        return SUCCESS;
    }

    public InputStream getInputStream() {
        return inputStream;
    }
}
