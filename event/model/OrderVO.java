package tw.com.tibame.event.model;

import java.sql.Timestamp;

public class OrderVO {
	private Integer orderID; 
	private Integer eventNumber;
	private Integer number;
	private Timestamp orderDate;
	private String orderType;
	private Integer total;
	private Integer totalTicket;
	private String pData;
	private String reason;
	private Integer reasonMoney;
	private Float eventScore;
	private String eventSContent;
	private Timestamp eventSDate;
	
	
	public OrderVO() {
    }


	@Override
    public String toString() {
        return "OrderVO [orderID=" + orderID + ", eventNumber=" + eventNumber + ", number=" + number + ", orderDate="
                + orderDate + ", orderType=" + orderType + ", total=" + total + ", totalTicket=" + totalTicket
                + ", pData=" + pData + ", reason=" + reason + ", reasonMoney=" + reasonMoney + ", eventScore="
                + eventScore + ", eventSContent=" + eventSContent + ", eventSDate=" + eventSDate + "]";
    }

    public OrderVO(Integer orderID, Integer eventNumber, Integer number, Timestamp orderDate, String orderType,
            Integer total, Integer totalTicket, String pData, String reason, Integer reasonMoney, Float eventScore,
            String eventSContent, Timestamp eventSDate) {
        this.orderID = orderID;
        this.eventNumber = eventNumber;
        this.number = number;
        this.orderDate = orderDate;
        this.orderType = orderType;
        this.total = total;
        this.totalTicket = totalTicket;
        this.pData = pData;
        this.reason = reason;
        this.reasonMoney = reasonMoney;
        this.eventScore = eventScore;
        this.eventSContent = eventSContent;
        this.eventSDate = eventSDate;
    }

    public Integer getOrderID() {
		return orderID;
	}

	public void setOrderID(Integer orderID) {
		this.orderID = orderID;
	}

	public Integer getEventNumber() {
		return eventNumber;
	}

	public void setEventNumber(Integer eventNumber) {
		this.eventNumber = eventNumber;
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	public Timestamp getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Timestamp orderDate) {
		this.orderDate = orderDate;
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

	public String getpData() {
		return pData;
	}

	public void setpData(String pData) {
		this.pData = pData;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public Integer getReasonMoney() {
		return reasonMoney;
	}

	public void setReasonMoney(Integer reasonMoney) {
		this.reasonMoney = reasonMoney;
	}


	public String getEventSContent() {
		return eventSContent;
	}

	public void setEventSContent(String eventSContent) {
		this.eventSContent = eventSContent;
	}

	public Timestamp getEventSDate() {
		return eventSDate;
	}

	public void setEventSDate(Timestamp eventSDate) {
		this.eventSDate = eventSDate;
	}

    public Float getEventScore() {
        return eventScore;
    }

    public void setEventScore(Float eventScore) {
        this.eventScore = eventScore;
    }
	
	
	
	
}