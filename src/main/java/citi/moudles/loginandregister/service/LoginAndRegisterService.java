package citi.moudles.loginandregister.service;

import org.springframework.transaction.annotation.Transactional;

/**
 * Created by coolAutumn on 7/15/16.
 */
@Transactional
public interface LoginAndRegisterService{
    public int insertNewUser(String uname,String upass,String phone);
    public String login(String uname,String upass);
    public int updatePassword(String phoneNumber,String newPass);
}
