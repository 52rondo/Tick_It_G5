package tw.com.tibame.management.model;

import java.time.LocalDateTime;

public class TermsVO {
	
	private Integer termsID; //ai pk
	private String termsTitle;
	private String termsContent; 
	private LocalDateTime termsCreateDate;

	@Override
	public String toString() {
		return "termsVO [termsID=" + termsID + ", termsTitle=" + termsTitle + ", termsContent=" + termsContent
				+ ", termsCreateDate=" + termsCreateDate + "]";
	}
	public Integer getTermsID() {
		return termsID;
	}
	public void setTermsID(Integer termsID) {
		this.termsID = termsID;
	}
	public String getTermsTitle() {
		return termsTitle;
	}
	public void setTermsTitle(String termsTitle) {
		this.termsTitle = termsTitle;
	}
	public String getTermsContent() {
		return termsContent;
	}
	public void setTermsContent(String termsContent) {
		this.termsContent = termsContent;
	}
	public LocalDateTime getTermsCreateDate() {
		return termsCreateDate;
	}
	public void setTermsCreateDate(LocalDateTime termsCreateDate) {
		this.termsCreateDate = termsCreateDate;
	}
	
	

}
