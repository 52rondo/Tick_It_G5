package tw.com.tibame.member.model;

public class NoticeVO {
	private Integer noticeID;       // NOT NULL AUTO_INCREMENT
	private Integer number;         // NOT NULL
	private String noticeConetnt;   // NOT NULL
	private Boolean isRead;         // default 0
	
	@Override
	public String toString() {
		return "NoticeVO [noticeID=" + noticeID + ", number=" + number + ", noticeConetnt=" + noticeConetnt
				+ ", isRead=" + isRead + "]";
	}
	public Integer getNoticeID() {
		return noticeID;
	}
	public void setNoticeID(Integer noticeID) {
		this.noticeID = noticeID;
	}
	public Integer getNumber() {
		return number;
	}
	public void setNumber(Integer number) {
		this.number = number;
	}
	public String getNoticeConetnt() {
		return noticeConetnt;
	}
	public void setNoticeConetnt(String noticeConetnt) {
		this.noticeConetnt = noticeConetnt;
	}
	public Boolean getIsRead() {
		return isRead;
	}
	public void setIsRead(Boolean isRead) {
		this.isRead = isRead;
	}
	
	
}
