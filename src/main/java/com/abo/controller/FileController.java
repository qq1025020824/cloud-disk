package com.abo.controller;

import java.io.File;
import java.io.IOException;

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

@Controller
@RequestMapping(value = "/file")
public class FileController extends BaseController{
	private static final Logger Log = LoggerFactory.getLogger(FileController.class);
	@Autowired
    private FileService fileService;
	
	//文件上传页
	@RequestMapping(value = "/upload")
	public String showUploadPage(){
		//跳转到文件上传页
		return "file/file_upload";
	}
	
	//上传文件
	@RequestMapping(value = "/doupload",method = RequestMethod.POST)
	public String doUpload(@RequestParam MultipartFile uploadfile) throws IOException{
		System.out.println("into doupload");
		if(!uploadfile.isEmpty()){
			System.out.println("start load");
			FileUtils.copyInputStreamToFile(uploadfile.getInputStream(), 
					new File("E:\\Programing\\temp\\file\\"+"abo\\",System.currentTimeMillis()+uploadfile.getOriginalFilename()));
			System.out.println("end load");
		}
		return "redirect:/file/upload";
	}
	
	/**
	 * @param folder 文件夹id,用户根目录为home
	 * @param model
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