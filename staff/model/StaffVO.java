package tw.com.tibame.staff.model;

public class StaffVO {
	
	
	private Integer staffNumber;
	private String staffName;
	private String staffAccount;
	private String staffPassword;



	

	@Override
	public String toString() {
		return "StaffVO [staffNumber=" + staffNumber + ", staffName=" + staffName + ", staffAccount=" + staffAccount
				+ ", staffPassword=" + staffPassword + "]";
	}

	public Integer getStaffNumber() {
		return staffNumber;
	}

	public void setStaffNumber(Integer staffNumber) {
		this.staffNumber = staffNumber;
	}

	public String getStaffName() {
		return staffName;
	}

	public void setStaffName(String staffName) {
		this.staffName = staffName;
	}

	public String getStaffAccount() {
		return staffAccount;
	}

	public void setStaffAccount(String staffAccount) {
		this.staffAccount = staffAccount;
	}

	public String getStaffPassword() {
		return staffPassword;
	}

	public void setStaffPassword(String staffPassword) {
		this.staffPassword = staffPassword;
	}

}
