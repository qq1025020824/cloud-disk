package com.abo.model;

public class Disk {
	private Long id;
	private Long user_id;
	private long totalSize;
	private long usedSize;
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
	public long getTotalSize() {
		return totalSize;
	}
	public void setTotalSize(long totalSize) {
		this.totalSize = totalSize;
	}
	public long getUsedSize() {
		return usedSize;
	}
	public void setUsedSize(long usedSize) {
		this.usedSize = usedSize;
	}
}
