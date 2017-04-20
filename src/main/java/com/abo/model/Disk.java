package com.abo.model;

public class Disk {
	private Long id;
	private Long user_id;
	private long totalsize;		//单位B(Byte)
	private long usedsize;		//单位B(Byte)
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getUser_id() {
		return user_id;
	}
	public void setUser_id(Long user_id) {
		this.user_id = user_id;
	}
	public long getTotalsize() {
		return totalsize;
	}
	public void setTotalsize(long totalsize) {
		this.totalsize = totalsize;
	}
	public long getUsedsize() {
		return usedsize;
	}
	public void setUsedsize(long usedsize) {
		this.usedsize = usedsize;
	}
}
