package tw.com.tibame.event.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.crypto.SecretKey;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;
import com.google.zxing.EncodeHintType;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import tw.com.tibame.event.model.ECPayService;
import tw.com.tibame.event.model.EventService;
import tw.com.tibame.event.model.EventVO;
import tw.com.tibame.event.model.OrderService;
import tw.com.tibame.event.model.OrderVO;
import tw.com.tibame.event.model.SeatService;
import tw.com.tibame.event.model.SeatVO;
import tw.com.tibame.event.model.SoldTicketsVO;
import tw.com.tibame.event.model.TicketVO;
import tw.com.tibame.member.model.MemberVO;

@WebServlet("/FrontendEventAjaxServlet")
public class FrontendEventAjaxServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private DateTimeFormatter dft = DateTimeFormatter.ofPattern("yyyyMMdd HHmmss");

//
//	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//	
//	}

	@SuppressWarnings("unchecked")
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	    
	    
	    String action = request.getParameter("action");
	    
	    HttpSession session = request.getSession();
	    
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        
        Gson gson = new Gson();
        OrderService orderService = new OrderService();
        
        Map<String,Object> result = new HashMap<>();
        result.put("success", false);
        
        if(action != null && ! action.equals("")) {
            String eventNumberStr = "";
            if("selectAllEvent".equals(action)) {
                EventService eventService = new EventService();
                List<Map<String,Object>> eventList = eventService.findAllForDebug();
                result.put("eventList", eventList);
                result.put("success", true);
                
            }else if("getEventTickets".equals(action)) {
                eventNumberStr = request.getParameter("eventNumber");
                if(eventNumberStr != null && ! eventNumberStr.equals("")) {
                    List<TicketVO> voList = orderService.queryTicketByEventNumber(Integer.parseInt(eventNumberStr));
                    result.put("voList", voList);
                    result.put("success", true);
                }
            }else if("confirmTicket".equals(action)) {
                
                
                eventNumberStr = request.getParameter("eventNumber");
                //??????????????????
                MemberVO memberProfile = (MemberVO)session.getAttribute("memberVO");
                if(memberProfile == null) {
                    result.put("success", false);
                    result.put("needLogin", true);
                    session.setAttribute("location", request.getContextPath() + "/FrontendEventServlet?eventNumber=" + eventNumberStr);
                    PrintWriter out = response.getWriter();
                    out.print(gson.toJson(result));
                    out.flush();
                    return;
                }
                
                
                int eventNumber = Integer.parseInt(eventNumberStr);
                String ticketSelectStr = request.getParameter("ticketSelect");
                System.out.println(String.format("ticketSelectStr:%s", ticketSelectStr));
                List<Map<String,Object>> ticketSelect = (List<Map<String,Object>>)gson.fromJson(ticketSelectStr, List.class);
                System.out.println(String.format("selectTicket:%s", ticketSelect));
                
                //???????????????????????????
                result = orderService.checkTicket(eventNumber, ticketSelect);
                
                if("true".equals(String.valueOf(result.get("success")))) {
                    //????????????????????????????????????????????????????????????????????????????????????
                    EventVO eventvo = orderService.queryEventByEventNumber(eventNumber);
                    
                    //????????????
                    int totalPrice = 0;
                    for(Map<String,Object> t : ticketSelect) {
                        Integer price = Integer.parseInt(String.valueOf(t.get("price")));
                        Integer count = Integer.parseInt(String.valueOf(t.get("val")));
                        
                        totalPrice += ( price * count );
                    }
                    
                    //??????????????? session
                    Map<String,Object> selectEventInfo = new HashMap<>();
                    if(session.getAttribute("selectEventInfo") != null) {
                        selectEventInfo = (Map<String,Object>)session.getAttribute("selectEventInfo");
                        
                    }
                    
                    //selectEventInfo.put("time", dft.format(LocalDateTime.now()) );
                    selectEventInfo.put("ticketSelect", ticketSelect);
                    selectEventInfo.put("eventNumber", eventvo.getEventNumber());
                    selectEventInfo.put("needSeat", eventvo.getNeedSeat());
                    selectEventInfo.put("totalPrice", totalPrice);
                    //????????????????????????????????????????????????(????????????????????????)
                    //TODO
                    
                    //????????????
                    orderService.createOrUpdateOrder(selectEventInfo,memberProfile);
                    //??????selectSeat
                    selectEventInfo.remove("selectSeat");
                    session.setAttribute("selectEventInfo", selectEventInfo);
                    
                    result.put("selectEventInfo", selectEventInfo);
                    
                    
                }
            }else if("checkStep".equals(action)) {
                
                Map<String,Object> selectEventInfo = null;
                if(session.getAttribute("selectEventInfo") != null) {
                    selectEventInfo = (Map<String,Object>)session.getAttribute("selectEventInfo");
                    result.put("selectEventInfo", selectEventInfo);
                }
                
                //????????????????????????1
                if(selectEventInfo == null) {
                    System.out.println("selectEventInfo is null");
                    result.put("step", 1);
                }else {
                    
                    
                    if(selectEventInfo.get("eventNumber") == null) {
                        System.out.println("event is null");
                        //??????????????????????????????+??????
                        result.put("step", 1);
                    }else {
                        Integer eventNumber = Integer.parseInt(String.valueOf(selectEventInfo.get("eventNumber")));
                        EventVO eventvo = orderService.queryEventByEventNumber(eventNumber);
                        List<Map<String,Object>> ticketSelect = (List<Map<String,Object>>) selectEventInfo.get("ticketSelect");
                        if(ticketSelect == null) {
                            //????????????????????????
                            System.out.println("ticketSelect is null");
                            result.put("step", 1);
                        }else if(eventvo.getNeedSeat() && selectEventInfo.get("seatInfo") == null ) {
                            System.out.println("needSeeat , seatInfo is null");
                            //??????????????????????????????????????????
                            result.put("step", 2);
                        }else {
                            //??????????????????
                            result.put("step", 3);
                        }
                    }
                    
                }
                
                result.put("success", true);
                
            }else if("getSeat".equals(action)) {
                
                eventNumberStr = request.getParameter("eventNumber");
                //??????????????????
                MemberVO memberProfile = (MemberVO)session.getAttribute("memberVO");
                if(memberProfile == null) {
                    result.put("success", false);
                    result.put("needLogin", true);
                    session.setAttribute("location", request.getContextPath() + "/FrontendEventServlet?eventNumber=" + eventNumberStr);
                    PrintWriter out = response.getWriter();
                    out.print(gson.toJson(result));
                    out.flush();
                    return;
                }
                
                Map<String,Object> selectEventInfo = (Map<String,Object>)session.getAttribute("selectEventInfo");
                if(selectEventInfo == null) {
                    result.put("success", false);
                    result.put("noSession", true);
                }
                
                result.put("selectEventInfo", selectEventInfo);
                
                int eventNumber = Integer.parseInt(eventNumberStr);
                EventVO eventvo = orderService.queryEventByEventNumber(eventNumber);
                SeatService seatService = new SeatService();
                List<SeatVO> seatList = seatService.selectSeatByEventNumber(eventNumber);
                
                result.put("seatData", seatList);
                result.put("eventInfo", eventvo);
                result.put("success", true);
                
            }else if("confirmSeat".equals(action)) {
                eventNumberStr = request.getParameter("eventNumber");
                //??????????????????
                MemberVO memberProfile = (MemberVO)session.getAttribute("memberVO");
                if(memberProfile == null) {
                    result.put("success", false);
                    result.put("needLogin", true);
                    PrintWriter out = response.getWriter();
                    session.setAttribute("location", request.getContextPath() + "/FrontendEventServlet?eventNumber=" + eventNumberStr);
                    out.print(gson.toJson(result));
                    out.flush();
                    return;
                }
                
                Map<String,Object> selectEventInfo = (Map<String,Object>)session.getAttribute("selectEventInfo");
                if(selectEventInfo == null) {
                    result.put("success", false);
                    result.put("noSession", true);
                }
                
                
                String selectSeatStr = request.getParameter("selectSeat");
                int eventNumber = Integer.parseInt(eventNumberStr);
                
                List<Integer> selectSeat = gson.fromJson(selectSeatStr, List.class);
                
                List<Integer> occupySeats = orderService.checkOccupySeat(eventNumber, selectSeat ,selectEventInfo);
                
                if(occupySeats.size() > 0) {
                    result.put("occupySeats", occupySeats);
                }else {
                    //??????session
                    
                    selectEventInfo.put("time", dft.format(LocalDateTime.now()) );
                    selectEventInfo.put("selectSeat", selectSeat);
                    
                    orderService.updateSeat(selectEventInfo);
                    
                    session.setAttribute("selectEventInfo",selectEventInfo);
                    result.put("selectEventInfo", selectEventInfo);
                    result.put("success", true);
                }
                
                
            }else if("getSelectEventInfo".equals(action)) {
                
                Map<String,Object> selectEventInfo = (Map<String,Object>)session.getAttribute("selectEventInfo");
                if(selectEventInfo == null) {
                    result.put("success", false);
                    result.put("noSession", true);
                }
                result.put("selectEventInfo", selectEventInfo);
                result.put("success", true);
                
            }else if("getUserData".equals(action)) {
                
                //??????????????????
                MemberVO memberProfile = (MemberVO)session.getAttribute("memberVO");
                if(memberProfile == null) {
                    result.put("success", false);
                    result.put("needLogin", true);
                    //session.setAttribute("location", "/FrontendEventServlet?eventNumber=" + eventNumberStr);
                    PrintWriter out = response.getWriter();
                    out.print(gson.toJson(result));
                    out.flush();
                    return;
                }
                if(memberProfile.getName() != null) {
                    result.put("inputName", memberProfile.getName());
                }else {
                    result.put("inputName", "");
                }
                if(memberProfile.getEmail() != null) {
                    result.put("inputEmail", memberProfile.getEmail());
                }else {
                    result.put("inputEmail", "");
                }
                if(memberProfile.getIDNumber() != null) {
                    result.put("inputRocid", memberProfile.getIDNumber());
                }else {
                    result.put("inputRocid", "");
                }
                if(memberProfile.getPhoneNumber() != null) {
                    result.put("inputPhone", memberProfile.getPhoneNumber());
                }else {
                    result.put("inputPhone", "");
                }
               
                result.put("success", true);
                
            }else if("confirmUserData".equals(action)) {
                
                Map<String,Object> selectEventInfo = (Map<String,Object>)session.getAttribute("selectEventInfo");
                if(selectEventInfo == null) {
                    result.put("success", false);
                    result.put("noSession", true);
                }
                
                //??????????????????
                MemberVO memberProfile = (MemberVO)session.getAttribute("memberVO");
                if(memberProfile == null) {
                    result.put("success", false);
                    result.put("needLogin", true);
                    session.setAttribute("location", request.getContextPath() + "/FrontendEventServlet?eventNumber=" + selectEventInfo.get("eventNumber"));
                    PrintWriter out = response.getWriter();
                    out.print(gson.toJson(result));
                    out.flush();
                    return;
                }
                
                Map<String,Object> userData = new HashMap<>();
                userData.put("inputName", request.getParameter("inputName"));
                userData.put("inputEmail", request.getParameter("inputEmail"));
                userData.put("inputPhone", request.getParameter("inputPhone"));
                userData.put("inputRocid", request.getParameter("inputRocid"));
                selectEventInfo.put("userData", userData);
                
                orderService.updateUserData(selectEventInfo);
                
                
                session.setAttribute("selectEventInfo", selectEventInfo);
                
                result.put("selectEventInfo", selectEventInfo);
                
                result.put("returnUrl", "http://127.0.0.1:8080/TGA104G5");
                
                //?????????????????????????????????????????????????????????????????????
//                String httpPath =request.getScheme() + "://" + request.getServerName() + request.getServerPort() + request.getContextPath();
//                System.out.println(httpPath);
//                String callbackUrl = String.format(httpPath+"/FrontendEventOrderProcessServlet?return=%s_tickit",
//                        String.valueOf(selectEventInfo.get("orderId")));
                String callbackUrl = String.format("http://localhost:8080/TGA104G5/FrontendEventOrderProcessServlet?return=%s_tickit",
                        String.valueOf(selectEventInfo.get("orderId")));
                result.put("callbackUrl", callbackUrl);
                
                
                result.put("success", true);
            }else if("confirmUserDataForFinish".equals(action)) {
                
                String orderIdStr = request.getParameter("orderId");
                //OrderVO order = orderService.queryOrderById(Integer.parseInt(orderIdStr));
                //??????????????????
                MemberVO memberProfile = (MemberVO)session.getAttribute("memberVO");
                if(memberProfile == null) {
                    result.put("success", false);
                    result.put("needLogin", true);
                    session.setAttribute("location", request.getContextPath() + "/FrontendEventOrderProcessServlet?return=" + orderIdStr);
                    PrintWriter out = response.getWriter();
                    out.print(gson.toJson(result));
                    out.flush();
                    return;
                }
                
                Map<String,Object> userData = new HashMap<>();
                userData.put("inputName", request.getParameter("inputName"));
                userData.put("inputEmail", request.getParameter("inputEmail"));
                userData.put("inputPhone", request.getParameter("inputPhone"));
                userData.put("inputRocid", request.getParameter("inputRocid"));
                
                orderService.updateUserDataForFinish(Integer.parseInt(orderIdStr)  ,userData);
                
                result.put("success", true);
                
                result.put("userData", userData);
                
            }else if("cancelOrder".equals(action)) {
                String orderIdStr = request.getParameter("orderId");
                result = orderService.doCancelOrder(Integer.parseInt(orderIdStr));
            }else if("change256".equals(action)) {
            	  //????????????????????? ??????
            	String callbackUrl=request.getParameter("callbackUrl");
            	String ItemName=request.getParameter("ItemName");
            	String MerchantTradeDate=request.getParameter("MerchantTradeDate");
            	String ranDom=request.getParameter("ranDom");
            	String returnUrl=request.getParameter("returnUrl");
            	String totalPrice=request.getParameter("totalPrice");
            	String TradeDesc=request.getParameter("TradeDesc");
            	String checkVis = "HashKey=pwFHCqoQZGmho4w6&ChoosePayment=ALL&ClientBackURL="+callbackUrl+"&EncryptType=1&ItemName="+ItemName+"&MerchantID=3002607&MerchantTradeDate="+MerchantTradeDate+"&MerchantTradeNo="+ranDom+"&PaymentType=aio&ReturnURL="+returnUrl+"&TotalAmount="+totalPrice+"&TradeDesc="+TradeDesc+"&HashIV=EkRm7iFT261dpevs";
                ECPayService ecSvc = new ECPayService();
                String digest=ecSvc.genCheckMacValue(checkVis);
//                System.out.println("digest="+digest);
                result.put("digest", digest);
            }
            
        }
	    
	    
	    PrintWriter out = response.getWriter();
        out.print(gson.toJson(result));
        out.flush();
	}

}
