package com.abo.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
@Lazy(false)
public class DiskProperty implements InitializingBean{
	private static final Logger Log = LoggerFactory.getLogger(DiskProperty.class);
	
	@Value("${DISK_PATH}")
	private String diskpath;
	@Value("${DEFAULT_TOTAL_SIZE_NUM}")
	private String size;
	@Value("${DEFAULT_TOTAL_SIZE_UNIT}")
	private String unit;
	
	private int totalsize;
	
	public String getDiskpath() {
		Log.debug("[ABO][getDiskpath()]diskpath:{}", diskpath);
		return diskpath;
	}

	public int getTotalsize() {
		Log.debug("[ABO][getTotalsize()]{}", totalsize);
		return totalsize;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		// TODO Auto-generated method stub
		Log.debug("[ABO][afterPropertiesSet()]diskpath:{}", diskpath);
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
}
