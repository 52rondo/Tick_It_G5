package tw.com.tibame.staff.model;

import java.util.List;

//要先實作interface出來給service用
public interface StaffDAO_interface {
	
	public void insert(StaffVO staffVO,List<S_permissionVO> list);
	public void update(StaffVO staffVO);
	public void delete(Integer staffNumber);
	public StaffVO findByPrimaryKey(Integer staffNumber);
	public StaffVO findByStaffAccount(String staffAccount);
	public StaffVO findByStaffAccount2(String staffAccount);
	public List<StaffVO> getAll();
	public StaffVO findByStaffNumber(Integer staffNumber);
	public String pwd(String staffPassword) ;
	//萬用複合查詢(傳入參數型態Map)(回傳 List)
//  public List<EmpVO> getAll(Map<String, String[]> map);
}
