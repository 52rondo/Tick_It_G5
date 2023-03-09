package tw.com.tibame.member.model;

import java.util.List;

import tw.com.tibame.event.model.EventVO;

public class CollectService {
	private CollectDAOinterface dao;

	public CollectService() {
		 dao = new CollectDAO();
	}
	
	public List<Integer> getFavorite(int memberid){
		return dao.selectAll(memberid);
	}
	
	public void addFavorite(int member, int event) {
		dao.insert(member, event);
	}
	
	public void delete(int member, int event) {
		dao.delete(member, event);
	}

}
