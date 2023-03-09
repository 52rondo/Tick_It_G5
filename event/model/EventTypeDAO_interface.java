package tw.com.tibame.event.model;

import java.util.List;

public interface EventTypeDAO_interface {
	public int insert(EventTypeVO eventTypevo);
	public int update(EventTypeVO eventTypevo);
	public List<EventTypeVO> selectAll();
	public EventTypeVO selectByEventClassNumber(Integer eventClassNumber);
	public List<EventTypeVO> selectTypeIsON();
}
