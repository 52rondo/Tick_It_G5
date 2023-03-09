package tw.com.tibame.event.model;

import java.util.List;

public class EventTypeService {
	private EventTypeDAO_interface dao;
	public EventTypeService() {
		dao = new EventTypeJDBCDAO();
	}
	
	public List<EventTypeVO> getTypeIsON(){
		List<EventTypeVO> re = dao.selectTypeIsON();

//		System.out.println(re.toString());
		return re;
	}
}
