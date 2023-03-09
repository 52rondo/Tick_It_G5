package tw.com.tibame.management.model;

import java.util.List;


public interface ContactDAOInterface {
	public void insert(ContactVO bean);
    public void update(ContactVO bean);
    public void delete(Integer contactID);
    public ContactVO findByPrimaryKey(Integer contactID);
    public List<ContactVO> selectAll();
		
}
