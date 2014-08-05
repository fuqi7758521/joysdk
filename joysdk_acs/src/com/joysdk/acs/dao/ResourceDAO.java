package com.joysdk.acs.dao;

import java.util.List;

import com.joysdk.acs.criteria.ResourceCriteria;
import com.joysdk.common.acs.model.Resource;

public interface ResourceDAO {
	
	void add(Resource to);
	
	void update(Resource to);
	
	void delete(Long id);
	
	List<Resource> getResources(ResourceCriteria criteria);
	
	Long getResourcesCnt(ResourceCriteria criteria);
	
	List<Resource> getMenusByUserId(Long userId);
	
	List<Resource> getUrlsByUserId(Long userId);

	Resource getById(Long id);
}
