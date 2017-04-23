package com.abo.model;

public class MyFile {
	private Long 		id;			//自动生成
	private Long 		user_id;	//user id
	private Long		parent_id;	//上级文件夹,用户目录无
	private String 		name;		//OriginalFilename，无后缀,用户目录#user_id
	private long		size;		//文件夹为0
	private String 		type;		//扩展名，小写；文件夹folder
	private String  	path;		//网盘路径，根/
	private String 		createdate;	//用户目录无
	private String 		location;	//本地定位,文件夹无
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getUser_id() {
		return user_id;
	}
	public void setUser_id(Long user_id) {
		this.user_id = user_id;
	}
	public Long getParent_id() {
		return parent_id;
	}
	public void setParent_id(Long parent_id) {
		this.parent_id = parent_id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public long getSize() {
		return size;
	}
	public void setSize(long size) {
		this.size = size;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getCreatedate() {
		return createdate;
	}
	public void setCreatedate(String createdate) {
		this.createdate = createdate;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	@Override
	public String toString() {
		return "MyFile [id=" + id + ", user_id=" + user_id + ", parent_id=" + parent_id + ", name=" + name + ", size="
				+ size + ", type=" + type + ", path=" + path + ", createdate=" + createdate + ", location=" + location
				+ "]";
	}
	
}
