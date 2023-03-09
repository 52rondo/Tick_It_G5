package tw.com.tibame.staff.model;

public class S_permissionVO {

	private Integer staffNumber;
	private Integer permissionNumber;

	@Override
	public String toString() {
		return "s_permissionVO [staffNumber=" + staffNumber + ", permissionNumber=" + permissionNumber + "]";
	}

	public Integer getStaffNumber() {
		return staffNumber;
	}

	public void setStaffNumber(Integer staffNumber) {
		this.staffNumber = staffNumber;
	}

	public Integer getPermissionNumber() {
		return permissionNumber;
	}

	public void setPermissionNumber(Integer permissionNumber) {
		this.permissionNumber = permissionNumber;
	}

}
