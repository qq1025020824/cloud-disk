package com.abo.dao;

import java.util.List;

import com.abo.model.Disk;
import com.abo.model.FileMd5;
import com.abo.model.MyFile;

public interface FileDao {
	
	/**
	 * 通过文件名查询一个文件列
	 * @param name
	 * @return
	 */
	List<MyFile> selectMyfileByName(String name);
	
	/**
	 * 通过文件id获取文件
	 * @param id
	 * @return
	 */
	MyFile selectMyfileById(Long id);
	
	/**
	 * 找出父目录下的所以文件
	 * @param parent_id
	 * @return
	 */
	List<MyFile> selectMyfileByParent(Long parent_id);
	
	/**
	 * 通过user_id获取mydiskinfo表中一行
	 * @param userid
	 * @return
	 */
	Disk selectDiskByUserid(Long user_id);
	
	/**
	 * 往mydiskinfo表中插入一行
	 * @param disk
	 * @return 成功插入行数 1/0
	 * id自动产生并填入
	 */
	int insertDisk(Disk disk);
	
	/**
	 * 往文件表中插入一行
	 * @param myfile
	 * @return 成功插入行数 1/0
	 * id自动产生并填入
	 */
	int insertMyFile(MyFile myfile);

	/**
	 * 更新mydiskinfo表中的一行
	 * @param disk
	 */
	void updateDisk(Disk disk);
	
	/**
	 * 通过id获取文件path
	 * @param id
	 * @return
	 */
	String getPath(Long id);
	
	/**
	 * 通过id删除文件
	 * @param id
	 * @return
	 */
	void delectMyFileByID(Long id);
	
	/**
	 * 通过md5查询FileMd5列
	 * @param md5
	 * @return
	 */
	List<FileMd5> selectFileMd5ByMd5(String md5);
	
	/**
	 * 通过file_id获取文件md5值
	 * @param file_id
	 * @return
	 */
	String selectMd5Byfile(Long file_id);
	
	/**
	 * 向FileMd5表中插入一行
	 * @param filemd5
	 * @return 成功插入行数 1/0
	 * id自动产生并填入
	 */
	int insertFileMd5(FileMd5 filemd5);
	
	/**
	 * 通过file_id删除FileMd5表中的一行
	 * @param file_id
	 */
	void deleteFileMd5ByFile(Long file_id);
	
}
