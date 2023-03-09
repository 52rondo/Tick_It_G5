package tw.com.tibame.event.model;

import java.sql.Connection;
import java.util.List;


public interface SeatDAO_interface {
	public int insert(SeatVO aseat,Connection conn);
//	public int update(SeatVO aseat,Connection conn);
	public void updateSeat(SeatVO aseat);
	public int updateByseatType();
	public SeatVO eventNumber();
	public List<SeatVO> selectByEventNumber(Integer eventNumber);
	public int deleteByEventNumber(Integer eventNumber, Connection con);
	
	public int updateBySeatSetAndEventNumver(int seatSet , int eventNumber,Boolean seatType);
	public SeatVO queryBySetAndEventNumber(int seatSet,int eventNumber);
	public SeatVO queryById(int id);
	
}
