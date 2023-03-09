package tw.com.tibame.event.model;

import java.sql.Timestamp;
import java.util.Arrays;

public class EventVO {
	private Integer eventNumber; //auto increment
	private Integer organizerNumber;
	private String eventName;
	private Timestamp eventStartDate;
	private Timestamp eventEndDate;
	private Integer peopleNumber;
	private String eventPlace;
	private String eventP2;
	private String eventSummary;
	private String eventDescribe;
	private byte[] bigImg;
	private byte[] smallImg;
	private byte[] video;
	private byte[] otherImg1;
	private byte[] otherImg2;
	private Boolean collectType; // default 0
	private Integer banner; // default 0
	private Integer focus; // default 0
	private Integer totalClick; // default 0
	private Boolean isON;
	private String eventType;
	private Boolean needSeat; // default 0
	private Integer seatX;
	private Integer seatY;
	@Override
	public String toString() {
		return "EventVO [eventNumber=" + eventNumber + ", organizerNumber=" + organizerNumber + ", eventName="
				+ eventName + ", eventStartDate=" + eventStartDate + ", eventEndDate=" + eventEndDate
				+ ", peopleNumber=" + peopleNumber + ", eventPlace=" + eventPlace + ", eventP2=" + eventP2
				+ ", eventSummary=" + eventSummary + ", eventDescribe=" + eventDescribe + ", bigImg="
				+ Arrays.toString(bigImg) + ", smallImg=" + Arrays.toString(smallImg) + ", video="
				+ Arrays.toString(video) + ", otherImg1=" + Arrays.toString(otherImg1) + ", otherImg2="
				+ Arrays.toString(otherImg2) + ", collectType=" + collectType + ", banner=" + banner + ", focus="
				+ focus + ", totalClick=" + totalClick + ", isON=" + isON + ", eventType=" + eventType + ", needSeat="
				+ needSeat + ", seatX=" + seatX + ", seatY=" + seatY + "]";
	}
	public Integer getEventNumber() {
		return eventNumber;
	}
	public void setEventNumber(Integer eventNumber) {
		this.eventNumber = eventNumber;
	}
	public Integer getOrganizerNumber() {
		return organizerNumber;
	}
	public void setOrganizerNumber(Integer organizerNumber) {
		this.organizerNumber = organizerNumber;
	}
	public String getEventName() {
		return eventName;
	}
	public void setEventName(String eventName) {
		this.eventName = eventName;
	}
	public Timestamp getEventStartDate() {
		return eventStartDate;
	}
	public void setEventStartDate(Timestamp eventStartDate) {
		this.eventStartDate = eventStartDate;
	}
	public Timestamp getEventEndDate() {
		return eventEndDate;
	}
	public void setEventEndDate(Timestamp eventEndDate) {
		this.eventEndDate = eventEndDate;
	}
	public Integer getPeopleNumber() {
		return peopleNumber;
	}
	public void setPeopleNumber(Integer peopleNumber) {
		this.peopleNumber = peopleNumber;
	}
	public String getEventPlace() {
		return eventPlace;
	}
	public void setEventPlace(String eventPlace) {
		this.eventPlace = eventPlace;
	}
	public String getEventP2() {
		return eventP2;
	}
	public void setEventP2(String eventP2) {
		this.eventP2 = eventP2;
	}
	public String getEventSummary() {
		return eventSummary;
	}
	public void setEventSummary(String eventSummary) {
		this.eventSummary = eventSummary;
	}
	public String getEventDescribe() {
		return eventDescribe;
	}
	public void setEventDescribe(String eventDescribe) {
		this.eventDescribe = eventDescribe;
	}
	public byte[] getBigImg() {
		return bigImg;
	}
	public void setBigImg(byte[] bigImg) {
		this.bigImg = bigImg;
	}
	public byte[] getSmallImg() {
		return smallImg;
	}
	public void setSmallImg(byte[] smallImg) {
		this.smallImg = smallImg;
	}
	public byte[] getVideo() {
		return video;
	}
	public void setVideo(byte[] video) {
		this.video = video;
	}
	public byte[] getOtherImg1() {
		return otherImg1;
	}
	public void setOtherImg1(byte[] otherImg1) {
		this.otherImg1 = otherImg1;
	}
	public byte[] getOtherImg2() {
		return otherImg2;
	}
	public void setOtherImg2(byte[] otherImg2) {
		this.otherImg2 = otherImg2;
	}
	public Boolean getCollectType() {
		return collectType;
	}
	public void setCollectType(Boolean collectType) {
		this.collectType = collectType;
	}
	public Integer getBanner() {
		return banner;
	}
	public void setBanner(Integer banner) {
		this.banner = banner;
	}
	public Integer getFocus() {
		return focus;
	}
	public void setFocus(Integer focus) {
		this.focus = focus;
	}
	public Integer getTotalClick() {
		return totalClick;
	}
	public void setTotalClick(Integer totalClick) {
		this.totalClick = totalClick;
	}
	public Boolean getIsON() {
		return isON;
	}
	public void setIsON(Boolean isON) {
		this.isON = isON;
	}
	public String getEventType() {
		return eventType;
	}
	public void setEventType(String eventType) {
		this.eventType = eventType;
	}
	public Boolean getNeedSeat() {
		return needSeat;
	}
	public void setNeedSeat(Boolean needSeat) {
		this.needSeat = needSeat;
	}
	public Integer getSeatX() {
		return seatX;
	}
	public void setSeatX(Integer seatX) {
		this.seatX = seatX;
	}
	public Integer getSeatY() {
		return seatY;
	}
	public void setSeatY(Integer seatY) {
		this.seatY = seatY;
	}
}
