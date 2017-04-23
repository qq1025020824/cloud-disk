package com.abo.dao;

import java.util.List;

import com.abo.model.MyFile;

public interface FileDao {
	
	/**
	 * 通过文件名查询一个文件列
	 * @param name
	 * @return
	 */
	List<MyFile> selectMyfileByName(String name);
	
	//未写
	/**
	 * 通过文件id获取文件
	 * @param id
	 * @return
	 */
	MyFile selectMyfileById(Long id);
}
