package com.joysdk.acs.dao;

import java.util.List;

import com.joysdk.acs.criteria.RoleCriteria;
import com.joysdk.common.acs.model.Role;

public interface RoleDAO {
	
	void add(Role to);
	
	void update(Role to);
	
	void delete(Long id);
	
	List<Role> getRoles(RoleCriteria criteria);
	
	Long getRolesCnt(RoleCriteria criteria);

	Role getById(Long id);
}
