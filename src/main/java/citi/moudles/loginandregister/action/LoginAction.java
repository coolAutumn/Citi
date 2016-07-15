package citi.moudles.loginandregister.action;

import citi.moudles.loginandregister.service.LoginAndRegisterService;
import com.opensymphony.xwork2.ActionSupport;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

/**
 * Created by coolAutumn on 7/14/16.
 */
public class LoginAction extends ActionSupport {

    String username;
    String password;
    InputStream inputStream;

    @Autowired
    LoginAndRegisterService loginAndRegisterService;

    @Override
    public String execute() throws Exception {
        if(username == null || password == null){
            inputStream = new ByteArrayInputStream("infolack".getBytes());
        }else{
            int result = loginAndRegisterService.login(username,password);
            if(result == 1){
                inputStream = new ByteArrayInputStream("success".getBytes());
            }else if(result == -1){
                inputStream = new ByteArrayInputStream("wrongusername".getBytes());
            }else if(result == -2){
                inputStream = new ByteArrayInputStream("wrongpass".getBytes());
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
}
