package tw.com.tibame.member.model;

public class CollectVO {
	private Integer number;		    // NOT NULL
	private Integer eventNumber;    // NOT NULL
	@Override
	public String toString() {
		return "CollectVO [number=" + number + ", eventNumber=" + eventNumber + "]";
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
	
	
}
