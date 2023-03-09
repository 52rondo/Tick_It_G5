package tw.com.tibame.event.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.PageContext;

import tw.com.tibame.event.model.EventVO;
import tw.com.tibame.event.model.OrderService;
import tw.com.tibame.event.model.OrderVO;
import tw.com.tibame.staff.model.StaffService;
import tw.com.tibame.staff.model.StaffVO;

@WebServlet("/OrderServlet")
public class OrderServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);

	}

	@SuppressWarnings("unused")
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");
		HttpSession session = request.getSession();
		String action = request.getParameter("action");
		System.out.println("action: " + request.getParameter("action"));
		response.setContentType("text/html; charset=UTF-8");

		if ("search".equals(action)) { // 查看詳情按鈕
//		System.out.println("in search" + action);
			List<String> errorMsgs = new LinkedList<String>();
//		先把errorMsgs new出來，再裝進去request
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			request.setAttribute("errorMsgs", errorMsgs);

			String searchByNumber = request.getParameter("searchByNumber");
			StaffService staffSvc = new StaffService();
			StaffVO orderEventVO = null;

			/*************************** 3.查詢完成,準備轉交(Send the Success view) *************/
			request.setAttribute("orderEventVO", orderEventVO); // 資料庫取出的staffVO物件,存入request
			String url = "/back-staff-end/staff/ticketOrder.jsp";
			RequestDispatcher successView = request.getRequestDispatcher(url); // 成功轉交 ticketOrder.jsp
			successView.forward(request, response);
		}

