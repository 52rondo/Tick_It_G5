package tw.com.tibame.event.model;

import java.util.List;

public class TicketService {
	private TicketDAO_interface dao;
	public TicketService() {
		dao = new TicketJDBCDAO();
	}
	
	public List<TicketVO> selectTicketByEventNumber(Integer eventNumber){
		return dao.selectByeventNumber(eventNumber);
	}
	
	public TicketVO queryTicketByTicketID(int ticketID){
        return dao.queryById(ticketID);
    }
}
