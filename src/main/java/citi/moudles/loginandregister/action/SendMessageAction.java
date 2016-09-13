package citi.moudles.loginandregister.action;

import citi.moudles.loginandregister.dao.UserDao;
import citi.util.messagesender.service.MessageSenderService;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayInputStream;
import java.io.InputStream;

/**
 * Created by coolAutumn on 7/14/16.
 */
@Component
public class SendMessageAction implements Action {

    public String phoneNumber;
    public String type = null;        //所需要发送短信的类型

    @Autowired
    public MessageSenderService messageSenderService;   //用来发送短信验证码的服务

    @Autowired
    public UserDao userDao;

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    public InputStream inputStream;

    /**
     * inputStream的返回值
     * success              发送成功
     * phone_number_need    未输入phoneNumber
     * fail                 发送失败
     */
    public String send() throws Exception {
        HttpServletRequest httpServletRequest = ServletActionContext.getRequest();

        phoneNumber = httpServletRequest.getParameter("phoneNumber");
        type        = httpServletRequest.getParameter("type");
        //判断是否已存在用户
        if(!type.equals("2") && userDao.selectSpecificUser(phoneNumber) != null){
            inputStream = new ByteArrayInputStream("duplicate".getBytes());
            return SUCCESS;
        }


        if(phoneNumber != null || type != null){

            if(messageSenderService.sendMessage(phoneNumber,type)){
                inputStream = new ByteArrayInputStream("success".getBytes());
            }else{
                inputStream = new ByteArrayInputStream("fail".getBytes());
            }
        }else{
            inputStream = new ByteArrayInputStream("paramslack".getBytes());
        }
        ServletActionContext.getResponse().addHeader("Access-Control-Allow-Origin","*");
        return SUCCESS;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    public InputStream getInputStream() {
        return inputStream;
    }
    public void setInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String execute() throws Exception {
        return null;
    }
}
