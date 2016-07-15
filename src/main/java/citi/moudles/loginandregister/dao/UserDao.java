package citi.moudles.loginandregister.dao;

import citi.moudles.loginandregister.model.UserinfoEntity;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by coolAutumn on 7/15/16.
 */
@Component(value = "userDao")
public class UserDao{
    @Autowired
    @Qualifier("sessionFactory")
    SessionFactory sessionFactory;

    /**
     * 将新用户插入数据库
     * @param username
     * @param password
     * @param phoneNumber
     * @return 1--插入成功  -1--用户名重复
     */
    public int insertNewUser(String username,String password,String phoneNumber){
        if(selectSpecificUser(username) != null){
            return -1;
        }
        UserinfoEntity user = new UserinfoEntity();
        user.setUname(username);
        user.setUpass(password);
        user.setPhone(phoneNumber);
        sessionFactory.getCurrentSession().save(user);
        return 1;
    }

    public UserinfoEntity selectSpecificUser(String username){
        List<UserinfoEntity> list = sessionFactory.getCurrentSession().createSQLQuery("SELECT * FROM userinfo WHERE uname="+username)
                .addEntity(UserinfoEntity.class).list();
        if(list.size() == 0){
            return null;
        }else{
            return list.get(0);
        }
    }
}
