package tw.com.tibame.staff.model;

public class PermissionVO {

	private Integer permissionNumber;
	private String permissionName;

	@Override
	public String toString() {
		return "permissionVO [permissionNumber=" + permissionNumber + ", permissionName=" + permissionName + "]";
	}

	public Integer getPermissionNumber() {
		return permissionNumber;
	}

	public void setPermissionNumber(Integer permissionNumber) {
		this.permissionNumber = permissionNumber;
	}

	public String getPermissionName() {
		return permissionName;
	}

	public void setPermissionName(String permissionName) {
		this.permissionName = permissionName;
	}
}
