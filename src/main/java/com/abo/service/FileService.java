package com.abo.service;

import java.util.Stack;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.abo.dao.FileDao;
import com.abo.model.MyFile;
import com.abo.vo.MyFileVO;


public class FileService {
	@Resource(name = "fileDao")
	private FileDao fileDao;
	private static final Logger Log = LoggerFactory.getLogger(FileService.class);

	//未写
	
	/**
	 * 判断用户是否能访问某文件
	 * 包含文件不存在情形
	 * @param userid
	 * @param fileid
	 * @return
	 */
	public boolean checkUserFile(Long userid,Long fileid){
		return false;
	}
	
	/**
	 * 取出用户根目录
	 * @param userid
	 * @return
	 */
	public MyFile getUserRoot(Long userid){
		return null;
	}
	
	/**
	 * 获取路径栈用于展示
	 * @param folderid
	 * @return
	 */
	public Stack<MyFileVO> getPathByFolderID(Long folderid){
		return null;
	}
}
