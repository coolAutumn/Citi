package citi.util.messagesender.service;

/**
 * Created by coolAutumn on 7/14/16.
 */
public interface MessageSenderService {
    //发送短信接口
    public boolean sendMessage(String phoneNumber);
}
