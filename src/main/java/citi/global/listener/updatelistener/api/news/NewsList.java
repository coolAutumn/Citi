package citi.global.listener.updatelistener.api.news;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class NewsList
 */
@WebServlet("/newslist")
public class NewsList extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public NewsList() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html;charset=utf8");
		PrintWriter out=response.getWriter();
		
		String code=request.getParameter("code");
		String dayString=request.getParameter("day");
		web.NewsList newsList;
		if (code==null){
			return;
		}
		if (dayString==null){
			newsList=new web.NewsList(code);
		}
		else{
			int day;
			try{
				day=Integer.parseInt(dayString);				
			}
			catch(Exception e){
				return;
			}
			newsList=new web.NewsList(code, day);
		}
		
		out.println(newsList.toJson());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
