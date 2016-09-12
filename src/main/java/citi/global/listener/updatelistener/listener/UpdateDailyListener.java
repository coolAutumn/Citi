package citi.global.listener.updatelistener.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import citi.global.listener.updatelistener.finance.UpdateDailyTimer;

/**
 * Application Lifecycle Listener implementation class UpdateDailyListener
 *
 */

public class UpdateDailyListener implements ServletContextListener {

    /**
     * Default constructor. 
     */
    public UpdateDailyListener() {
        // TODO Auto-generated constructor stub
    }

	/**
     * @see ServletContextListener#contextDestroyed(ServletContextEvent)
     */
    public void contextDestroyed(ServletContextEvent arg0)  { 
         // TODO Auto-generated method stub
    }

	/**
     * @see ServletContextListener#contextInitialized(ServletContextEvent)
     */
    public void contextInitialized(ServletContextEvent arg0)  { 
         // TODO Auto-generated method stub
    	Thread updateDailyTimer=new Thread(new UpdateDailyTimer());
    	System.out.println("start update timer");
    	updateDailyTimer.start();
   	
    }
	
}
