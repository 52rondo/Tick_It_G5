package tw.com.tibame.event.model;

import java.sql.Timestamp;

public class TicketVO {
	private Integer ticketID;
	private String ticketName;
	private Integer eventNumber;
	private Integer price ;
	private Boolean limitTicket;
	private Integer ticketQuantity;
	private Timestamp ticketStartTime;
	private Timestamp ticketEndTime;
	private Integer ticketMIN ; 
	private Integer ticketMAX ;
	private String ticketType;
	
	

	@Override
	public String toString() {
		return "TicketVO [ticketID=" + ticketID + ", ticketName=" + ticketName + ", eventNumber=" + eventNumber
				+ ", price=" + price + ", limitTicket=" + limitTicket + ", ticketQuantity=" + ticketQuantity
				+ ", ticketStartTime=" + ticketStartTime + ", ticketEndTime=" + ticketEndTime + ", ticketMIN="
				+ ticketMIN + ", ticketMAX=" + ticketMAX + ", ticketType=" + ticketType + "]";
	}

	public Integer getTicketID() {
		return ticketID;
	}

	public void setTicketID(Integer ticketID) {
		this.ticketID = ticketID;
	}

	public String getTicketName() {
		return ticketName;
	}

	public void setTicketName(String ticketName) {
		this.ticketName = ticketName;
	}

	public Integer getEventNumber() {
		return eventNumber;
	}

	public void setEventNumber(Integer eventNumber) {
		this.eventNumber = eventNumber;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	public Boolean getLimitTicket() {
		return limitTicket;
	}

	public void setLimitTicket(Boolean limitTicket) {
		this.limitTicket = limitTicket;
	}

	public Integer getTicketQuantity() {
		return ticketQuantity;
	}

	public void setTicketQuantity(Integer ticketQuantity) {
		this.ticketQuantity = ticketQuantity;
	}

	public Timestamp getTicketStartTime() {
		return ticketStartTime;
	}

	public void setTicketStartTime(Timestamp ticketStartTime) {
		this.ticketStartTime = ticketStartTime;
	}

	public Timestamp getTicketEndTime() {
		return ticketEndTime;
	}

	public void setTicketEndTime(Timestamp ticketEndTime) {
		this.ticketEndTime = ticketEndTime;
	}

	public Integer getTicketMIN() {
		return ticketMIN;
	}

	public void setTicketMIN(Integer ticketMIN) {
		this.ticketMIN = ticketMIN;
	}

	public Integer getTicketMAX() {
		return ticketMAX;
	}

	public void setTicketMAX(Integer ticketMAX) {
		this.ticketMAX = ticketMAX;
	}

	public String getTicketType() {
		return ticketType;
	}

	public void setTicketType(String ticketType) {
		this.ticketType = ticketType;
	}
	
	
	
}
