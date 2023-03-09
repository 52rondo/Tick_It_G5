package tw.com.tibame.event.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import tw.com.tibame.event.model.EventClassService;
import tw.com.tibame.event.model.EventService;
import tw.com.tibame.event.model.EventVO;
import tw.com.tibame.event.model.OrderService;
import tw.com.tibame.event.model.TicketService;
import tw.com.tibame.event.model.TicketVO;


@WebServlet("/EventDetails")
public class EventDetails extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		System.out.println("controller event deatils got request");
		String eventNumber = request.getParameter("eventNumber");
		System.out.println(eventNumber);
		if(eventNumber != null) {
			//event
	    	EventService eventService = new EventService();
	    	EventVO event = eventService.getOneEvent(Integer.parseInt(eventNumber));
	    	Base64.Encoder encoder = Base64.getEncoder();
	    	String bigImg64 = "data:image/jpeg;base64,"+encoder.encodeToString(event.getBigImg());
	    	request.setAttribute("bigImg64", bigImg64);
	    	request.setAttribute("event", event);
	    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    	request.setAttribute("eventStart", sdf.format(event.getEventStartDate()));
	    	request.setAttribute("eventEnd", sdf.format(event.getEventEndDate()));
	    	//organizer name
	    	request.setAttribute("orgName", eventService.getOrgName(event.getOrganizerNumber()));
	    	//event class name
	    	EventClassService eventClassService = new EventClassService();
	    	List<String> nameList =eventClassService.getEvnClassName(event.getEventNumber());
	    	request.setAttribute("eventClassName", nameList);
	    	//ticket list
	    	TicketService ticketservice = new TicketService();
	    	List<TicketVO> ticketlist =ticketservice.selectTicketByEventNumber(event.getEventNumber());
	    	request.setAttribute("ticketlist", ticketlist);
	    	
	    	
	        //跳轉頁面進入，清空session
	        HttpSession session = request.getSession();
	        session.removeAttribute("selectEventInfo");
	    }
		
		request.getRequestDispatcher("/front-end/event/eventDetails.jsp").forward(request, response);
	}

}
