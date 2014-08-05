package com.joysdk.acs.model;

/**
 * 用户角色关联实体类
 * @author qi.fu@yunyoyo.cn
 * @date 2014-07-16 15:04
 */
public class UserRole {
	
	private Long id;
	
	private Long userId;
	
	private Long roleId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

}
