package tw.com.tibame.event.model;

import java.util.Arrays;

public class EventTypeVO {
	private Integer eventClassNumber;
	private String eventClassName;
	private Boolean eventClassState;


	@Override
	public String toString() {
		return "EventTypeVO [eventClassNumber=" + eventClassNumber + ", eventClassName=" + eventClassName
				+ ", eventClassState=" + eventClassState + "]";
	}

	public Integer getEventClassNumber() {
		return eventClassNumber;
	}

	public void setEventClassNumber(Integer eventClassNumber) {
		this.eventClassNumber = eventClassNumber;
	}

	public String getEventClassName() {
		return eventClassName;
	}

	public void setEventClassName(String eventClassName) {
		this.eventClassName = eventClassName;
	}

	public Boolean getEventClassState() {
		return eventClassState;
	}

	public void setEventClassState(Boolean eventClassState) {
		this.eventClassState = eventClassState;
	}
}
