package com.joysdk.acs.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.joysdk.acs.criteria.RoleCriteria;
import com.joysdk.acs.dao.RoleDAO;
import com.joysdk.acs.service.RoleService;
import com.joysdk.common.acs.model.Role;

@Service
public class RoleServiceImpl implements RoleService {

	@Autowired
	private RoleDAO roleDAO;
	
	@Override
	public void add(Role to) {
		roleDAO.add(to);
	}

	@Override
	public void update(Role to) {
		roleDAO.update(to);
	}

	@Override
	public void delete(Long id) {
		roleDAO.delete(id);
	}

	@Override
	public List<Role> getRoles(RoleCriteria criteria) {
		return roleDAO.getRoles(criteria);
	}

	@Override
	public Long getRolesCnt(RoleCriteria criteria) {
		return roleDAO.getRolesCnt(criteria);
	}

	@Override
	public Role getById(Long id) {
		return roleDAO.getById(id);
	}

}
