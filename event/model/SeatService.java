package tw.com.tibame.event.model;

import java.util.List;

public class SeatService {
	private SeatDAO_interface dao;
	public SeatService() {
		dao = new SeatJDBCDAO();
	}
	
	public List<SeatVO> selectSeatByEventNumber(Integer eventNumber){
		List<SeatVO> re = dao.selectByEventNumber(eventNumber);
		return re;
	}
	
}
