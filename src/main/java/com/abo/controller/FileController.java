package com.abo.controller;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping(value = "/file")
public class FileController {
	
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
