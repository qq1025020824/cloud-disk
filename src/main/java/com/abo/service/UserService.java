package com.abo.service;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abo.dao.UserDao;
import com.abo.model.Disk;
import com.abo.model.MyFile;
import com.abo.model.User;
import com.abo.vo.UserInfoVO;

@Service("userService")
public class UserService {
	@Resource(name = "userDao")
	private UserDao userDao;
	private static final long DEFAULT_TOTAL_SIZE = 1024 * 1024 * 5; // 默认空间5M
	private static final Logger Log = LoggerFactory.getLogger(UserService.class);
	/**
	 * 验证手机是否被注册
	 * 
	 * @param phone
	 * @return
	 */
	public boolean isPhoneUsed(String phone) {
		// System.out.println("into isPhoneUsed()");
		if (userDao.countUserByPhone(phone) > 0) {
			return true;
		}
		return false;
	}

	/**
	 * 创建新的账户
	 * 
	 * @param user
	 * @return
	 */
	@Transactional
	public boolean createUser(User user) {
		// 添加注册时间
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日(E)");
		user.setJoindate(sdf.format(new Date()));

		// 插入用户
		if (userDao.insertUser(user) == 0) {
			return false;
		}

		// 分配网盘
		Disk disk = new Disk();
		disk.setUser_id(user.getId());
		disk.setTotalsize(DEFAULT_TOTAL_SIZE);
		disk.setUsedsize(0);
		if (userDao.insertDisk(disk) == 0) {
			/* 错误处理 */
			return false;
		}

		// 创建用户目录
		MyFile myfile = new MyFile();
		myfile.setUser_id(user.getId());
		myfile.setParent_id(null);
		myfile.setName("#" + user.getId());
		myfile.setSize(0);
		myfile.setType("folder");
		myfile.setPath("/");
		if (userDao.insertMyFile(myfile) == 0) {
			/* 错误处理 */
			return false;
		}

		return true;
	}

	/**
	 * 通过phone,password登录用户
	 * 
	 * @param user，需包含phone,password
	 * @return 登录成功则return true,并填充user信息；错误则返回false
	 */
	public boolean loginUser(User user) {
		User usertemp = userDao.selectUserByPhone(user.getPhone());
		if (usertemp != null) {
			if (usertemp.getPassword().equals(user.getPassword())) {
				// 密码正确
				user.setId(usertemp.getId());
				user.setUsername(usertemp.getUsername());
				user.setJoindate(usertemp.getJoindate());
				Log.debug("!!usertemp:{}",usertemp);
				Log.debug("!!user:{}",user);
				return true;
			} else {
				// 密码错误
				return false;
			}
		} else {
			return false;
		}
	}

	/**
	 * 获取最新的页面展示用户信息
	 * 
	 * @param userid
	 * @return 成功UserInfoVO对象，错误返回null
	 */
	public UserInfoVO updateUserInfo(Long userid) {
		User user = userDao.selectUserById(userid);
		Log.debug("user is:{}",user);
		Disk disk = userDao.selectDiskByUserid(userid);
		if (user != null && disk != null) {
			UserInfoVO uif = new UserInfoVO();
			uif.setId(user.getId());
			uif.setUsername(user.getUsername());
			uif.setSizeinfo(disk.getTotalsize(), disk.getUsedsize());
			return uif;
		} else {
			return null;
		}
	}
}
