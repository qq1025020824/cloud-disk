package com.abo.controller;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Stack;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.abo.model.MyFile;
import com.abo.service.FileService;
import com.abo.service.UserService;
import com.abo.vo.MyFileVO;
import com.abo.vo.UserInfoVO;

@Controller
@RequestMapping(value = "/file")
public class FileController extends BaseController{
	private static final Logger Log = LoggerFactory.getLogger(FileController.class);
	@Autowired
    private FileService fileService;
	@Autowired
    private UserService userService;
	String FILE_BASE_PATH ="E:\\Programing\\temp\\clouddisk\\";
	
	//上传文件
	@SuppressWarnings("finally")
	@RequestMapping(value = "/doupload",method = RequestMethod.POST)
	public String doUpload(@RequestParam Long folder,@RequestParam MultipartFile uploadfile){
		if(uploadfile.isEmpty()){
			this.sendMessage("文件为空，请重新选择。");
			return "redirect:/file/view?folder="+folder.toString();
		}
		String fileName=uploadfile.getOriginalFilename();
		String suffix=fileName.substring(fileName.lastIndexOf(".")+1);
		long size=uploadfile.getSize();
		
		//判断剩余空间
		if(size>fileService.getFreeSize(this.getUserID())){
			this.sendMessage("剩余空间不足。");
			return "redirect:/file/view?folder="+folder.toString();
		}
		
		String localName=new Date().getTime()+"."+suffix;
		File localfile=new File(FILE_BASE_PATH,localName);
		//开始上传
		try{
			FileUtils.copyInputStreamToFile(uploadfile.getInputStream(),localfile);
			
			//操作数据库
			MyFile file=new MyFile();
			file.setUser_id(getUserID());
			file.setParent_id(folder);
			file.setName(fileName);
			file.setType(suffix.toLowerCase());
			file.setSize(size);
			file.setLocation(FILE_BASE_PATH+localName);
			if(!fileService.addFile(file)){
				//数据库错误，删除文件
				localfile.delete();
				this.sendMessage("系统错误，请稍后再试。");
				return "redirect:/file/view?folder="+folder.toString();
			}
			
			//更新用户信息
			UserInfoVO uif=userService.updateUserInfo(this.getUserID());
			if(uif!=null){
				sendUserInfo(uif);
			}
		}catch(IOException e){
			Log.debug("上传文件错误");
			if(localfile.exists()){
				localfile.delete();
			}
			this.sendMessage("系统错误，请稍后再试。");
		}finally{
			return "redirect:/file/view?folder="+folder.toString();
		}
	}
	
	/**
	 * @param folder 文件夹id,用户根目录为home
	 * @param model 
	 * Stack<MyFileVO> paths 至少有一个内容
	 * List<MyFileVO> contents 可能为空
	 * @return 
	 */
	@RequestMapping(value = "/view")
	public String showFolder(@RequestParam String folder,Model model){
		//取用户id
		Long userid=this.getUserID();
		if(userid==null){
			this.sendMessage("尚未登录，请先登录。");
			return "redirect:/login";
		}
		//判断folder
		if(folder.equals("home")){
			if(fileService.showUserRoot(userid, model)){
				return "file/view";
			}else{
				this.sendMessage("系统错误，请稍后再试。");
				return "redirect:/login";
			}
		}else{
			if(fileService.showFolder(new Long(folder), userid, model)){
				return "file/view";
			}else{
				//错误，跳转用户根目录
				return "redirect:/file/view?folder=home";
			}
		}
	}

	//test
	@RequestMapping(value = "/test1")
	public String test1(){
		System.out.println("into mapping /test1");
		return "file/success";
	}
	@RequestMapping(value = "/{test}")
	public String test2(@PathVariable String test){
		System.out.println("into mapping /{test}:"+test);
		return "file/success";
	}
}