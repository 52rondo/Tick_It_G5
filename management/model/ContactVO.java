package tw.com.tibame.management.model;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class ContactVO {                            
	private Integer contactID; //pk ai
	private String contactTitle;
	private String contactContent;
	private Timestamp contactCreateDate;
	
	@Override
	public String toString() {
		return "ContactVO [contactID=" + contactID + ", contactTitle=" + contactTitle + ", contactContent="
				+ contactContent + ", contactCreateDate=" + contactCreateDate + "]";
	}
	public Integer getContactID() {
		return contactID;
	}
	public void setContactID(Integer contactID) {
		this.contactID = contactID;
	}
	public String getContactTitle() {
		return contactTitle;
	}
	public void setContactTitle(String contactTitle) {
		this.contactTitle = contactTitle;
	}
	public String getContactContent() {
		return contactContent;
	}
	public void setContactContent(String contactContent) {
		this.contactContent = contactContent;
	}
	public Timestamp getContactCreateDate() {
		return contactCreateDate;
	}
	public void setContactCreateDate(Timestamp contactCreateDate) {
		this.contactCreateDate = contactCreateDate;
	}
	
	
	
	
}                                                   
