package tw.com.tibame.staff.model;

import java.util.*;

import tw.com.tibame.member.model.MemberVO;

public class StaffService {

	private StaffDAO_interface dao;

	public StaffService() {
		dao = new StaffJDBCDAO();
	}

	public List<StaffVO> getAll() {
		return dao.getAll();
	}

	public StaffVO findByStaffNumber(Integer staffNumber) {
		return dao.findByStaffNumber(staffNumber);
	}

	public StaffVO insertStaff(String staffName, String staffAccount, String staffPassword, String[] permissionNumber) {

		StaffVO staffVO = new StaffVO();
		staffVO.setStaffName(staffName);
		staffVO.setStaffAccount(staffAccount);
		staffVO.setStaffPassword(staffPassword);

		// 判斷是否null，如果是null不進去做以下這事情
		List<S_permissionVO> s_permissionVOList = new ArrayList<S_permissionVO>();
		if (permissionNumber != null) {
			// set s_permissionVO table value
			for (int i = 0; i < permissionNumber.length; i++) {
				S_permissionVO s_permissionVO = new S_permissionVO();
				// 轉型
				Integer I_permission = Integer.valueOf(permissionNumber[i]);
				s_permissionVO.setPermissionNumber(I_permission);
				s_permissionVOList.add(s_permissionVO);
			}
			System.out.println(s_permissionVOList.toString());
		}

		
		dao.insert(staffVO, s_permissionVOList);

		return staffVO;
	}

	public StaffVO updateStaff(Integer staffNumber, String staffName, String staffAccount, String staffPassword) {

		StaffVO staffVO = new StaffVO();

		staffVO.setStaffNumber(staffNumber);
		staffVO.setStaffName(staffName);
		staffVO.setStaffAccount(staffAccount);
		staffVO.setStaffPassword(staffPassword);
		dao.update(staffVO);

		return staffVO;
	}

	public StaffVO getOneStaff(Integer staffNumber) {
		System.out.println("get one service");
		return dao.findByPrimaryKey(staffNumber);
	}

	public void deleteStaff(Integer staffNumber) {
		dao.delete(staffNumber);

	}

	public StaffVO getOneByAccount(String staffAccount) {
		return dao.findByStaffAccount(staffAccount);
	}
	
	public StaffVO findByStaffAccount(String staffAccount) {
		return dao.findByStaffAccount2(staffAccount);
	}
	
	public String pwd(String staffPassword) {
		return dao.pwd(staffPassword);
	}

}
