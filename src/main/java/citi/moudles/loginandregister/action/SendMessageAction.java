package citi.moudles.loginandregister.action;

import citi.util.messagesender.service.MessageSenderService;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

/**
 * Created by coolAutumn on 7/14/16.
 */
public class SendMessageAction extends ActionSupport {
    public String phoneNumber;

    @Autowired
    public MessageSenderService messageSenderService;   //用来发送短信验证码的服务

    /**
     * inputStream的返回值
     * success              发送成功
     * phone_number_need    未输入phoneNumber
     * fail                 发送失败
     */
    public InputStream inputStream;

    //生成随机6位验证码
    private String getVerificationCode(){
        String vc = "";
        for(int i=0;i<6;i++){
            vc += (int) (Math.random() * 10);
        }
        return vc;
    }

    @Override
    public String execute() throws Exception {
        if(phoneNumber != null){
            //首先获得6位验证码并存储到session中
            String vc = getVerificationCode();
            ActionContext.getContext().getSession().put("vc",vc);

            if(messageSenderService.sendMessage(phoneNumber)){
                inputStream = new ByteArrayInputStream("success".getBytes());
            }else{
                inputStream = new ByteArrayInputStream("fail".getBytes());
            }
        }else{
            inputStream = new ByteArrayInputStream("phone_number_need".getBytes());
        }
        return super.execute();
    }
}
