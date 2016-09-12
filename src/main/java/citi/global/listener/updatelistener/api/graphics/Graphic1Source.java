package citi.global.listener.updatelistener.api.graphics;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import citi.util.cmk.web.*;

/**
 * Servlet implementation class Graphic1Source
 */
public class Graphic1Source extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Graphic1Source() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.setContentType("text/html;charset=utf8");
		PrintWriter out=response.getWriter();
		// 股票代码，间隔天数，间隔幅度
		String code=request.getParameter("code");		
		String dayString=request.getParameter("day");
		String stepString=request.getParameter("step");
		if (code==null || dayString==null || stepString==null){
			return;
		}
		int day,step;	
		try{
			day=Integer.parseInt(dayString);
			step=Integer.parseInt(stepString);
		}
		catch(Exception e){
			return;
		}					
		
		Frequency frequency=new Frequency(code,day,step);			
		try {
			String json = frequency.toJson();
			out.println(json);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			return;
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
