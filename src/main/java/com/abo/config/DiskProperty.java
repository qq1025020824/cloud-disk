package com.abo.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class DiskProperty {
	private static final Logger Log = LoggerFactory.getLogger(DiskProperty.class);
	
	@Value("${DISK_PATH}")
	private String diskpath;
	@Value("${DEFAULT_TOTAL_SIZE_NUM}")
	private String size;
	@Value("${DEFAULT_TOTAL_SIZE_UNIT}")
	private String unit;
	
	private static int totalsize=0;
	
	public String getDiskpath() {
		Log.debug("[ABO][getDiskpath()]diskpath:{}", diskpath);
		return diskpath;
	}

	public int getTotalsize() {
		if (totalsize == 0) {
			Log.debug("[ABO][init totalsize]");
			totalsize = Integer.parseInt(size);
			switch (unit) {
			case "TB":
				totalsize *= 1024;
			case "GB":
				totalsize *= 1024;
			case "MB":
				totalsize *= 1024;
			case "KB":
				totalsize *= 1024;
			case "B":
			default:
				break;
			}
			Log.debug("[ABO]totalsize:{}", totalsize);
		}
		return totalsize;
	}
}
