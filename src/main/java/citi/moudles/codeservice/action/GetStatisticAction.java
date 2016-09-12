package citi.moudles.codeservice.action;

import citi.util.cmk.web.Statistics;
import com.opensymphony.xwork2.Action;
import org.apache.struts2.ServletActionContext;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by coolAutumn on 9/12/16.
 */
public class GetStatisticAction implements Action {

    public String codeOrTrade;
    public InputStream inputStream;


    public String execute() throws Exception {
        codeOrTrade = ServletActionContext.getRequest().getParameter("codeortrade");
        if(codeOrTrade ==null){
            inputStream = new ByteArrayInputStream("paramslack".getBytes());
            return SUCCESS;
        }
        inputStream = new ByteArrayInputStream(new Statistics(codeOrTrade).toJson().getBytes());
        return SUCCESS;
    }

    public InputStream getInputStream() {
        return inputStream;
    }
}
