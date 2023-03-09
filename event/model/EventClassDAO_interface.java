package tw.com.tibame.event.model;

import java.sql.Connection;
import java.util.List;

public interface EventClassDAO_interface {
	public List<EventClassVO> selectByeventNumber(Integer eventNumber);
//	public int update(EventClassVO eventclassvo, Connection con);
	public int insert(EventClassVO eventclassvo, Connection con);
	public int deleteByEventNumber(Integer eventNumber, Connection con);
}
