package tw.com.tibame.management.model;

import java.util.List;


public interface BulletinDAOInterface {
	public void insert(BulletinVO bean);
    public void update(BulletinVO bean);
    public void delete(Integer bulletinID);
    public BulletinVO findByPrimaryKey(Integer bulletinID);
    public List<BulletinVO> selectAllOpen();
}
