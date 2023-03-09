package tw.com.tibame.main;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import tw.com.tibame.event.model.EventService;
import tw.com.tibame.event.model.EventVO;
import tw.com.tibame.member.model.*;

@WebServlet("/IndexEventSearch")
public class IndexEventSearch extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Gson gson1;
	
	public void init() throws ServletException {
		gson1 = new Gson();
	}
	
    public IndexEventSearch() {
        super();
    }
    
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		res.setContentType("text/html; charset=UTF-8");
		String keyword = req.getParameter("mainSearch");
		System.out.println(keyword);
		if(keyword != null) {
			System.out.println("keyword: " + keyword);
			EventService es1 = new EventService();
			List<EventVO> list1= new ArrayList<EventVO>();
			if(! keyword.trim().equals("")) {
				list1 = es1.searchEvent(keyword);
				
			}else {
				list1 = es1.selectAllEvent();
			}
			
			JsonObject resBody = new JsonObject();
			if (! list1.isEmpty()) {
				resBody.addProperty("successful", true);
				resBody.addProperty("allEvents", gson1.toJson(list1));
			} else {
				resBody.addProperty("successful", false);
				list1 = es1.selectAllEvent();
				resBody.addProperty("allEvents", gson1.toJson(list1));
			}
			res.getWriter().write(resBody.toString());
			
			
		}else {
			System.out.println("keyword is null");
		}
	
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	
	

}
