package tw.com.tibame.management.model;

import java.sql.Timestamp;

public class BulletinVO {

	private Integer bulletinID; // int AI PK         ;
	private String bulletinName; // varchar(255)    ;
	private String bulletinContent; // text         ;
	private Timestamp bulletinDate; //       ;
	private Boolean isTop; // bit(1)                 ;
	private Boolean isOpen; // bit(1)                ;
	private Timestamp bulletinCreateDate; // timestamp ;
	@Override
	public String toString() {
		return "bulletinVO [bulletinID=" + bulletinID + ", bulletinName=" + bulletinName + ", bulletinContent="
				+ bulletinContent + ", bulletinDate=" + bulletinDate + ", isTop=" + isTop + ", isOpen=" + isOpen
				+ ", bulletinCreateDate=" + bulletinCreateDate + "]";
	}
	public Integer getBulletinID() {
		return bulletinID;
	}
	public void setBulletinID(Integer bulletinID) {
		this.bulletinID = bulletinID;
	}
	public String getBulletinName() {
		return bulletinName;
	}
	public void setBulletinName(String bulletinName) {
		this.bulletinName = bulletinName;
	}
	public String getBulletinContent() {
		return bulletinContent;
	}
	public void setBulletinContent(String bulletinContent) {
		this.bulletinContent = bulletinContent;
	}
	public Timestamp getBulletinDate() {
		return bulletinDate;
	}
	public void setBulletinDate(Timestamp bulletinDate) {
		this.bulletinDate = bulletinDate;
	}
	public Boolean getIsTop() {
		return isTop;
	}
	public void setIsTop(Boolean isTop) {
		this.isTop = isTop;
	}
	public Boolean getIsOpen() {
		return isOpen;
	}
	public void setIsOpen(Boolean isOpen) {
		this.isOpen = isOpen;
	}
	public Timestamp getBulletinCreateDate() {
		return bulletinCreateDate;
	}
	public void setBulletinCreateDate(Timestamp bulletinCreateDate) {
		this.bulletinCreateDate = bulletinCreateDate;
	}

	

}
