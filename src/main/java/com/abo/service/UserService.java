package com.abo.service;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abo.dao.UserDao;
import com.abo.model.Disk;
import com.abo.model.MyFile;
import com.abo.model.User;

@Service("userService")
public class UserService {
	@Resource(name = "userDao")
	private UserDao userDao;
	private static final long DEFAULT_TOTAL_SIZE = 1024*1024*5; //默认空间5M
	
	/**
	 * 验证手机是否被注册
	 * @param phone
	 * @return
	 */
	@Transactional
	public boolean isPhoneUsed(String phone){
//		System.out.println("into isPhoneUsed()");
		if(userDao.countUserByPhone(phone)>0){
			return true;
		}
		return false;
	}
	
	/**
	 * 创建新的账户
	 * @param user
	 * @return
	 */
	@Transactional
	public boolean createUser(User user){
		//添加注册时间
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日(E)");
		user.setJoindate(sdf.format(new Date()));
		
		//插入用户
		if(userDao.insertUser(user)==0){
			return false;
		}
		
		//分配网盘
		Disk disk=new Disk();
		disk.setUser_id(user.getId());
		disk.setTotalsize(DEFAULT_TOTAL_SIZE);
		disk.setUsedsize(0);
		if(userDao.insertDisk(disk)==0){
			/*错误处理*/
			return false;
		}
		
		//创建用户目录
		MyFile myfile=new MyFile();
		myfile.setUser_id(user.getId());
		myfile.setParent_id(null);
		myfile.setName("#"+user.getId());
		myfile.setSize(0);
		myfile.setType("folder");
		myfile.setPath("/");
		if(userDao.insertMyFile(myfile)==0){
			/*错误处理*/
			return false;
		}
		
		return true;
	}
}
