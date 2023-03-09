package tw.com.tibame.management.model;

public class ContactService {
	private ContactDAOInterface dao;
	public ContactService() {
		dao = new ContactDAO();
	}
	public boolean addContactUs(ContactVO bean) {
		boolean isSuccess = false;
		if(bean!=null) {
			dao.insert(bean);
			isSuccess = true;
		}
		return isSuccess;
	}
}
