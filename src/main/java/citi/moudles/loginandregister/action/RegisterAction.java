package citi.moudles.loginandregister.action;

import citi.moudles.loginandregister.service.LoginAndRegisterService;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

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

        if(!paramNullCheck()){
            inputStream = new ByteArrayInputStream("paramlack".getBytes());
            return SUCCESS;
        }

        if(httpSession.get("vc").equals(vc)){
            int result = loginAndRegisterService.insertNewUser(username,password,phoneNumber);
            if(result == -1){
                inputStream = new ByteArrayInputStream("duplicate".getBytes());
            }else if(result != 1){
                inputStream = new ByteArrayInputStream("registerfail".getBytes());
            }else{
                inputStream = new ByteArrayInputStream("fail".getBytes());
            }
        }else{
            inputStream = new ByteArrayInputStream("wrongvc".getBytes());
        }

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
