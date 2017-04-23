package com.abo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;

import com.abo.dao.FileDao;
import com.abo.model.MyFile;
import com.abo.vo.MyFileVO;


public class FileService {
	@Resource(name = "fileDao")
	private FileDao fileDao;
	private static final Logger Log = LoggerFactory.getLogger(FileService.class);
	
	/**
	 * 展示用户根目录
	 * @param userid
	 * @param model
	 * @return
	 * 正确则向model插入信息paths,contents，返回true
	 * 错误则返回false
	 */
	public boolean showUserRoot(Long userid,Model model){
		//找到用户根目录
		List<MyFile> filelist=fileDao.selectMyfileByName("#"+userid.toString());
		MyFile folder=null;
		if(filelist==null) return false;
		for(MyFile file:filelist){
			if(file.getParent_id()==null&&file.getType().equals("folder")){
				folder=file;
			}
		}
		if(folder==null){
			return false;
		}
		//路径列
		Stack<MyFileVO> paths=new Stack<MyFileVO>();
		MyFileVO myv=new MyFileVO();
		myv.setId(folder.getId().toString());
		myv.setName("home");
		myv.setSize("-");
		myv.setType(folder.getType());
		myv.setCreatedate(folder.getCreatedate());
		paths.push(myv);
		model.addAttribute("paths", paths);
		//内容列
		List<MyFileVO> contents=new ArrayList<MyFileVO>();
		List<MyFile> templist=fileDao.selectMyfileByParent(folder.getId());
		if(templist==null){
			model.addAttribute("contents", contents);
			return true;
		}
		for(MyFile file:templist){
			MyFileVO myvc =new MyFileVO();
			myvc.setId(file.getId().toString());
			if(file.getType().equals("folder")){
				//如果是文件夹
				myvc.setName(file.getName());
				myvc.setSize("-");
			}else{
				//如果是文件
				myvc.setName(file.getName()+"."+file.getType());
				//自动给size加单位
				long size=file.getSize();
				int i;
				for (i = 1; size > 1024 && i < 5; i++) {
					size /= 1024;
				}
				String un;
				switch (i) {
				case 1:
					un = "B";
					break;
				case 2:
					un = "KB";
					break;
				case 3:
					un = "MB";
					break;
				case 4:
					un = "GB";
					break;
				case 5:
					un = "TB";
					break;
				default:
					un = "B";
					break;
				}
				myvc.setSize(String.valueOf(size) + un);
			}
			myvc.setType(file.getType());
			myvc.setCreatedate(file.getCreatedate());
			contents.add(myvc);
		}
		model.addAttribute("contents", contents);
		return true;
	}
	
	/**
	 * 展示文件夹
	 * 需传入userid用于验证
	 * @param folderid
	 * @param model
	 * @return
	 * 正确则向model插入信息，返回true
	 * 错误则返回false
	 */
	public boolean showFolder(Long folderid,Long userid,Model model){
		//获取目录
		MyFile folder=fileDao.selectMyfileById(folderid);
		if(folder==null){
			//id错误
			return false;
		}
		if(!folder.getType().equals("folder")){
			//非文件夹
			return false;
		}
		if(!folder.getUser_id().equals(userid)){
			//用户无权限
			return false;
		}
		//路径列
		Stack<MyFileVO> paths=new Stack<MyFileVO>();
		while(true){
			MyFileVO pfile=new MyFileVO();
			pfile.setId(folder.getId().toString());
			if(folder.getParent_id()==null){
				//用户根目录
				pfile.setName("home");
			}else{
				pfile.setName(folder.getName());
			}
			pfile.setSize("-");
			pfile.setType(folder.getType());
			pfile.setCreatedate(folder.getCreatedate());
			paths.push(pfile);
			if(folder.getParent_id()==null){
				//找到用户根目录
				break;
			}else{
				//找父目录
				folder=fileDao.selectMyfileById(folder.getParent_id());
			}
		}
		model.addAttribute("paths", paths);
		
		//内容列
		List<MyFileVO> contents=new ArrayList<MyFileVO>();
		List<MyFile> templist=fileDao.selectMyfileByParent(folder.getId());
		if(templist==null){
			model.addAttribute("contents", contents);
			return true;
		}
		for(MyFile file:templist){
			MyFileVO myvc =new MyFileVO();
			myvc.setId(file.getId().toString());
			if(file.getType().equals("folder")){
				//如果是文件夹
				myvc.setName(file.getName());
				myvc.setSize("-");
			}else{
				//如果是文件
				myvc.setName(file.getName()+"."+file.getType());
				//自动给size加单位
				long size=file.getSize();
				int i;
				for (i = 1; size > 1024 && i < 5; i++) {
					size /= 1024;
				}
				String un;
				switch (i) {
				case 1:
					un = "B";
					break;
				case 2:
					un = "KB";
					break;
				case 3:
					un = "MB";
					break;
				case 4:
					un = "GB";
					break;
				case 5:
					un = "TB";
					break;
				default:
					un = "B";
					break;
				}
				myvc.setSize(String.valueOf(size) + un);
			}
			myvc.setType(file.getType());
			myvc.setCreatedate(file.getCreatedate());
			contents.add(myvc);
		}
		model.addAttribute("contents", contents);
		return true;
	}

}
