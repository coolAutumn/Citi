package citi.moudles.loginandregister.action;

import com.opensymphony.xwork2.ActionSupport;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

/**
 * Created by coolAutumn on 7/14/16.
 */
public class RegisterAction extends ActionSupport{
    public String phoneNumber;
    public String password;



    @Override
    public String execute() throws Exception {

        return super.execute();
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
