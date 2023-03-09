package tw.com.tibame.member.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import tw.com.tibame.event.model.EventService;
import tw.com.tibame.event.model.EventVO;
import tw.com.tibame.member.model.CollectService;
import tw.com.tibame.member.model.MemberVO;

@WebServlet("/FindFavorite")
public class FindFavorite extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Gson gson1;
    public FindFavorite() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request,response);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		res.setContentType("text/html; charset=UTF-8");
        System.out.println("find faorite got request");
        this.gson1 = new Gson();
        CollectService cs1 = new CollectService();
        EventService es1 = new EventService(); 
        List<EventVO> voList = new ArrayList<EventVO>();
		List<Integer> list1 = new ArrayList<Integer>();
		//getting current logged in members memberid
		HttpSession session1 = req.getSession();
		if(session1.getAttribute("memberVO") != null) {
			MemberVO vo = (MemberVO) session1.getAttribute("memberVO");
			int memberId = vo.getNumber();
			list1 = cs1.getFavorite(memberId);
			list1.forEach((e) -> {
				EventVO vo1 = es1.getSingleEvent(e);
				voList.add(vo1);
			});
			System.out.println("add all events to voList");
			session1.setAttribute("favoriteList", voList);
		}else {
			System.out.println("did member login yet?");
		}
		
		JsonObject resBody = new JsonObject();
		if (! voList.isEmpty()) {
			resBody.addProperty("successful", true);
			resBody.addProperty("favEvents", gson1.toJson(voList));
		} else {
			resBody.addProperty("successful", false);
			resBody.addProperty("favEvents", gson1.toJson(voList));
		}
		res.getWriter().write(resBody.toString());
		
		
	}

}
