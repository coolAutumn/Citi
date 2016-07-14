package citi.moudles.loginandregister.dao;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * Created by coolAutumn on 7/15/16.
 */
@Component(value = "userDao")
public class UserDao {
    @Autowired
    @Qualifier("sessionFactory")
    SessionFactory sessionFactory;

    public void insertNewUser(String phoneNumber,String password){

    }
}
