package tw.com.tibame.event.model;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class EventService {
	private EventDAO_interface dao;
	public EventService() {
		dao = new EventJDBCDAO();
	}
	
	public int addEvent(EventVO eventvo,String[] event_classArr,String tickets) { //no seat
		int re = addEvent(eventvo,event_classArr,tickets,0,0,null);
		return re;
	}
	public int addEvent(EventVO eventvo,String[] event_classArr,String tickets,Integer xVal,Integer yVal,String[] seatIdListary) {
		//event table
		eventvo.setCollectType(false);
		eventvo.setSeatX(xVal);
		eventvo.setSeatY(yVal);
		//未開賣 販售中 已售罊(新增無需使用) 已結束
		Timestamp start=eventvo.getEventStartDate();
		Timestamp end=eventvo.getEventEndDate();
		Date toDay = new Date();

		if(start.compareTo(toDay) > 0) {
			eventvo.setEventType("未開賣");
		}else if(start.compareTo(toDay) <= 0 && end.compareTo(toDay) > 0) {
			eventvo.setEventType("販售中");
		}else {
			eventvo.setEventType("已結束");
		}
//		System.out.println("service eventvo = "+eventvo.toString());
		//ticket table
		JSONObject ticketsJSON = new JSONObject(tickets);
		List<TicketVO> ticketlist = new ArrayList<TicketVO>();
		JSONArray ticket = ticketsJSON.getJSONArray("ticket");
		for(int i =0 ; i < ticket.length() ; i++) {
			JSONObject oneticket = ticket.getJSONObject(i);
			TicketVO ticketvo = new TicketVO();
			ticketvo.setTicketName(oneticket.getString("ticket_name"));
			ticketvo.setPrice(Integer.valueOf(oneticket.getString("ticket_price")));
			ticketvo.setTicketQuantity(Integer.valueOf(oneticket.getString("ticket_quantity")));
			ticketvo.setTicketStartTime(Timestamp.valueOf(oneticket.getString("start_date")));
			ticketvo.setTicketEndTime(Timestamp.valueOf(oneticket.getString("end_date")));
			ticketvo.setTicketMIN(Integer.valueOf(oneticket.getString("ticket_min")));
			ticketvo.setTicketMAX(Integer.valueOf(oneticket.getString("ticket_max")));
			ticketvo.setLimitTicket(true);
			//未開賣 販售中 已售罊 已結束
			Timestamp startticket=ticketvo.getTicketStartTime();
			Timestamp endticket=ticketvo.getTicketEndTime();
			
			if(startticket.compareTo(toDay) > 0) {
				ticketvo.setTicketType("未開賣");
			}else if(startticket.compareTo(toDay) <=0 && endticket.compareTo(toDay) > 0) {
				ticketvo.setTicketType("販售中");
			}else {
				ticketvo.setTicketType("已結束");
			}
			ticketlist.add(ticketvo);
		}
		
		//event class table
		List<EventClassVO> eventclasslist = new ArrayList<EventClassVO>();
		for(int i=0; i < event_classArr.length; i++) {
			EventClassVO eventclassvo = new EventClassVO();
			eventclassvo.setEventClassNumber(Integer.valueOf(event_classArr[i]));
			eventclasslist.add(eventclassvo);
		}
		
		//seat table
		List<SeatVO> seatlist = new ArrayList<SeatVO>();
		if(eventvo.getNeedSeat()) {
			for(int i=0; i < seatIdListary.length ; i++) {
				SeatVO seatvo = new SeatVO();
				seatvo.setSeatNumber(i+1);
				seatvo.setSeatSet(Integer.valueOf(seatIdListary[i]));
				seatlist.add(seatvo);
			}
		}
		
		int re =dao.insert(eventvo,ticketlist,eventclasslist,seatlist);
		
		return re;
	}
	
	public EventVO getOneEvent(Integer eventNumber) {
		return dao.selectByeventNumber(eventNumber);
	}
	
	public int updateEvent(EventVO eventvo,String[] event_classArr,String tickets) { //no seat
		int re = updateEvent(eventvo,event_classArr,tickets,0,0,null);
		return re;
	}
	public int updateEvent(EventVO eventvo,String[] event_classArr,String tickets,Integer xVal,Integer yVal,String[] seatIdListary) {
		//event table
		eventvo.setCollectType(false);
		eventvo.setSeatX(xVal);
		eventvo.setSeatY(yVal);
		//未開賣 販售中 已售罊(新增無需使用) 已結束
		Timestamp start=eventvo.getEventStartDate();
		Timestamp end=eventvo.getEventEndDate();
		Date toDay = new Date();

		if(start.compareTo(toDay) > 0) {
			eventvo.setEventType("未開賣");
		}else if(start.compareTo(toDay) <= 0 && end.compareTo(toDay) > 0) {
			eventvo.setEventType("販售中");
		}else {
			eventvo.setEventType("已結束");
		}
//		System.out.println("service eventvo = "+eventvo.toString());
		
		//ticket table
		JSONObject ticketsJSON = new JSONObject(tickets);
		List<TicketVO> ticketlist = new ArrayList<TicketVO>();
		JSONArray ticket = ticketsJSON.getJSONArray("ticket");
		for(int i =0 ; i < ticket.length() ; i++) {
			JSONObject oneticket = ticket.getJSONObject(i);
			TicketVO ticketvo = new TicketVO();
			
			Integer ticketID=oneticket.getInt("ticket_ID");
			if(ticketID > 0) {
				ticketvo.setTicketID(ticketID);
			}

			ticketvo.setTicketName(oneticket.getString("ticket_name"));
			
			Integer eventNumber=oneticket.getInt("eventNumber");
			if(eventNumber > 0) {
				ticketvo.setEventNumber(eventNumber);
			}
			ticketvo.setPrice(Integer.valueOf(oneticket.get("ticket_price").toString()));
			ticketvo.setTicketQuantity(Integer.valueOf(oneticket.get("ticket_quantity").toString()));
			ticketvo.setTicketStartTime(Timestamp.valueOf(oneticket.get("start_date").toString()));
			ticketvo.setTicketEndTime(Timestamp.valueOf(oneticket.get("end_date").toString()));
			ticketvo.setTicketMIN(Integer.valueOf(oneticket.get("ticket_min").toString()));
			ticketvo.setTicketMAX(Integer.valueOf(oneticket.get("ticket_max").toString()));
			ticketvo.setLimitTicket(true);
			//未開賣 販售中 已售罊 已結束 已下架
			Timestamp startticket=ticketvo.getTicketStartTime();
			Timestamp endticket=ticketvo.getTicketEndTime();
//			System.out.println(oneticket.getInt("record"));
			if(oneticket.getInt("record") == -1) {
				ticketvo.setTicketType("已下架");
			}else {
				if(startticket.compareTo(toDay) > 0) {
					ticketvo.setTicketType("未開賣");
				}else if(startticket.compareTo(toDay) <=0 && endticket.compareTo(toDay) > 0) {
					ticketvo.setTicketType("販售中");
				}else {
					ticketvo.setTicketType("已結束");
				}
			}
			
			ticketlist.add(ticketvo);
		}
//		System.out.println("ticketlist="+ticketlist.toString());
		
		//event class table
		List<EventClassVO> eventclasslist = new ArrayList<EventClassVO>();
		for(int i=0; i < event_classArr.length; i++) {
			EventClassVO eventclassvo = new EventClassVO();
			eventclassvo.setEventClassNumber(Integer.valueOf(event_classArr[i]));
			eventclasslist.add(eventclassvo);
		}
		
		//seat table
		List<SeatVO> seatlist = new ArrayList<SeatVO>();
		if(eventvo.getNeedSeat()) {
			for(int i=0; i < seatIdListary.length ; i++) {
				SeatVO seatvo = new SeatVO();
				seatvo.setSeatNumber(i+1);
				seatvo.setSeatSet(Integer.valueOf(seatIdListary[i]));
				seatlist.add(seatvo);
			}
		}
		
		int re =dao.update(eventvo,ticketlist,eventclasslist,seatlist);
		
		return re;
	}
	
	public String getOrgName(int organizerNumber) {
	    return dao.getOrganizerName(organizerNumber);
	}
	
	public List<Map<String,Object>> findAllForDebug(){
	    return dao.findAllForDebug();
	}
	public List<EventVO> selectAllEvent() {
		List<EventVO> list1 = null;
		list1 = dao.selectAll();
		return list1;
	}
	public List<EventVO> searchEvent(String k) {
		List<EventVO> list1 = null;
		list1 = dao.searchBy(k);
		return list1;
	}
	public List<Integer> getBanner() {
		List<Integer> list1 = null;
		list1 = dao.getBanner();
		return list1;
	}
	public EventVO getSingleEvent(Integer eventNumber) {
		return dao.selectSingleEvent(eventNumber);
	}
	
	public static void main(String args[]) {
		EventService es = new EventService();
		es.selectAllEvent();
	}
	
}
