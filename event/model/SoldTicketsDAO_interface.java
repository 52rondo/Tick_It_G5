package tw.com.tibame.event.model;

import java.sql.Connection;
import java.util.List;

public interface SoldTicketsDAO_interface {
	public int insert(SoldTicketsVO vo,Connection conn);
	public int update(SoldTicketsVO vo);
	
	public void updateQRData(int ticketNumber , byte[] qr);
	
	public List<SoldTicketsVO> selectByOrderID(int orderId);
	
	public List<SoldTicketsVO> selectByEventNumber(int eventNumber);
	
	public List<SoldTicketsVO> selectByEventAndTicket(int eventNumber,int ticketId);
	
	public void deleteByOrderId(int orderID);
	
	public SoldTicketsVO queryById(int ticketNumber);
	
	public void updateUsed(int ticketNumber, Boolean used);
	
}
