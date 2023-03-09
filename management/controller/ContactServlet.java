package tw.com.tibame.management.controller;

import java.io.BufferedReader;

import java.io.IOException;

import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import tw.com.tibame.management.model.*;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

@WebServlet("/ContactServlet")
public class ContactServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Gson gson1;
	private ContactService cs1;
	
	@Override
	public void init() throws ServletException {
		try {
			cs1 = new ContactService();
		} catch (Exception e) {
			e.printStackTrace();
		}
		gson1 = new Gson();
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
//		req.setCharacterEncoding("UTF-8");
//		res.setContentType("text/html; charset=UTF-8");
//	
//		String doTask = req.getParameter("doTask");
//		String contactId = req.getParameter("contactID");
//		String contactTitle = req.getParameter("contactTitle");
//		String contactContent = req.getParameter("contactContent");
//		
		
		ContactVO vo1 = new ContactVO();

//		ContactVO vo1 = new ContactVO();
//		if(doTask!=null) {
//			if(doTask.equals("insert")) {
//				if(contactTitle!=null && contactTitle.trim()!="" && contactContent!=null 
//						&& contactContent.trim()!="") {
//					vo1.setContactTitle(contactTitle);
//					vo1.setContactContent(contactContent);
//					cs1.addContactUs(vo1);
//				}else {
//					System.out.println("something is null or ''");
//				}			
//			}else if(doTask.equals("update")) {
//				
//			}else {
//				
//			}
//		}
		
		
		
		vo1 = gson1.fromJson(req.getReader(), ContactVO.class);
		
		
		JsonObject resBody = new JsonObject();
		if (vo1 == null) {
			resBody.addProperty("successful", false);
		} else {
			resBody.addProperty("successful", cs1.addContactUs(vo1));
		}
		
		res.getWriter().write(resBody.toString());
		
	}

}
