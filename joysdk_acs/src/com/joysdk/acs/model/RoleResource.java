package com.joysdk.acs.model;

/**
 * 角色资源关联实体类
 * @author qi.fu@yunyoyo.cn
 * @date 2014-07-16 15:11
 */
public class RoleResource {
	private Long id;

	private Long roleId;

	private Long ResourceId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public Long getResourceId() {
		return ResourceId;
	}

	public void setResourceId(Long resourceId) {
		ResourceId = resourceId;
	}

}
