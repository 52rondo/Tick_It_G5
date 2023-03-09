package tw.com.tibame.event.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;

import tw.com.tibame.event.model.EventService;
import tw.com.tibame.event.model.EventVO;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@WebServlet("/AddEvent2Servlet")
public class AddEvent2Servlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	//insert page 3 use
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        
        HttpSession session = request.getSession();
        
		String action = request.getParameter("action");
		
		Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
		Map<String,Object> result = new HashMap<>();
        result.put("success", false);
        
        if("save".equals(action)) {
        	String seatIdList  = request.getParameter("seatIdList");
			if (seatIdList == null || seatIdList.trim().length() == 0) {
				result.put("seatIdList","座位設定有異");
			}
			
			String xVal=request.getParameter("xVal");
			Integer xval = null;
			try {
				xval = Integer.valueOf(xVal);
			}catch (NumberFormatException e) {
				result.put("xValmsg","只能是數字");
				result.put("xVal",xVal);
			}
			
			String yVal=request.getParameter("yVal");
			Integer yval = null;
			try {
				yval = Integer.valueOf(yVal);
			}catch (NumberFormatException e) {
				result.put("yValmsg","只能是數字");
				result.put("yVal",yVal);
			}
        	
//	        System.out.println("seatIdList:" + seatIdList);
//	        System.out.println("xVal:" + xval);
//	        System.out.println("yVal:" + yval);
	        
	        String[] seatIdListary = seatIdList.split(",");
	        
	        
			if(result.size() > 1) {
				
				System.out.println("go error "+result.size());
			    PrintWriter out = response.getWriter();
		        out.print(gson.toJson(result));
		        out.flush();
		        return;
				
			}else{
				System.out.println("in suc true"+result.size());
				result.put("success", true);
			}
			
			String eventNumber=request.getParameter("eventNumber");
			System.out.println("eventNumber="+eventNumber);
			if("".equals(eventNumber)) {
//				System.out.println("adddata="+((Map)session.getAttribute("adddata")).toString());
//				System.out.println(session.getAttribute("tickets").toString());
				//get page1 data
				Map eventMap =(Map)session.getAttribute("adddata");
				EventVO eventvo = (EventVO)eventMap.get("page1");
				String[] event_class=(String[]) eventMap.get("event_class");
				//get page2 data
				String tickets=session.getAttribute("tickets").toString();
				
				// call service insert data
				EventService eventSvc = new EventService();
				int eventInsertOK = eventSvc.addEvent(eventvo,event_class,tickets,xval,yval,seatIdListary);
				result.put("insertOK",eventInsertOK);
				session.removeAttribute("adddata");
				session.removeAttribute("maxDate");
				session.removeAttribute("tickets");
			}else {
//				System.out.println("up_adddata="+((Map)session.getAttribute("up_adddata")).toString());
//				System.out.println(session.getAttribute("up_tickets").toString());
				//get page1 data
				Map eventMap =(Map)session.getAttribute("up_adddata");
				EventVO eventvo = (EventVO)eventMap.get("page1");
				String[] event_class=(String[]) eventMap.get("event_class");
				//get page2 data
				String tickets=session.getAttribute("up_tickets").toString();
				
				// call service insert data
				EventService eventSvc = new EventService();
				int eventInsertOK = eventSvc.updateEvent(eventvo,event_class,tickets,xval,yval,seatIdListary);
				result.put("insertOK",eventInsertOK);
				session.removeAttribute("up_eventvo");
				session.removeAttribute("up_adddata");
				session.removeAttribute("up_maxDate");
				session.removeAttribute("up_tickets");
			}
			
        }
        
	    PrintWriter out = response.getWriter();
        out.print(gson.toJson(result));
        out.flush();
	}
}
