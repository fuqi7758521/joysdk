package com.joysdk.acs.service;

import java.util.List;

import com.joysdk.acs.criteria.RoleCriteria;
import com.joysdk.acs.exception.AcsException;
import com.joysdk.common.acs.model.Role;

public interface RoleService {
	
	void add(Role to) throws AcsException;
	
	void update(Role to) throws AcsException;
	
	void delete(Long id) throws AcsException;
	
	Role getById(Long id) throws AcsException;
	
	List<Role> getRoles(RoleCriteria criteria) throws AcsException;
	
	Long getRolesCnt(RoleCriteria criteria) throws AcsException;
}
