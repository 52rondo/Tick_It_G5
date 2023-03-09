package tw.com.tibame.event.model;

import java.util.Arrays;

public class SoldTicketsVO {
	private Integer ticketNumber; 
	private Integer orderID;
	private Integer seatID;
	private Integer ticketID;
	private Boolean isUse;
	private Integer orderPrice;
	private byte[] ticketQR ;
	
	@Override
    public String toString() {
        return "SoldTicketsVO [ticketNumber=" + ticketNumber + ", orderID=" + orderID + ", seatID=" + seatID
                + ", ticketID=" + ticketID + ", isUse=" + isUse + ", orderPrice=" + orderPrice + ", ticketQR="
                + Arrays.toString(ticketQR) + "]";
    }

    public Integer getTicketNumber() {
		return ticketNumber;
	}
	public void setTicketNumber(Integer ticketNumber) {
		this.ticketNumber = ticketNumber;
	}
	public Integer getOrderID() {
		return orderID;
	}
	public void setOrderID(Integer orderID) {
		this.orderID = orderID;
	}
	
	public Integer getSeatID() {
        return seatID;
    }

    public void setSeatID(Integer seatID) {
        this.seatID = seatID;
    }

    public Integer getTicketID() {
		return ticketID;
	}
	public void setTicketID(Integer ticketID) {
		this.ticketID = ticketID;
	}
	public Boolean isUse() {
		return isUse;
	}
	public void setUse(Boolean isUse) {
		this.isUse = isUse;
	}
	public Integer getOrderPrice() {
		return orderPrice;
	}
	public void setOrderPrice(Integer orderPrice) {
		this.orderPrice = orderPrice;
	}
	public byte[] getTicketQR() {
		return ticketQR;
	}
	public void setTicketQR(byte[] ticketQR) {
		this.ticketQR = ticketQR;
	}

	
}