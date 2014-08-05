package com.joysdk.acs.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.joysdk.acs.Constant;
import com.joysdk.acs.criteria.ResourceCriteria;
import com.joysdk.acs.dao.ResourceDAO;
import com.joysdk.acs.exception.AcsException;
import com.joysdk.acs.service.ResourceService;
import com.joysdk.common.acs.model.Resource;

@Service
public class ResourceServiceImpl implements ResourceService {

	@Autowired
	private ResourceDAO resourceDAO;

	@Override
	public void add(Resource to) {
		resourceDAO.add(to);
	}

	@Override
	public void update(Resource to) {
		resourceDAO.update(to);
	}

	@Override
	public void delete(Long id) {
		resourceDAO.delete(id);
	}

	@Override
	public List<Resource> getResources(ResourceCriteria criteria) {
		return resourceDAO.getResources(criteria);
	}

	@Override
	public Long getResourcesCnt(ResourceCriteria criteria) {
		return resourceDAO.getResourcesCnt(criteria);
	}

	@Override
	public List<Resource> getMenus() {
		ResourceCriteria criteria = new ResourceCriteria();
		criteria.setType(Constant.RESOURCE_TYPE_MENU);
		return resourceDAO.getResources(criteria);
	}

	@Override
	public List<Resource> getMenusByUserId(Long userId) {
		return resourceDAO.getMenusByUserId(userId);
	}

	@Override
	public List<Resource> getUrlsByUserId(Long userId) {
		return resourceDAO.getUrlsByUserId(userId);
	}

	@Override
	public Resource getById(Long id) throws AcsException {
		return resourceDAO.getById(id);
	}
	

}
