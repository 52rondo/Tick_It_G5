package tw.com.tibame.management.controller;

import java.io.IOException;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import tw.com.tibame.management.model.BulletinService;
import tw.com.tibame.management.model.BulletinVO;

@WebServlet("/BulletinServlet")
public class BulletinServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);

	}
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		res.setContentType("text/html; charset=UTF-8");
		String doTask = req.getParameter("doTask");
		String bID = req.getParameter("bulletinID");
		String bName = req.getParameter("bulletinName");
		String bContent = req.getParameter("bulletinContent");
		Timestamp bDateTimestamp = null;
		//利用三元運算 判斷轉換 STRING TO BOOLEAN
		Boolean bTop = req.getParameter("isTop").equals("yes")? true :false;
		Boolean bOpen = req.getParameter("isOpen").equals("yes")? true :false;
		//時間轉換 STRING TO SQL TIMESTAMP
		String bDate = req.getParameter("bulletinDate");
		String bCreateDate = req.getParameter("bulletinCreateDate");
		try {
		    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		    java.util.Date parsedDate = dateFormat.parse(bDate);
		    bDateTimestamp = new java.sql.Timestamp(parsedDate.getTime());
		} catch(Exception e) { //this generic but you can control another types of exception
		    // look the origin of excption 
			System.out.println("exception occurred");
			e.printStackTrace();
		}
		
		
		BulletinService bs1 = new BulletinService();
		BulletinVO vo1 = new BulletinVO();
		if(doTask.equals("insert")) {
			vo1.setBulletinName(bName);
			vo1.setBulletinContent(bContent);
			vo1.setIsTop(bTop);
			vo1.setIsOpen(bOpen);
			vo1.setBulletinDate(bDateTimestamp);
			bs1.addBulletin(vo1);
			String url = "back-staff-end/management/AddBulletin.jsp";
			RequestDispatcher rd = req.getRequestDispatcher(url);
			rd.forward(req, res);
			return;
		}else if (doTask.equals("select")) {
			
		}else {
			
		}
		
		
		
	}
}
