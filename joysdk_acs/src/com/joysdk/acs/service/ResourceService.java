package com.joysdk.acs.service;

import java.util.List;

import com.joysdk.acs.criteria.ResourceCriteria;
import com.joysdk.acs.exception.AcsException;
import com.joysdk.common.acs.model.Resource;

public interface ResourceService {
	
	void add(Resource to) throws AcsException;
	
	void update(Resource to) throws AcsException;
	
	void delete(Long id) throws AcsException;
	
	Resource getById(Long id) throws AcsException;
	
	List<Resource> getResources(ResourceCriteria criteria) throws AcsException;
	
	Long getResourcesCnt(ResourceCriteria criteria) throws AcsException;
	
	List<Resource> getMenus() throws AcsException;
	
	List<Resource> getMenusByUserId(Long userId) throws AcsException;
	
	List<Resource> getUrlsByUserId(Long userId) throws AcsException;
}
