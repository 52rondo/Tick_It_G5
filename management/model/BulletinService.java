package tw.com.tibame.management.model;

import java.util.ArrayList;
import java.util.List;

public class BulletinService {
	private BulletinDAOInterface dao;
	public BulletinService(){
		dao = new BulletinDAO();
	}
	public BulletinVO addBulletin(BulletinVO bean) {
		dao.insert(bean);
		return bean;
	}
	public List<BulletinVO> getAllOn(){
		List<BulletinVO> list = new ArrayList<BulletinVO>();
		list = dao.selectAllOpen();
		return list;
	}
}
