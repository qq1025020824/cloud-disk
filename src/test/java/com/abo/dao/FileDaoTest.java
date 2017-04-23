package com.abo.dao;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import com.abo.model.MyFile;

import junit.framework.Assert;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration("classpath:conf/spring/persistent.xml")
@EnableTransactionManagement
public class FileDaoTest {

	@Autowired
	private FileDao fileDao;
	
	@Test
	@Transactional
	public void selectMyfileByNameTest(){
		Assert.assertNotNull(fileDao);
		List<MyFile> files = fileDao.selectMyfileByName("#1");
//		Assert.assertEquals(, actual);
		for(MyFile file : files){
			System.out.println(file);
		}
	}
}
