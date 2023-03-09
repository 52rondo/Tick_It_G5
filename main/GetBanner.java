package tw.com.tibame.main;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import tw.com.tibame.event.model.EventService;

@WebServlet("/GetBanner")
public class GetBanner extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Gson gson;
	public void init() throws ServletException {
		System.out.println("init GetBanner Servlet");
		gson = new Gson();
	}
	
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		res.setContentType("text/html; charset=UTF-8");
		System.out.println("GetBanner received doGet");
		
		EventService es1 = new EventService();
		List bannerL = es1.getBanner();
	
		JsonObject resBody = new JsonObject();
		if(bannerL != null) {
			resBody.addProperty("bannerL", gson.toJson(bannerL));
			resBody.addProperty("successful", true);
		}else {
			resBody.addProperty("successful", false);
			System.out.println("noBanner");
		}
		res.getWriter().write(resBody.toString());
	}

//	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		// TODO Auto-generated method stub
//		doGet(request, response);
//	}
	
	
}
