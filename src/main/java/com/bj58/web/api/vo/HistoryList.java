package com.bj58.web.api.vo;

public class HistoryList {
	
	private int auditStatus;
	
	private String createTime;
	
	private int type;
	
	private String desc;  // 房抵的抵押权;垫资的上家名称

	public int getAuditStatus() {
		return auditStatus;
	}

	public void setAuditStatus(int auditStatus) {
		this.auditStatus = auditStatus;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
	
}
