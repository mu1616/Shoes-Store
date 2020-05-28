package com.example.store.model.notice.dto;

import java.util.Date;

public class NoticeDTO {
	private int notice_idx;
	private String notice_type;
	private String notice_title;
	private String notice_contents;
	private String notice_id;
	private Date notice_date;
	public int getNotice_idx() {
		return notice_idx;
	}
	public void setNotice_idx(int notice_idx) {
		this.notice_idx = notice_idx;
	}
	public String getNotice_type() {
		return notice_type;
	}
	public void setNotice_type(String notice_type) {
		this.notice_type = notice_type;
	}
	public String getNotice_title() {
		return notice_title;
	}
	public void setNotice_title(String notice_title) {
		this.notice_title = notice_title;
	}
	public String getNotice_contents() {
		return notice_contents;
	}
	public void setNotice_contents(String notice_contents) {
		this.notice_contents = notice_contents;
	}
	public String getNotice_id() {
		return notice_id;
	}
	public void setNotice_id(String notice_id) {
		this.notice_id = notice_id;
	}
	public Date getNotice_date() {
		return notice_date;
	}
	public void setNotice_date(Date notice_date) {
		this.notice_date = notice_date;
	}
	@Override
	public String toString() {
		return "NoticeDTO [notice_idx=" + notice_idx + ", notice_type=" + notice_type + ", notice_title=" + notice_title
				+ ", notice_contents=" + notice_contents + ", notice_id=" + notice_id + ", notice_date=" + notice_date
				+ "]";
	}
	
	
}
