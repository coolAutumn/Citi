package citi.global.interceptors;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.MethodFilterInterceptor;
import org.apache.struts2.ServletActionContext;

import javax.servlet.ServletContext;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Map;

/**
 * Created by coolAutumn on 7/14/16.
 */
public class LoginInterceptor extends MethodFilterInterceptor{

    @Override
    protected String doIntercept(ActionInvocation actionInvocation) throws Exception {

        Map<String,Object> sessionMap = ActionContext.getContext().getSession();


        //登录action中会给session中添加 login/haslogin 的键值对
        String login;
        if( (login = (String)sessionMap.get("login")) != null){
            if(login.equals("hasLogin")){
                actionInvocation.invoke();
            }
        }

        return "loginneed";
    }


}
