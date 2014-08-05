package com.joysdk.common.acs.model;

/**
 * 资源实体类
 * 
 * @author qi.fu@yunyoyo.cn
 * @date 2014-07-16 15:02
 */
public class Resource {

	private Long id;

	private Long pid;

	private String url;

	private String des;

	// 资源类型 0: 菜单 , 1: url
	private Integer type;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getPid() {
		return pid;
	}

	public void setPid(Long pid) {
		this.pid = pid;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getDes() {
		return des;
	}

	public void setDes(String des) {
		this.des = des;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

}
