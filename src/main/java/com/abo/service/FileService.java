package com.abo.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Stack;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import com.abo.dao.FileDao;
import com.abo.dao.UserDao;
import com.abo.model.Disk;
import com.abo.model.MyFile;
import com.abo.vo.MyFileVO;


@Service
public class FileService {
	@Autowired
	private FileDao fileDao;
	private static final Logger Log = LoggerFactory.getLogger(FileService.class);

	/**
	 * 展示用户根目录
	 * 
	 * @param userid
	 * @param model
	 * @return 正确则向model插入信息paths,contents，返回true 错误则返回false
	 */
	public boolean showUserRoot(Long userid, Model model) {
		// 找到用户根目录
		List<MyFile> filelist = fileDao.selectMyfileByName("#" + userid.toString());
		MyFile folder = null;
		if (filelist == null)
			return false;
		for (MyFile file : filelist) {
			if (file.getParent_id() == null && file.getType().equals("folder")) {
				folder = file;
			}
		}
		if (folder == null) {
			return false;
		}
		// 路径列
		Stack<MyFileVO> paths = new Stack<MyFileVO>();
		MyFileVO myv = new MyFileVO();
		myv.setId(folder.getId().toString());
		myv.setName("home");
		myv.setSize("-");
		myv.setType(folder.getType());
		myv.setCreatedate(folder.getCreatedate());
		paths.push(myv);
		model.addAttribute("paths", paths);
		// 内容列
		List<MyFileVO> contents = new ArrayList<MyFileVO>();
		List<MyFile> templist = fileDao.selectMyfileByParent(folder.getId());
		if (templist == null) {
			model.addAttribute("contents", contents);
			return true;
		}
		for (MyFile file : templist) {
			MyFileVO myvc = new MyFileVO();
			myvc.setId(file.getId().toString());
			myvc.setName(file.getName());
			if (file.getType().equals("folder")) {
				// 如果是文件夹
				myvc.setSize("-");
			} else {
				// 如果是文件
				// 自动给size加单位
				long size = file.getSize();
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
	 * 展示文件夹 需传入userid用于验证
	 * 
	 * @param folderid
	 * @param model
	 * @return 正确则向model插入信息，返回true 错误则返回false
	 */
	public boolean showFolder(Long folderid, Long userid, Model model) {
		// 获取目录
		MyFile curfolder = fileDao.selectMyfileById(folderid);
		if (curfolder == null) {
			// id错误
			return false;
		}
		if (!curfolder.getType().equals("folder")) {
			// 非文件夹
			return false;
		}
		if (!curfolder.getUser_id().equals(userid)) {
			// 用户无权限
			return false;
		}
		MyFile folder = curfolder;
		// 路径列
		Stack<MyFileVO> paths = new Stack<MyFileVO>();
		while (true) {
			MyFileVO pfile = new MyFileVO();
			pfile.setId(folder.getId().toString());
			if (folder.getParent_id() == null) {
				// 用户根目录
				pfile.setName("home");
			} else {
				pfile.setName(folder.getName());
			}
			pfile.setSize("-");
			pfile.setType(folder.getType());
			pfile.setCreatedate(folder.getCreatedate());
			paths.push(pfile);
			if (folder.getParent_id() == null) {
				// 找到用户根目录
				break;
			} else {
				// 找父目录
				folder = fileDao.selectMyfileById(folder.getParent_id());
			}
		}
		model.addAttribute("paths", paths);

		folder = curfolder;
		// 内容列
		List<MyFileVO> contents = new ArrayList<MyFileVO>();
		List<MyFile> templist = fileDao.selectMyfileByParent(folder.getId());
		if (templist == null) {
			model.addAttribute("contents", contents);
			return true;
		}
		for (MyFile file : templist) {
			MyFileVO myvc = new MyFileVO();
			myvc.setId(file.getId().toString());
			myvc.setName(file.getName());
			if (file.getType().equals("folder")) {
				// 如果是文件夹
				myvc.setSize("-");
			} else {
				// 如果是文件
				// 自动给size加单位
				long size = file.getSize();
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
	 * 获取网盘剩余空间
	 * 
	 * @param userid
	 * @return
	 */
	public long getFreeSize(Long userid) {
		Disk disk = fileDao.selectDiskByUserid(userid);
		return disk.getTotalsize() - disk.getUsedsize();
	}

	/**
	 * 向网盘添加文件
	 * 
	 * @param file
	 *            须包含:user_id,parent_id,name,type,size,location
	 * @return 插入成功返回true,否则false 自动填充file中的信息
	 */
	@Transactional
	public boolean addFile(MyFile file) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		file.setCreatedate(sdf.format(new Date()));
		file.setPath(fileDao.getPath(file.getParent_id()) + file.getParent_id() + "/");
		if (fileDao.insertMyFile(file) == 0) {
			return false;
		}
		// 更新网盘信息
		Disk disk = fileDao.selectDiskByUserid(file.getUser_id());
		disk.setUsedsize(disk.getUsedsize() + file.getSize());
		fileDao.updateDisk(disk);
		return true;
	}

	/**
	 * 向网盘添加文件夹
	 * 
	 * @param file
	 *            须包含:user_id,parent_id,name
	 * @return
	 */
	@Transactional
	public boolean addFolder(MyFile file) {
		file.setPath(fileDao.getPath(file.getParent_id()) + file.getParent_id() + "/");
		file.setSize(0);
		file.setType("folder");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		file.setCreatedate(sdf.format(new Date()));
		if (fileDao.insertMyFile(file) > 0) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 删除文件
	 * 
	 * @param idlist
	 * @return
	 */
	@Transactional
	public boolean delectFile(String[] idlist) {
		List<MyFile> filelist = new ArrayList<MyFile>();
		// 获取文件列
		for (String id : idlist) {
			MyFile file = fileDao.selectMyfileById(new Long(id));
			if (file != null)
				filelist.add(file);
		}
		// 开始删除
		try {
			for (int i=0;i<filelist.size();i++) {
				MyFile file=filelist.get(i);
				if (file.getType().equals("folder")) {
					// 如果为文件夹
					// 找到所有子文件
					List<MyFile> childlist = fileDao.selectMyfileByParent(file.getId());
					for (MyFile child : childlist) {
						filelist.add(child);
					}
					// 删除文件夹
					fileDao.delectMyFileByID(file.getId());
				} else {
					// 如果为文件
					// 删除磁盘文件
					Log.debug("!!!file path:{}", file.getLocation());
					new File(file.getLocation()).delete();
					Log.debug("!!!after delete()");
					// 删除数据库文件
					fileDao.delectMyFileByID(file.getId());
					// 更新网盘信息
					Disk disk = fileDao.selectDiskByUserid(file.getUser_id());
					disk.setUsedsize(disk.getUsedsize() - file.getSize());
					fileDao.updateDisk(disk);
				}
			}
		} catch (Exception e) {
			return false;
		}
		return true;
	}
	
	/**
	 * 下载单个文件
	 * @param id
	 * @return
	 */
	public boolean downloadFile(String id,HttpServletResponse response){
		MyFile myFile=null;
		if(id.equals("zip")){
			//下载打包的文件
//			myFile=fileDao.selectMyfileById(new Long(id));
		}else{
			//下载单个文件
			myFile=fileDao.selectMyfileById(new Long(id));
			if(myFile.getType().equals("folder")){
				//跳转到下载多个文件
			}
		}
		if(myFile==null){
			return false;
		}
		
		//开始下载
		String fileName = "unknown";
		try {
			fileName = URLEncoder.encode(myFile.getName(), "UTF-8").replace("+", "%20");
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
			return false;
		}
		
		response.reset();
        response.setContentType("APPLICATION/OCTET-STREAM");
        response.setHeader("Content-Length", myFile.getSize()+"");
        response.setHeader("Content-Disposition", "attachment;filename=\"" + 
        					myFile.getName() + "\";filename*=utf-8''"+fileName);
        
        PrintWriter out = null;
        FileInputStream in = null;
		try {
			in = new FileInputStream(myFile.getLocation());
			out = response.getWriter();
			int b;
			while ((b = in.read()) != -1) {
				out.write(b);
			}
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}finally{
			if(out!=null){
				out.close();
			}
			if(in!=null){
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return true;
	}
}
