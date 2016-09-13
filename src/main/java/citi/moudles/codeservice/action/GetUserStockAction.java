package citi.moudles.codeservice.action;

import citi.util.cmk.web.UserStock;
import com.opensymphony.xwork2.Action;
import org.apache.struts2.ServletActionContext;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

/**
 * Created by coolAutumn on 9/13/16.
 */
public class GetUserStockAction implements Action {

    public InputStream inputStream;
    public String phone;
    public String execute() throws Exception {
        phone = ServletActionContext.getRequest().getParameter("phone");
        if(phone == null){
            inputStream = new ByteArrayInputStream("paramslack".getBytes());
            return SUCCESS;
        }
        inputStream = new ByteArrayInputStream(new UserStock(phone).toJson().getBytes());
        ServletActionContext.getResponse().addHeader("Access-Control-Allow-Origin","*");
        return SUCCESS;
    }

    public InputStream getInputStream() {
        return inputStream;
    }
}
