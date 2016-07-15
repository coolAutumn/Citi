package citi.moudles.loginandregister.service;

/**
 * Created by coolAutumn on 7/15/16.
 */
public interface LoginAndRegisterService{
    public int insertNewUser(String uname,String upass,String phone);
    public int login(String uname,String upass);
}
