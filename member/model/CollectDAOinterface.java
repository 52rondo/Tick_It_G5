package tw.com.tibame.member.model;

import java.util.List;

import tw.com.tibame.event.model.EventVO;

public interface CollectDAOinterface {
	public void insert(int memberId, int eventId);
	public List<Integer> selectAll(int memeberId);
	public void delete(int memberId, int eventId);
}
