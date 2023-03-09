package tw.com.tibame.event.model;

import java.util.Arrays;

public class SeatVO {
	private Integer seatID;
	private Integer eventNumber;
	private Boolean seatType; // default 0
	private Integer seatNumber;
	private Integer seatSet;

	@Override
    public String toString() {
        return "SeatVO [seatID=" + seatID + ", eventNumber=" + eventNumber + ", seatType=" + seatType + ", seatNumber="
                + seatNumber + ", seatSet=" + seatSet + "]";
    }
	

    public Boolean getSeatType() {
        return seatType;
    }

    public void setSeatType(Boolean seatType) {
        this.seatType = seatType;
    }

    public Integer getSeatID() {
		return seatID;
	}

	public void setSeatID(Integer seatID) {
		this.seatID = seatID;
	}

	public Integer getEventNumber() {
		return eventNumber;
	}

	public void setEventNumber(Integer eventNumber) {
		this.eventNumber = eventNumber;
	}

	public Integer getSeatNumber() {
		return seatNumber;
	}

	public void setSeatNumber(Integer seatNumber) {
		this.seatNumber = seatNumber;
	}

	public Integer getSeatSet() {
		return seatSet;
	}

	public void setSeatSet(Integer seatSet) {
		this.seatSet = seatSet;
	}

}
