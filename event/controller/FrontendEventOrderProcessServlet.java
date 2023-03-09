package tw.com.tibame.event.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONObject;

import com.google.gson.Gson;

import tw.com.tibame.event.model.EventVO;
import tw.com.tibame.event.model.MailService;
import tw.com.tibame.event.model.OrderService;
import tw.com.tibame.event.model.OrderVO;
import tw.com.tibame.member.model.MemberVO;


@WebServlet("/FrontendEventOrderProcessServlet")
public class FrontendEventOrderProcessServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private OrderService orderService = new OrderService();
	
       
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Gson gson = new Gson();
		
		 HttpSession session = request.getSession();
	    //自金流平台繳費專用處理
	    if(request.getParameter("return") != null) {
	        
            String returnVal = request.getParameter("return");
            
            MemberVO memberProfile = (MemberVO)session.getAttribute("memberVO");
            if(memberProfile == null) {
                session.setAttribute("location", request.getContextPath() + "/FrontendEventOrderProcessServlet?return=" + returnVal);
                response.sendRedirect(request.getContextPath() + "/front-end/member/memberLogin.jsp");
                return;
            }
            
            String[] returnAry = returnVal.split("_");
            String orderId = returnAry[0];
            OrderVO order = orderService.queryOrderById(Integer.parseInt(orderId));
            
            if("已取消".equals(order.getOrderType())) {
                request.setAttribute("orderCancel", true);
            }else {
            	EventVO event = orderService.queryEventByEventNumber(order.getEventNumber());
                String orgName = orderService.getOrgName(event.getOrganizerNumber());
                request.setAttribute("orgName", orgName);
                request.setAttribute("order", order);
                request.setAttribute("event", event);
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                request.setAttribute("eventStart", sdf.format(event.getEventStartDate()));
                request.setAttribute("eventEnd", sdf.format(event.getEventEndDate()));
                
                List<Map<String,String>> qrList = orderService.getOrderTicketQRCode(Integer.parseInt(orderId));
                try {
                    
                    
                    if(returnAry.length == 2) {
                        String tradeCode = returnAry[1];
                        
                        JSONObject userData = new JSONObject(order.getpData());
                        
                        if (tradeCode != null ) {
                            orderService.changeOrderType(Integer.parseInt(orderId), "已繳費");
//                            String httpPath =request.getScheme()+ "://" + request.getServerName() + request.getServerPort() + request.getContextPath() +request.getServletPath();
//                            System.out.println(httpPath);
                            MailService mailservice = new MailService();
//                            mailservice.sendMail(userData.getString("inputEmail"), "Tickit 活動訂單完成通知"
//                                    ,String.format("您的訂單編號%s已經繳費完成，請點選%s確認訂單訂購結果與取得QR CODE。"
//                                            ,orderId
//                                            ,"<a href=\""+ httpPath+"?return="+orderId+"\">此連結</a>"
//                                            ));
                            mailservice.sendMail(userData.getString("inputEmail"), "Tickit 活動訂單完成通知"
                                    ,String.format("您的訂單編號%s已經繳費完成，請點選%s確認訂單訂購結果與取得QR CODE。"
                                            ,orderId
                                            ,"<a href=\"http://127.0.0.1:8080/TGA104G5/FrontendEventOrderProcessServlet?return="+orderId+"\">此連結</a>"
                                            ));
                            
                        }
                    }
                    
                    
                } catch (Exception e) {
                    e.printStackTrace();
                }
                
                //輸入訂單完成資料
                String userData = order.getpData();
                Map<String,Object> userDataMap = gson.fromJson(userData, Map.class);
                
                request.setAttribute("orderId",orderId);
                
                request.setAttribute("inputName", String.valueOf(userDataMap.get("inputName")));
                //inputEmail
                request.setAttribute("inputEmail", String.valueOf(userDataMap.get("inputEmail")));
                //inputPhone
                request.setAttribute("inputPhone", String.valueOf(userDataMap.get("inputPhone")));
                //inputRocid
                request.setAttribute("inputRocid", String.valueOf(userDataMap.get("inputRocid")));
                
                request.setAttribute("qrList", qrList);
                
            }
            
            request.getRequestDispatcher("/front-end/event/finishEventOrder.jsp").forward(request, response);
            
        }else {
            response.sendRedirect("/");
        }
	    
	}


//	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		// TODO Auto-generated method stub
//		doGet(request, response);
//	}

}
