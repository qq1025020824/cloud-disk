package com.abo.vo;

import java.text.DecimalFormat;

public class UserInfoVO {
	private Long id; // User id
	private String username;
	private String freesize; // 含自动单位
	private String usedsize; // 含自动单位
	private String percentage; // 百分比，不含%

	public Long getId() {
		return id;
	}

	/**
	 * @param Long型
	 *            User.id
	 */
	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @return 自动单位
	 */
	public String getFreesize() {
		return freesize;
	}

	/**
	 * 自动添加单位
	 * 
	 * @param long型
	 *            Disk.totalsize
	 */
	public void setFreesize(long totalsize, long usedsize) {
		long freesize = totalsize - usedsize;
		int i;
		for (i = 1; freesize > 1024 && i < 5; i++) {
			freesize /= 1024;
		}
		String un;
		switch (i) {
		case 1:
			un = "B";
			break;
		case 2:
			un = "KB";
			break;
		case 3:
			un = "MB";
			break;
		case 4:
			un = "GB";
			break;
		case 5:
			un = "TB";
			break;
		default:
			un = "B";
			break;
		}
		this.freesize = String.valueOf(freesize) + un;
	}

	/**
	 * @return 自动单位
	 */
	public String getUsedsize() {
		return usedsize;
	}

	/**
	 * 自动添加单位
	 * 
	 * @param long型
	 *            Disk.usedsize
	 */
	public void setUsedsize(long usedsize) {
		int i;
		for (i = 1; usedsize > 1024 && i < 5; i++) {
			usedsize /= 1024;
		}
		String un;
		switch (i) {
		case 1:
			un = "B";
			break;
		case 2:
			un = "KB";
			break;
		case 3:
			un = "MB";
			break;
		case 4:
			un = "GB";
			break;
		case 5:
			un = "TB";
			break;
		default:
			un = "B";
			break;
		}
		this.usedsize = String.valueOf(usedsize) + un;
	}

	/**
	 * @return 保留小数点后两位，String型，含%
	 */
	public String getPercentage() {
		return percentage;
	}

	/**
	 * 计算百分比，保留小数点后两位，String型，含%
	 * 
	 * @param totalsize
	 * @param usedsize
	 */
	public void setPercentage(long totalsize, long usedsize) {
		double per = (double) usedsize / totalsize * 100;
		this.percentage = new DecimalFormat("#.##").format(per)+"%";
	}

	/**
	 * setUsedsize setFreesize setPercentage
	 * 
	 * @param totalsize
	 * @param usedsize
	 */
	public void setSizeinfo(long totalsize, long usedsize) {
		this.setUsedsize(usedsize);
		this.setFreesize(totalsize, usedsize);
		this.setPercentage(totalsize, usedsize);
	}
}
