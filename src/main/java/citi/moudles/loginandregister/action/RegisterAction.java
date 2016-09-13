package citi.moudles.loginandregister.action;

import citi.moudles.loginandregister.service.LoginAndRegisterService;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.ServletActionContext;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Map;

/**
 * Created by coolAutumn on 7/14/16.
 */
@Component
public class RegisterAction implements Action {

    public String username;
    public String phoneNumber;
    public String password;
    public String vc;
    public InputStream inputStream;
    public Map<String,Object> httpSession;

    @Autowired
    public LoginAndRegisterService loginAndRegisterService;



    public String register() throws Exception {
        httpSession = ActionContext.getContext().getSession();
        inputStream = new ByteArrayInputStream("success".getBytes());

        HttpServletRequest httpServletRequest = ServletActionContext.getRequest();

        username = httpServletRequest.getParameter("username");
        password = httpServletRequest.getParameter("password");
        phoneNumber = httpServletRequest.getParameter("phoneNumber");
        vc = httpServletRequest.getParameter("vc");

        if(!paramNullCheck()){
            inputStream = new ByteArrayInputStream("paramslack".getBytes());
            return SUCCESS;
        }

        System.out.println(httpSession.get("vc_1")+","+vc);

        if(httpSession.get("vc_1").equals(vc) && httpSession.get("phoneNumber_1").equals(phoneNumber)){
            int result = loginAndRegisterService.insertNewUser(username,password,phoneNumber);
            if(result == -1){
                inputStream = new ByteArrayInputStream("duplicate".getBytes());
            }else if(result != 1){
                inputStream = new ByteArrayInputStream("registerfail".getBytes());
            }
        }else{
            inputStream = new ByteArrayInputStream("wrongvcorphone".getBytes());
        }
        ServletActionContext.getResponse().addHeader("Access-Control-Allow-Origin","*");
        return SUCCESS;
    }

    public boolean paramNullCheck(){
        if(phoneNumber ==null || password == null || vc == null){
            return false;
        }
        return true;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public void setVc(String vc) {
        this.vc = vc;
    }
    public InputStream getInputStream() {
        return inputStream;
    }
    public void setLoginAndRegisterService(LoginAndRegisterService loginAndRegisterService) {
        this.loginAndRegisterService = loginAndRegisterService;
    }
    public void setInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
    }


    public String execute() throws Exception {
        return null;
    }
}
