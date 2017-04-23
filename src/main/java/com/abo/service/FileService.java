package com.abo.service;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.abo.dao.FileDao;


public class FileService {
	@Resource(name = "fileDao")
	private FileDao fileDao;
	private static final Logger Log = LoggerFactory.getLogger(FileService.class);

}
