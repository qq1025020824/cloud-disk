package com.abo.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
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
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipOutputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import com.abo.config.DiskProperty;
import com.abo.dao.FileDao;
import com.abo.model.Disk;
import com.abo.model.FileMd5;
import com.abo.model.MyFile;
import com.abo.vo.MyFileVO;

@Service
public class FileService {
	@Autowired
	private FileDao fileDao;
	@Autowired
	private DiskProperty diskProperty;
	
	private static final Logger Log = LoggerFactory.getLogger(FileService.class);

	/**
	 * չʾ�û���Ŀ¼
	 * 
	 * @param userid
	 * @param model
	 * @return ��ȷ����model������Ϣpaths,contents������true �����򷵻�false
	 */
	public boolean showUserRoot(Long userid, Model model) {
		// �ҵ��û���Ŀ¼
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
		// ·����
		Stack<MyFileVO> paths = new Stack<MyFileVO>();
		MyFileVO myv = new MyFileVO();
		myv.setId(folder.getId().toString());
		myv.setName("home");
		myv.setSize("-");
		myv.setType(folder.getType());
		myv.setCreatedate(folder.getCreatedate());
		paths.push(myv);
		model.addAttribute("paths", paths);
		// ������
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
				// ������ļ���
				myvc.setSize("-");
			} else {
				// ������ļ�
				// �Զ���size�ӵ�λ
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
	 * չʾ�ļ��� �贫��userid������֤
	 * 
	 * @param folderid
	 * @param model
	 * @return ��ȷ����model������Ϣ������true �����򷵻�false
	 */
	public boolean showFolder(Long folderid, Long userid, Model model) {
		// ��ȡĿ¼
		MyFile curfolder = fileDao.selectMyfileById(folderid);
		if (curfolder == null) {
			// id����
			return false;
		}
		if (!curfolder.getType().equals("folder")) {
			// ���ļ���
			return false;
		}
		if (!curfolder.getUser_id().equals(userid)) {
			// �û���Ȩ��
			return false;
		}
		MyFile folder = curfolder;
		// ·����
		Stack<MyFileVO> paths = new Stack<MyFileVO>();
		while (true) {
			MyFileVO pfile = new MyFileVO();
			pfile.setId(folder.getId().toString());
			if (folder.getParent_id() == null) {
				// �û���Ŀ¼
				pfile.setName("home");
			} else {
				pfile.setName(folder.getName());
			}
			pfile.setSize("-");
			pfile.setType(folder.getType());
			pfile.setCreatedate(folder.getCreatedate());
			paths.push(pfile);
			if (folder.getParent_id() == null) {
				// �ҵ��û���Ŀ¼
				break;
			} else {
				// �Ҹ�Ŀ¼
				folder = fileDao.selectMyfileById(folder.getParent_id());
			}
		}
		model.addAttribute("paths", paths);

		folder = curfolder;
		// ������
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
				// ������ļ���
				myvc.setSize("-");
			} else {
				// ������ļ�
				// �Զ���size�ӵ�λ
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
	 * ��ȡ����ʣ��ռ�
	 * 
	 * @param userid
	 * @return
	 */
	public long getFreeSize(Long userid) {
		Disk disk = fileDao.selectDiskByUserid(userid);
		return disk.getTotalsize() - disk.getUsedsize();
	}

	/**
	 * ����������ļ�
	 * 
	 * @param file
	 *            �����:user_id,parent_id,name,type,size,location
	 * @return ����ɹ�����true,����false �Զ����file�е���Ϣ
	 */
	@Transactional
	public boolean addFile(MyFile file) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		file.setCreatedate(sdf.format(new Date()));
		file.setPath(fileDao.getPath(file.getParent_id()) + file.getParent_id() + "/");
		if (fileDao.insertMyFile(file) == 0) {
			return false;
		}
		// ����������Ϣ
		Disk disk = fileDao.selectDiskByUserid(file.getUser_id());
		disk.setUsedsize(disk.getUsedsize() + file.getSize());
		fileDao.updateDisk(disk);
		return true;
	}
	
	/**
	 * ���FileMd5��Ϣ
	 * @param file_id
	 * @param md5
	 * @return
	 */
	@Transactional
	public boolean addFileMd5(Long file_id,String md5){
		FileMd5 fm5 = new FileMd5();
		fm5.setFile_id(file_id);
		fm5.setMd5(md5);
		if(fileDao.insertFileMd5(fm5)>0){
			return true;
		}else{
			return false;
		}
	}

	/**
	 * ����������ļ���
	 * 
	 * @param file
	 *            �����:user_id,parent_id,name
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
	 * ɾ���ļ�
	 * 
	 * @param idlist
	 * @return
	 */
	@Transactional
	public boolean delectFile(String[] idlist) {
		List<MyFile> filelist = new ArrayList<MyFile>();
		// ��ȡ�ļ���
		for (String id : idlist) {
			MyFile file = fileDao.selectMyfileById(new Long(id));
			if (file != null)
				filelist.add(file);
		}
		// ��ʼɾ��
		try {
			for (int i=0;i<filelist.size();i++) {
				MyFile file=filelist.get(i);
				if (file.getType().equals("folder")) {
					// ���Ϊ�ļ���
					// �ҵ��������ļ�
					List<MyFile> childlist = fileDao.selectMyfileByParent(file.getId());
					for (MyFile child : childlist) {
						filelist.add(child);
					}
					// ɾ���ļ���
					fileDao.delectMyFileByID(file.getId());
				} else {
					// ���Ϊ�ļ�
					//��ѯFileMd5
					List<FileMd5> fm5list=fileDao.selectFileMd5ByMd5(fileDao.selectMd5Byfile(file.getId()));
					// ɾ�����ݿ��ļ�
					fileDao.delectMyFileByID(file.getId());
					//ɾ��md5
					fileDao.deleteFileMd5ByFile(file.getId());
					// ɾ�������ļ�
					if(fm5list.size()<=1){
						Log.debug("!!!file path:{}", file.getLocation());
						File localfile = new File(file.getLocation());
						if(localfile.exists()) localfile.delete();
						Log.debug("!!!after delete()");
					}
					// ����������Ϣ
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
	 * ���ص����ļ�
	 * @param id
	 * @return
	 */
	public boolean downloadFile(String id,HttpServletResponse response){
		MyFile myFile=null;
		if(id.equals("zip")){
			//���ش�����ļ�
			myFile=new MyFile();
			myFile.setName("temp.zip");
			myFile.setLocation(diskProperty.getDiskpath()+"temp.zip");
			File tempfile=new File(myFile.getLocation());
			if(tempfile.exists()){
				myFile.setSize(tempfile.length());
			}else{
				return false;
			}
		}else{
			//���ص����ļ�
			myFile=fileDao.selectMyfileById(new Long(id));
			if(myFile==null){
				return false;
			}
			if(myFile.getType().equals("folder")){
				//��ת�����ض���ļ�
				String[] idlist={id};
				return downloadFiles(idlist, response);
			}
		}
		
		//��ʼ����
		String fileName = "unknown";
		try {
			fileName = URLEncoder.encode(myFile.getName(), "UTF-8").replace("+", "%20");
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
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
	
	/**
	 * ���ض���ļ�
	 * @param idlist
	 * @param response
	 * @return
	 */
	public boolean downloadFiles(String[] idlist,HttpServletResponse response){
		List<MyFile> filelist = new ArrayList<MyFile>();
		// ��ȡ�ļ���
		for (String id : idlist) {
			MyFile file = fileDao.selectMyfileById(new Long(id));
			if (file != null)
				filelist.add(file);
		}
		//�����ļ���
		for(int i=0;i<filelist.size();i++){
			if(filelist.get(i).getType().equals("folder")){
				List<MyFile> templist = fileDao.selectMyfileByParent(filelist.get(i).getId());
				filelist.addAll(templist);
				filelist.remove(i);
				i--;
			}
		}
		if(filelist.isEmpty()){
			return false;
		}
		
		String tmpFileName = "temp.zip";  
        byte[] buffer = new byte[1024];  
        String strZipPath = diskProperty.getDiskpath()+tmpFileName;
        try {
            ZipOutputStream out = new ZipOutputStream(new FileOutputStream(  
                        strZipPath));  
            // ���ص��ļ�����
            for (MyFile file:filelist) {  
                FileInputStream fis = new FileInputStream(file.getLocation()); 
                out.putNextEntry(new ZipEntry(file.getName())); 
                 //����ѹ���ļ��ڵ��ַ����룬��Ȼ��������  
                out.setEncoding("GBK");  
                int len;  
                // ������Ҫ���ص��ļ������ݣ������zip�ļ�  
                while ((len = fis.read(buffer)) > 0) {  
                    out.write(buffer, 0, len);  
                }  
                out.closeEntry();  
                fis.close();  
            }
			out.close();
			downloadFile("zip", response);
			//ɾ����ʱ�ļ�
			File tempfile = new File(strZipPath);
			if (tempfile.exists()) {
				tempfile.delete();
			}
        } catch (Exception e) {
            e.printStackTrace();
        }
		return true;
	}
	
	/**
	 * ͨ��md5��֤�ļ��Ƿ����
	 * @param md5
	 * @return false������,true����
	 */
	public boolean isMd5Exist(String md5){
		List<FileMd5> fmlist=fileDao.selectFileMd5ByMd5(md5);
		if(fmlist.isEmpty()){
			return false;
		}else{
			return true;
		}
	}
	
	
	/**
	 * ͨ��md5��ȡһ��MyFile
	 * @param md5
	 * @return û���򷵻�null
	 */
	public MyFile getFileByMd5(String md5){
		List<FileMd5> fmlist=fileDao.selectFileMd5ByMd5(md5);
		if(fmlist.isEmpty()){
			return null;
		}else{
			return fileDao.selectMyfileById(fmlist.get(0).getFile_id());
		}
	}
}
