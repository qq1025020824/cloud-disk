package com.abo.dao;

import com.abo.model.Disk;
import com.abo.model.MyFile;
import com.abo.model.User;

public interface UserDao {
	
	/**
	 * 通过手机号码查找用户数量
	 * @param phone
	 * @return
	 */
	Long countUserByPhone(String phone);
	
	/**
	 * 向用户表中插入一行
	 * @param user
	 * @return 成功插入行数 1/0
	 * user id自动产生并填入
	 */
	int insertUser(User user);
	
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
	
}