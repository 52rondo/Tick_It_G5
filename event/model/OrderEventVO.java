package tw.com.tibame.event.model;

import java.sql.Blob;
import java.sql.Timestamp;

public class OrderEventVO {
	private Integer orderID; 
	private String orderType;
	private Integer total;
	private Integer totalTicket;
	private String eventName;
	private String eventPlace;
	private byte[] bigImg;
	private Timestamp eventStartDate;
	private String organizerName;
	private Integer number;
	private Integer eventNumber;
	private String bigImg64;
	
	@Override
	public String toString() {
		return "OrderEventVO [orderID=" + orderID + ", orderType=" + orderType + ", total=" + total + ", totalTicket="
				+ totalTicket + ", eventName=" + eventName + ", eventPlace=" + eventPlace + ", bigImg=" + bigImg
				+ ", eventStartDate=" + eventStartDate + ", organizerName=" + organizerName + ", number=" + number
				+ ", eventNumber=" + eventNumber + "]";
	}
	
	public Integer getOrderID() {
		return orderID;
	}
	public void setOrderID(Integer orderID) {
		this.orderID = orderID;
	}
	public String getOrderType() {
		return orderType;
	}
	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}
	public Integer getTotal() {
		return total;
	}
	public void setTotal(Integer total) {
		this.total = total;
	}
	public Integer getTotalTicket() {
		return totalTicket;
	}
	public void setTotalTicket(Integer totalTicket) {
		this.totalTicket = totalTicket;
	}
	public String getEventName() {
		return eventName;
	}
	public void setEventName(String eventName) {
		this.eventName = eventName;
	}
	public String getEventPlace() {
		return eventPlace;
	}
	public void setEventPlace(String eventPlace) {
		this.eventPlace = eventPlace;
	}
	
	public byte[] getBigImg() {
		return bigImg;
	}

	public void setBigImg(byte[] bigImg) {
		this.bigImg = bigImg;
	}

	public Timestamp getEventStartDate() {
		return eventStartDate;
	}
	public void setEventStartDate(Timestamp eventStartDate) {
		this.eventStartDate = eventStartDate;
	}
	public String getOrganizerName() {
		return organizerName;
	}
	public void setOrganizerName(String organizerName) {
		this.organizerName = organizerName;
	}
	public Integer getNumber() {
		return number;
	}
	public void setNumber(Integer number) {
		this.number = number;
	}
	public Integer getEventNumber() {
		return eventNumber;
	}
	public void setEventNumber(Integer eventNumber) {
		this.eventNumber = eventNumber;
	}

	public String getBigImg64() {
		return bigImg64;
	}

	public void setBigImg64(String bigImg64) {
		this.bigImg64 = bigImg64;
	}
}
