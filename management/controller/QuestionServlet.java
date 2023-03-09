package tw.com.tibame.management.controller;

import java.io.IOException;
import java.time.LocalDateTime;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import tw.com.tibame.management.model.*;


@WebServlet("/QuestionServlet")
public class QuestionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public QuestionServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		res.setContentType("text/html; charset=UTF-8");
		
		String doTask = req.getParameter("doTask");
		//get parameters
		String commonID = req.getParameter("commonID");
		String commonName = req.getParameter("commonName");
		String commonContent = req.getParameter("commonContent");
		String commonCreateDate = req.getParameter("commonCreateDate");
		Integer sort = Integer.parseInt(req.getParameter("sort"));
		QuestionService qs1 = new QuestionService();
		if(doTask != null) {
			if(doTask.equals("insert")) {
				System.out.println("doTask == INSERT");
				if(commonName != null && commonName.trim()!="" && commonContent != null && commonContent.trim()!=""
						&& sort!=null ) {
					System.out.println("BEFORE CREATING!");
					QuestionVO vo1 = new QuestionVO();
					System.out.println("AFTER CREATING");
					vo1.setCommonName(commonName);
					vo1.setCommonContent(commonContent);
					vo1.setSort(sort);
					qs1.addQuestion(vo1);
					System.out.println("addQuestion using service");
				}
				
				
			}else {
				System.out.println("doTask not insert");
			}
			
		}else {
			System.out.println("doTask == null");
		}
	}
}
