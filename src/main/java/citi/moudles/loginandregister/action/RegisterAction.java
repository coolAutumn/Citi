package citi.moudles.loginandregister.action;

import citi.moudles.loginandregister.service.LoginAndRegisterService;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import javax.servlet.http.HttpSession;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Map;

/**
 * Created by coolAutumn on 7/14/16.
 */
public class RegisterAction extends ActionSupport{

    public String username;
    public String phoneNumber;
    public String password;
    public String vc;
    public InputStream inputStream;
    public Map<String,Object> httpSession;

    @Autowired
    public LoginAndRegisterService loginAndRegisterService;


    @Override
    public String execute() throws Exception {
        httpSession = ActionContext.getContext().getSession();
        inputStream = new ByteArrayInputStream("success".getBytes());

        if(!paramNullCheck()){
            inputStream = new ByteArrayInputStream("paramlack".getBytes());
            return SUCCESS;
        }
        if(httpSession.get("vc").equals(vc)){
            int result = loginAndRegisterService.insertNewUser(username,password,phoneNumber);
            if(result == -1){
                inputStream = new ByteArrayInputStream("duplicate".getBytes());
            }
        }else{
            inputStream = new ByteArrayInputStream("wrongvc".getBytes());
        }
        return SUCCESS;
    }

    public boolean paramNullCheck(){
        if(username == null || phoneNumber ==null || password == null || vc == null){
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


}
