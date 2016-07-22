package citi.moudles.loginandregister.action;

import citi.moudles.loginandregister.service.LoginAndRegisterService;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayInputStream;
import java.io.InputStream;

/**
 * Created by coolAutumn on 7/22/16.
 */
public class UpdatePasswordAction implements Action{
    String phoneNumber;
    String newPassword;
    String vc;

    InputStream inputStream;

    @Autowired
    public LoginAndRegisterService loginAndRegisterService;

    public String updatePass(){
        HttpServletRequest httpServletRequest = ServletActionContext.getRequest();
        phoneNumber = httpServletRequest.getParameter("phoneNumber");
        newPassword = httpServletRequest.getParameter("newPassword");
        vc = httpServletRequest.getParameter("vc");
        String vc1 = (String)ActionContext.getContext().getSession().get("vc");

        if(vc.equals(vc1)){
            loginAndRegisterService.updatePassword(phoneNumber,newPassword);
            inputStream = new ByteArrayInputStream("success".getBytes());
        }else{
            inputStream = new ByteArrayInputStream("wrongvc".getBytes());
        }

        return SUCCESS;
    }

    public String execute() throws Exception {
        return null;
    }

    public InputStream getInputStream() {
        return inputStream;
    }

    public void setLoginAndRegisterService(LoginAndRegisterService loginAndRegisterService) {
        this.loginAndRegisterService = loginAndRegisterService;
    }
}
