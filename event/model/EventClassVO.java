package tw.com.tibame.event.model;

public class EventClassVO {
	private Integer eventNumber;
	private Integer eventClassNumber;

	@Override
	public String toString() {
		return "EventClassVO [eventNumber=" + eventNumber + ", eventClassNumber=" + eventClassNumber + "]";
	}

	public Integer getEventNumber() {
		return eventNumber;
	}

	public void setEventNumber(Integer eventNumber) {
		this.eventNumber = eventNumber;
	}

	public Integer getEventClassNumber() {
		return eventClassNumber;
	}

	public void setEventClassNumber(Integer eventClassNumber) {
		this.eventClassNumber = eventClassNumber;
	}

}
