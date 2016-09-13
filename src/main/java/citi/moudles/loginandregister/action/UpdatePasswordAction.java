package citi.moudles.loginandregister.action;

import citi.moudles.loginandregister.dao.UserDao;
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

    @Autowired
    public UserDao userDao;

    public String updatePass(){
        HttpServletRequest httpServletRequest = ServletActionContext.getRequest();
        phoneNumber = httpServletRequest.getParameter("phoneNumber");
        newPassword = httpServletRequest.getParameter("newPassword");
        vc = httpServletRequest.getParameter("vc");
        String vc1 = (String)ActionContext.getContext().getSession().get("vc_2");
        String phoneNumber_2 = (String)ActionContext.getContext().getSession().get("phoneNumber_2");

        if(phoneNumber == null || newPassword == null || vc == null){
            inputStream = new ByteArrayInputStream("paramslack".getBytes());
            return SUCCESS;
        }

        //先判断该号码是否已经注册
        if(userDao.selectSpecificUser(phoneNumber) == null){
            inputStream = new ByteArrayInputStream("notregister".getBytes());
            return SUCCESS;
        }
        if(vc.equals(vc1) && phoneNumber_2.equals(phoneNumber)){
            loginAndRegisterService.updatePassword(phoneNumber,newPassword);
            inputStream = new ByteArrayInputStream("success".getBytes());
        }else{
            inputStream = new ByteArrayInputStream("wrongvcorphone".getBytes());
        }
        ServletActionContext.getResponse().addHeader("Access-Control-Allow-Origin","*");
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
