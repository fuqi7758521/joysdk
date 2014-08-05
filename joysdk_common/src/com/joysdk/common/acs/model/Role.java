package com.joysdk.common.acs.model;

/**
 * 角色实体类
 * @author qi.fu@yunyoyo.cn
 * @date 2014-07-16 14:54
 */
public class Role {
	
	private Long id;
	
	private String name;
	
	private Integer status;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
}
