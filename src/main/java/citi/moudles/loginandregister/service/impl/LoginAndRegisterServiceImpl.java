package citi.moudles.loginandregister.service.impl;

import citi.moudles.loginandregister.dao.UserDao;
import citi.moudles.loginandregister.model.UserinfoEntity;
import citi.moudles.loginandregister.service.LoginAndRegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by coolAutumn on 7/15/16.
 */
@Component
@Transactional
public class LoginAndRegisterServiceImpl implements LoginAndRegisterService {

    @Autowired
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
            if(!userinfoEntity.getPasswd().equals(upass)){
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
