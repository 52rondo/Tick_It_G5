package tw.com.tibame.staff.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.swing.JDialog;
import javax.swing.JOptionPane;

import org.json.JSONObject;

import tw.com.tibame.staff.model.StaffService;
import tw.com.tibame.staff.model.StaffVO;

@WebServlet("/StaffServlet")
public class StaffServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);

	}

	@SuppressWarnings("unused")
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession();

		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");

		String action = request.getParameter("action");
		System.out.println("action: " + request.getParameter("action"));
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();

		if ("search".equals(action)) { // 來自listAllStaff.jsp的請求
//			System.out.println("in search" + action);
			List<String> errorMsgs = new LinkedList<String>();
//			先把errorMsgs new出來，再裝進去request
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			request.setAttribute("errorMsgs", errorMsgs);

			String searchStaffNumber = request.getParameter("searchStaffNumber");
			StaffService staffSvc = new StaffService();

			Integer int_ssn = null; // 轉型
			StaffVO staffVO = null;
			try {
				// 轉型 STR轉INT，用valueof()
				int_ssn = Integer.valueOf(searchStaffNumber);
				staffVO = staffSvc.findByStaffNumber(int_ssn);
				if (staffVO.getStaffNumber() == null) {
					staffVO = null;
				}
			} catch (NumberFormatException e) {
				errorMsgs.add("未輸入員工編號");
			}

			if (staffVO == null && int_ssn != null) {
				errorMsgs.add("此員工編號不存在，請再確認一次");
			}

			if (!errorMsgs.isEmpty()) {
				RequestDispatcher failureView = request.getRequestDispatcher("/back-staff-end/staff/listAllStaff.jsp");
				failureView.forward(request, response);
				return;// 程式中斷
			}

			/*************************** 3.查詢完成,準備轉交(Send the Success view) *************/
			request.setAttribute("staffVO", staffVO); // 資料庫取出的staffVO物件,存入request
			String url = "/back-staff-end/staff/listOneStaff.jsp";
			RequestDispatcher successView = request.getRequestDispatcher(url); // 成功轉交 listOneStaff.jsp
			successView.forward(request, response);
		}

		if ("getOne_For_Update".equals(action)) { // 來自listAllStaff.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			request.setAttribute("errorMsgs", errorMsgs);

			/*************************** 1.接收請求參數 ****************************************/
			Integer staffNumber = Integer.valueOf(request.getParameter("staffNumber"));
			/*************************** 2.開始查詢資料 ****************************************/
			StaffService staffSvc = new StaffService();
			StaffVO staffVO = staffSvc.getOneStaff(staffNumber);

			/*************************** 3.查詢完成,準備轉交(Send the Success view) ************/
			request.setAttribute("staffVO", staffVO);
			// 資料庫取出的staffVO物件,存入request
			String url = "/back-staff-end/staff/updateStaff.jsp";
			RequestDispatcher successView = request.getRequestDispatcher(url);
			// 成功轉交 updateStaff.jsp
			successView.forward(request, response);
		}

		if ("insert".equals(action)) { // 來自insertStaff.jsp的請求
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			request.setAttribute("errorMsgs", errorMsgs);

			/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 **********************/
			// 要抓值出來，，所以用.gerParameter來抓，如果是checkbox又不一樣
			String staffName = request.getParameter("staffName");
			String staffNameReg = "^[(\u4e00-\u9fa5)(a-zA-Z)]{2,10}$";
			if (staffName == null || staffName.trim().length() == 0) {
				errorMsgs.add("員工姓名: 請勿空白");
			} else if (!staffName.trim().matches(staffNameReg)) { // 以下練習正則(規)表示式(regular-expression)
				errorMsgs.add("員工姓名: 只能是中、英文字母 , 且長度必需在2到10之間");
			}

			String staffAccount = request.getParameter("staffAccount");
			String staffAccountReg = "^[a-zA-Z_0-9]{6-20}$";
			if (staffAccount == null || staffAccount.trim().length() == 0) {
				errorMsgs.add("員工帳號: 請勿空白");
			}

			String staffPassword = request.getParameter("staffPassword");
			String staffPasswordReg = "^(?=.*d)(?=.*d)(?=.*[a-zA-Z])[da-zA-Z]{6-20}$";
			// 密碼中間同時含字母和數字且長度在6~20之間
			if (staffPassword == null || staffPassword.trim().length() == 0) {
				errorMsgs.add("員工密碼: 請勿空白");
			}

			String[] permissionNumber = request.getParameterValues("permissionNumber");
//			for(String a:permissionNumber) {
//				System.out.println(a);
//			}

			StaffVO staffVO = new StaffVO();
			staffVO.setStaffName(staffName);
			staffVO.setStaffAccount(staffAccount);
			staffVO.setStaffPassword(staffPassword);

			StaffService staffSvc = new StaffService();
			staffVO = staffSvc.getOneByAccount(staffAccount);
			if (staffVO != null) {
				errorMsgs.add("帳號或密碼重複，請再檢查一次帳號或密碼");

			}

			// Send the use back to the form, if there were errors
			if (!errorMsgs.isEmpty()) {
				request.setAttribute("staffVO", staffVO); // 含有輸入格式錯誤的staffVO物件,也存入request
				RequestDispatcher failureView = request.getRequestDispatcher("/back-staff-end/staff/insertStaff.jsp");
				failureView.forward(request, response);
				return;// 程式中斷
			}

			/*************************** 2.開始新增資料 *****************************************/
//			StaffService staffService = new StaffService();
			staffSvc.insertStaff(staffName, staffAccount, staffPassword, permissionNumber);
//			if (staffVO == null) {
//				errorMsgs.add("查無資料");
//			}
//			// Send the use back to the form, if there were errors
			if (!errorMsgs.isEmpty()) {
				RequestDispatcher failureView = request.getRequestDispatcher("/back-staff-end/staff/insertStaff.jsp");
				failureView.forward(request, response);
				return;// 程式中斷
			}

			/*************************** 3.新增完成,準備轉交(Send the Success view) ***********/
			String url = "/back-staff-end/staff/insertStaff.jsp";
			response.sendRedirect(request.getContextPath() + url);
			// 新增成功後轉交/back-staff-end/staff/insertStaff.jsp

		}

		if ("delete".equals(action)) { // 來自listAllStaff.jsp

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			request.setAttribute("errorMsgs", errorMsgs);

			/*************************** 1.接收請求參數 ***************************************/
			Integer staffNumber = Integer.valueOf(request.getParameter("staffNumber"));

			/*************************** 2.開始刪除資料 ***************************************/
			StaffService staffSvc = new StaffService();
			staffSvc.deleteStaff(staffNumber);

//			JOptionPane optionPane = new JOptionPane("勝敗乃兵家常事，大俠如果還需要此員工，請再新增一次！" );
//			JDialog dialog = optionPane.createDialog("Tips");
//			dialog.setAlwaysOnTop(true); // to show top of all other application
//			dialog.setVisible(true); // to visible the dialog

			/*************************** 3.刪除完成,準備轉交(Send the Success view) ***********/
			String url = "/back-staff-end/staff/listAllStaff.jsp";
			RequestDispatcher successView = request.getRequestDispatcher(url);// 刪除成功後,轉交回送出刪除的來源網頁
			successView.forward(request, response);
		}

		if ("update".equals(action)) { // 來自updateStaff.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			request.setAttribute("errorMsgs", errorMsgs);

			/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 **********************/
			Integer staffNumber = Integer.valueOf(request.getParameter("staffNumber").trim());
//			System.out.println("staffNumber="+staffNumber);
			String staffName = request.getParameter("staffName");
			String staffNameReg = "^[(\u4e00-\u9fa5)(a-zA-Z0-9_)]{2,10}$";

			if (staffName == null || staffName.trim().length() == 0) {
				errorMsgs.add("員工姓名: 請勿空白");
			} else if (!staffName.trim().matches(staffNameReg)) { // 以下練習正則(規)表示式(regular-expression)
				errorMsgs.add("員工姓名: 只能是中、英文字母 , 且長度必需在2到10之間");
			}

			String staffAccount = request.getParameter("staffAccount");
			String staffAccountReg = "^[a-zA-Z_0-9]{6-20}$";
			if (staffAccount == null || staffAccount.trim().length() == 0) {
				errorMsgs.add("員工帳號: 請勿空白");
			}

			String staffPassword = request.getParameter("staffPassword");
			String staffPasswordReg = "^(?=.*d)(?=.*d)(?=.*[a-zA-Z])[da-zA-Z]{6-20}$";
			// 密碼中間同時含字母和數字且長度在6~20之間
			if (staffPassword == null || staffPassword.trim().length() == 0) {
				errorMsgs.add("員工密碼: 請勿空白");
			}

			StaffVO staffVO = new StaffVO();
			staffVO.setStaffNumber(staffNumber);
			staffVO.setStaffName(staffName);
			staffVO.setStaffAccount(staffAccount);
			staffVO.setStaffPassword(staffPassword);
//			System.out.println(staffVO.toString());

			StaffService staffSvc = new StaffService();
			StaffVO staffVO2 = staffSvc.getOneByAccount(staffAccount);
			
			if (staffVO2 != null) {
				if((staffVO2.getStaffNumber() == staffNumber) && (staffAccount.equals(staffVO2.getStaffAccount()))) {
					
				}else {
					errorMsgs.add("員工帳號重複，請重新輸入帳號");
				};
			};

			// Send the use back to the form, if there were errors
			if (!errorMsgs.isEmpty()) {
				request.setAttribute("staffVO", staffVO); // 含有輸入格式錯誤的staffVO物件,也存入request
				System.out.println("3" + staffVO);
				RequestDispatcher failureView = request.getRequestDispatcher("/back-staff-end/staff/updateStaff.jsp");
				failureView.forward(request, response);
				return; // 程式中斷
			}

			/*************************** 2.開始修改資料 *****************************************/
			StaffService staffSvc2 = new StaffService();
//			System.out.println("update @ 2.開始修改資料");
			staffVO = staffSvc2.updateStaff(staffNumber, staffName, staffAccount, staffPassword);
//			System.out.println("update @ updateStaff執行完畢");
			/*************************** 3.修改完成,準備轉交(Send the Success view) *************/
			request.setAttribute("staffVO", staffVO); // 資料庫update成功後,正確的的staffVO物件,存入request
			String url = "/back-staff-end/staff/listAllStaff.jsp";
			RequestDispatcher successView = request.getRequestDispatcher(url);
			// 修改成功後,轉交/back-staff-end/staff/listAllStaff.jsp
			successView.forward(request, response);
		}

		// =============================員工登入=============================//

		if ("loginForStaff".equals(action)) {
			List<String> errorMsgs = new LinkedList<>();
			request.setAttribute("errorMsgs", errorMsgs);

			try {

				StaffVO staffVO = new StaffVO();
				StaffService staffSvc = new StaffService();

				// 確認輸入的值
				String staffAccount = request.getParameter("staffAccount");
				if (staffAccount == null || staffAccount.trim().length() == 0) {
					errorMsgs.add("請輸入帳號");
				}
				System.out.println("使用者輸入的帳號: " + staffAccount); // 使用者輸入的帳號

				String staffPassword = request.getParameter("staffPassword");
				if (staffPassword == null || staffPassword.trim().length() == 0) {
					errorMsgs.add("請輸入密碼");
				}
				System.out.println("使用者輸入的密碼: " + staffPassword); // 使用者輸入的密碼
				// 設定UserService傳入資訊

				staffVO = staffSvc.findByStaffAccount(staffAccount);
				String tickitPwd = staffSvc.pwd(staffPassword);

				if (staffVO == null) {
					errorMsgs.add("帳號或密碼錯誤");
				}

				String PasswordCheck = staffVO.getStaffPassword();
				if (!PasswordCheck.equals(tickitPwd)) {
					errorMsgs.add("帳號或密碼錯誤");
				}

				// 確認資料有誤，印出錯誤資料並跳回原頁
				if (!errorMsgs.isEmpty()) {
					session.setAttribute("staffVO", staffVO);
					RequestDispatcher failureView = request
							.getRequestDispatcher("/back-staff-end/staff/loginStaff.jsp");
					failureView.forward(request, response);
					return;
				}

				// 確認資料無誤，則設定
				session.setAttribute("staffVO", staffVO);
				System.out.println("be login...");
				String location = (String) session.getAttribute("location"); // 看看有無來源網頁

				if (location != null) { // 代表有來源網頁
					session.removeAttribute("location"); // 有來源網頁:重導至來源網頁
					response.sendRedirect(location);
					return;
				}
				RequestDispatcher successView = request.getRequestDispatcher("/main_frame/index_Staff.jsp");
				successView.forward(request, response);
				return;

			} catch (Exception e) {
				e.printStackTrace();
				RequestDispatcher failureView = request.getRequestDispatcher("/back-staff-end/staff/loginStaff.jsp");
				failureView.forward(request, response);
			}
		}
		// ===================================================員工登出=========================================================//

		if ("logout".equals(action)) {
			try {
				System.out.println("into logout");
				session.removeAttribute("staffVO");

				session.invalidate(); // 清除用戶端與伺服器之間的會話資料

				// 新增完成，準備轉交
				String url = "/back-staff-end/staff/loginStaff.jsp";
				RequestDispatcher successView = request.getRequestDispatcher(url);
				successView.forward(request, response);

			} catch (Exception e) {
				e.getStackTrace();
				RequestDispatcher failureView = request.getRequestDispatcher("index.jsp");
				failureView.forward(request, response);
			}

		}

	}

}
