package tw.com.tibame.main;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import tw.com.tibame.member.model.MemberService;
import tw.com.tibame.member.model.MemberVO;

@WebServlet("/CheckLogin")
public class CheckLogin extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Gson gson;
	public void init() throws ServletException {
		System.out.println("init checkLogin Servlet");
		gson = new Gson();
	}
	
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		System.out.println("check login recieved do Get");
		HttpSession session = req.getSession();
		int memberId = -1;
		if(session.getAttribute("memberVO") != null) {
			MemberVO mvo1 = (MemberVO) session.getAttribute("memberVO");
			memberId = mvo1.getNumber();
		}else {
			memberId = -1;
			//not logged in yet
		}
		JsonObject resBody = new JsonObject();
		if(memberId != -1) {
			System.out.println("login is done");
			resBody.addProperty("successful", true);
			resBody.addProperty("memId", memberId);
		}else {
			System.out.println("login not done");
			resBody.addProperty("successful", false);
		}
		res.getWriter().write(resBody.toString());
	}

}