//		if ("searchAllEvent".equals(action)) { // 來自listAllStaff.jsp的請求
////			System.out.println("in search" + action);
//			List<String> errorMsgs = new LinkedList<String>();
////			先把errorMsgs new出來，再裝進去request
//			// Store this set in the request scope, in case we need to
//			// send the ErrorPage view.
//			request.setAttribute("errorMsgs", errorMsgs);
//
//			String findByOrganizerNumber = request.getParameter("findByOrganizerNumber");
//			OrderService orderSvc = new OrderService();
//			List<EventVO> eventVO = null;
//			Integer int_sbn = null;
//			try {
////				 轉型 STR轉INT，用PARSEINT()
//				Integer int_fbon = Integer.valueOf(findByOrganizerNumber);
//				eventVO = orderSvc.organizerNumber();
//				if(eventVO.get(0) == null) {
//					eventVO = null;
//				}
//			} catch (NumberFormatException e) {
//				errorMsgs.add("未輸入廠商編號");
//			}
//			
//			if (eventVO == null) {
//				System.out.println("in null");
//				errorMsgs.add("此廠商編號不存在，請再確認一次");
//			}
//	
//			if (!errorMsgs.isEmpty()) {
//				RequestDispatcher failureView = request.getRequestDispatcher("/back-staff-end/staff/listAllStaff.jsp");
//				failureView.forward(request, response);
//				return;// 程式中斷
//			}
//
//			/*************************** 3.查詢完成,準備轉交(Send the Success view) *************/
//			request.setAttribute("eventVO", eventVO); // 資料庫取出的staffVO物件,存入request
//			String url = "/back-staff-end/staff/ListAllEvent.jsp";
//			RequestDispatcher successView = request.getRequestDispatcher(url); // 成功轉交 ticketOrder.jsp
//			successView.forward(request, response);
//		}

		if ("show_all_event_order".equals(action)) { // 來自listAllEvent.jsp的請求
			String eventNumber = request.getParameter("eventNumber");
//			System.out.println("eventNumber =>>>>>>>:" + "123");

			EventVO eventVO = new EventVO();
			Integer int_eventnumber = Integer.valueOf(eventNumber);
			eventVO.setEventNumber(int_eventnumber);

			OrderService orderSvc = new OrderService();
			List<OrderVO> orderVO = orderSvc.selectByEventNumber(int_eventnumber);// 先寫死
			
			request.setAttribute("eventVO", eventVO); // 資料庫取出的staffVO物件,存入request
//			System.out.println("eventNumber =>>>>>>>:" + "1234");
			String url = "/front-end/event/listAllEvent.jsp";
			RequestDispatcher successView = request.getRequestDispatcher(url); // 成功轉交 ticketOrder.jsp
			successView.forward(request, response);
		}

		if ("search".equals(action)) { // 來自listOneOrganizerEvent.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// 先把errorMsgs new出來，再裝進去request
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			request.setAttribute("errorMsgs", errorMsgs);

			String searchEventType = request.getParameter("searchEventType");
			OrderService orderSvc = new OrderService();

//			Integer int_ssn = null;
			OrderVO orderVO = null;
			try {
				// 轉型 STR轉INT，用PARSEINT()
//				int_ssn = Integer.valueOf(searchStaffNumber);
//				staffVO = staffSvc.findByStaffNumber(int_ssn);
				if (orderVO.getOrderType() == null) {
					orderVO = null;
				}
			} catch (NumberFormatException e) {
				errorMsgs.add("未輸入篩選條件");
			}

//			System.out.println("staffVO servlet="+staffVO.toString());
//			if (orderVO == null) {
////				System.out.println("in null");
//				errorMsgs.add("此員工編號不存在，請再確認一次");
//			}

			if (!errorMsgs.isEmpty()) {
				RequestDispatcher failureView = request.getRequestDispatcher("/back-staff-end/staff/listAllStaff.jsp");
				failureView.forward(request, response);
				return;// 程式中斷
			}

			/*************************** 3.查詢完成,準備轉交(Send the Success view) *************/
			request.setAttribute("orderVO", orderVO); // 資料庫取出的staffVO物件,存入request
			String url = "/front-end/staff/listEventType.jsp";
			RequestDispatcher successView = request.getRequestDispatcher(url); // 成功轉交 listOneStaff.jsp
			successView.forward(request, response);
		}

		if ("searchByOrderID".equals(action)) { // 來自listAllStaff.jsp的請求
			List<String> errorMsgs = new LinkedList<String>();
//			先把errorMsgs new出來，再裝進去request
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			request.setAttribute("errorMsgs", errorMsgs);

			String searchByOrderID = request.getParameter("searchByOrderID");
			OrderService orderSvc = new OrderService();
//System.out.println(searchByOrderID);
		
			OrderVO orderVO = null;
			List<Map> orderVO_list = new ArrayList<Map>();		
			
			try {
//				int_sboID = Integer.valueOf(searchByOrderID);
//				System.out.println("轉型"+int_sboID);
				Integer int_sbID = Integer.valueOf(searchByOrderID);
				//*************************************
//				session.getAttribute("organizerlogin data");//必須串到廠商登入資訊,在放到searchByOrderID();
				//*************************************
				orderVO_list = orderSvc.searchByOrderID(int_sbID,1);//先寫死 要套上面拿到的資料
//				System.out.println("@@@@@"+int_sboID);
//				if (orderVO_list.get(0) == null) {
//					orderVO_list = null;
//				}
			} catch (NumberFormatException e) {
				errorMsgs.add("未輸入訂單編號");
			}

			if (orderVO_list.isEmpty() && searchByOrderID.length() != 0) {
					errorMsgs.add("此訂單編號不存在，請再確認一次");
			}

			if (!errorMsgs.isEmpty()) {
				RequestDispatcher failureView = request
						.getRequestDispatcher("/front-end/event/listOneOrganizerEvent.jsp");
				failureView.forward(request, response);
				return;// 程式中斷
			}

			/*************************** 3.查詢完成,準備轉交(Send the Success view) *************/
			request.setAttribute("orderVO_list", orderVO_list); // 資料庫取出的staffVO物件,存入request
			String url = "/front-end/event/searchByOrderID.jsp";
			RequestDispatcher successView = request.getRequestDispatcher(url); // 成功轉交 listOneStaff.jsp
			successView.forward(request, response);
		}

		if ("searchByNumber".equals(action)) { // 來自listAllStaff.jsp的請求
//			System.out.println("in search" + action);
			List<String> errorMsgs = new LinkedList<String>();
//			先把errorMsgs new出來，再裝進去request
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			request.setAttribute("errorMsgs", errorMsgs);

			String searchByNumber = request.getParameter("searchByNumber");
			OrderService orderSvc = new OrderService();

			Integer int_sbn = null;
			List<OrderVO> orderVO = null;
			try {
				// 轉型 STR轉INT，用valueOf()
				int_sbn = Integer.valueOf(searchByNumber);
				orderVO = orderSvc.selectByNumber(int_sbn);
				if (((OrderVO) orderVO).getNumber() == null) {
					orderVO = null;
				}
			} catch (NumberFormatException e) {
				errorMsgs.add("未輸入會員編號");
			}

			if (orderVO == null && int_sbn != null) {
				errorMsgs.add("此會員編號不存在，請再確認一次");
			}

			if (!errorMsgs.isEmpty()) {
				RequestDispatcher failureView = request.getRequestDispatcher("/front-end/event/selectByNumber.jsp");
				failureView.forward(request, response);
				return;// 程式中斷
			}

			/*************************** 3.查詢完成,準備轉交(Send the Success view) *************/
			request.setAttribute("orderVO", orderVO); // 資料庫取出的orderVO物件,存入request
			String url = "/front-end/event/selectByNumber.jsp";
			RequestDispatcher successView = request.getRequestDispatcher(url); // 成功轉交 selectByNumber.jsp
			successView.forward(request, response);
		}

	}
}
