package citi.util.messagesender.service;

/**
 * Created by coolAutumn on 7/14/16.
 */
public interface MessageSenderService {
    /**
     * 发送短信接口
     * @param phoneNumber   需要收到短信的手机号码
     * @param type          需要发送短信的类型 1--注册账户 2--找回密码
     * @return
     */
    public boolean sendMessage(String phoneNumber,int type);
}
