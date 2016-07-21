package citi.moudles.loginandregister.action;

import citi.moudles.loginandregister.service.LoginAndRegisterService;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import java.io.ByteArrayInputStream;
import java.io.InputStream;

/**
 * Created by coolAutumn on 7/14/16.
 */
@Component
public class LoginAction implements Action {

    String username;
    String password;
    public InputStream inputStream;

    @Autowired
    LoginAndRegisterService loginAndRegisterService;


    public String login() throws Exception {
        if(username == null || password == null){
            inputStream = new ByteArrayInputStream("paramslack".getBytes());
        }else{
            int result = loginAndRegisterService.login(username,password);

            if(result == 1){
                ActionContext.getContext().getSession().put("login","hasLogin");
                inputStream = new ByteArrayInputStream("success".getBytes());
            }else if(result == -1){
                inputStream = new ByteArrayInputStream("wrongphonenumber".getBytes());
            }else if(result == -2){
                inputStream = new ByteArrayInputStream("wrongpass".getBytes());
            }else{
                inputStream = new ByteArrayInputStream("fail".getBytes());
            }
        }

        return SUCCESS;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setLoginAndRegisterService(LoginAndRegisterService loginAndRegisterService) {
        this.loginAndRegisterService = loginAndRegisterService;
    }

    public InputStream getInputStream() {
        return inputStream;
    }

    public void setInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    public String execute() throws Exception {
        return null;
    }
}
