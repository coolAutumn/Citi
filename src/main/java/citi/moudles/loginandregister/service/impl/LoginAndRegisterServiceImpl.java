package citi.moudles.loginandregister.service.impl;

import citi.moudles.loginandregister.dao.UserDao;
import citi.moudles.loginandregister.model.UserinfoEntity;
import citi.moudles.loginandregister.service.LoginAndRegisterService;
import org.springframework.stereotype.Component;

/**
 * Created by coolAutumn on 7/15/16.
 */
@Component(value = "loginAndRegisterService")
public class LoginAndRegisterServiceImpl implements LoginAndRegisterService {

    public UserDao userDao;


    public int insertNewUser(String uname, String upass, String phone) {
        return userDao.insertNewUser(uname,upass,phone);
    }

    /**
     * 判断登录是否合法
     * @param uname     用户名
     * @param upass     密码
     * @return  -1--用户名不存在  -2--密码错误  1--登陆成功
     */
    public int login(String uname, String upass) {
        UserinfoEntity userinfoEntity = userDao.selectSpecificUser(uname);
        if(userinfoEntity != null){
            if(!userinfoEntity.getUpass().equals(upass)){
                return -2;
            }else{
                return 1;
            }
        }
        return -1;
    }

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

}
