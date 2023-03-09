package tw.com.tibame.management.controller;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import tw.com.tibame.management.model.*;

@WebServlet("/TermsServlet")
public class TermsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request,response);
}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		//set req types encodings
		req.setCharacterEncoding("UTF-8");
		res.setContentType("text/html; charset=UTF-8");
		// get parameters
		String termsID = req.getParameter("termsID");
		String termsTitle = req.getParameter("termsTitle");
		String termsContent = req.getParameter("termsContent");
		//是不是在輸入時自動產生 不用從參數取得一個日期?
		String termsCreateDate = req.getParameter("termsCreateDate");
		
		TermsVO vo1 = new TermsVO();
		if(termsID!=null) {
			vo1.setTermsID(Integer.parseInt(termsID));
		}
		if(termsTitle!=null) {
			vo1.setTermsContent(termsContent);
		}
		if(termsContent!=null) {
			vo1.setTermsContent(termsContent);
		}
		if(termsCreateDate!=null) {
			//String to LocalDateTIMe conversion
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"); 
			LocalDateTime dateTime = LocalDateTime.parse(termsCreateDate, formatter);
			vo1.setTermsCreateDate(dateTime);
		}
		
		String doTask = req.getParameter("doTask");
		TermsService service1 = new TermsService();
		if(doTask != null && doTask.trim()!="") {
			if(doTask.equals("insert")) {
//				System.out.println("doTask = insert");
////				QuestionVO vo2 = new QuestionVO();
//				System.out.println("vo2 created");
//				vo2.setCommonContent("hi");
				if( termsTitle!=null && termsContent!=null
					 && termsTitle.trim()!="" && termsContent.trim()!=""){
					vo1.setTermsTitle(termsTitle);
					vo1.setTermsContent(termsContent);
					service1.createNewTerm(vo1);
					System.out.println("created New Term");
				}else {
					System.out.println("something == null, insert failed");
				}
				
			}else if(doTask.equals("select")){
				
			}else if(doTask.equals("update")){
				
			}
		}else {
			System.out.println("doTask == null or '' ");
		}
		
	}
}

//	public static void main(String args[]) {
//		String hi;
//		hi = "";
//		System.out.println(hi);
//		System.out.println(hi==null);
//		System.out.println(hi=="");
//	}