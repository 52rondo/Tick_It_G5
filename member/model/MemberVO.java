package tw.com.tibame.member.model;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

public class MemberVO {
	private Integer number;            // NOT NULL AUTO_INCREMENT
	private String account;            // NOT NULL
	private String password;           // NOT NULL
	private String email;              // NOT NULL
	private Date birthday;    
	private String name;               // NOT NULL
	private String phoneNumber;
	private Boolean subscription;      // default 0 NOT NULL
	private Timestamp createDate;  // CURRENT_TIMESTAMP
	private Boolean pass;      // default 0 NOT NULL
	private String IDNumber;
	private String phone2;
	private String postalCode;
	private String address;
	
//	public MemberVO(String userName, String userPWD) {
//		// TODO Auto-generated constructor stub
//	}
	@Override
	public String toString() {
		return "MemberVO [number=" + number + ", account=" + account + ", password=" + password + ", email=" + email
				+ ", birthday=" + birthday + ", name=" + name + ", phoneNumber=" + phoneNumber + ", subscription="
				+ subscription + ", createDate=" + createDate + ", pass=" + pass + ", IDNumber=" + IDNumber
				+ ", phone2=" + phone2 + ", postalCode=" + postalCode + ", address=" + address + "]";
	}
	public Integer getNumber() {
		return number;
	}
	public void setNumber(Integer number) {
		this.number = number;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public String getEmail(List<MemberVO> chk) {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Date getBirthday() {
		return birthday;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public Boolean getSubscription() {
		return subscription;
	}
	public void setSubscription(Boolean subscription) {
		this.subscription = subscription;
	}
	public Timestamp getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Timestamp createDate) {
		this.createDate = createDate;
	}
	public Boolean getPass() {
		return pass;
	}
	public void setPass(Boolean pass) {
		this.pass = pass;
	}

	public String getIDNumber() {
		return IDNumber;
	}
	public void setIDNumber(String IDNumber) {
		this.IDNumber = IDNumber;
	}
	public String getPhone2() {
		return phone2;
	}
	public void setPhone2(String phone2) {
		this.phone2 = phone2;
	}
	public String getPostalCode() {
		return postalCode;
	}
	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	
	
	
}
