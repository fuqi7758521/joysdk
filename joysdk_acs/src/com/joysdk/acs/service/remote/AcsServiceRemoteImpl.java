package com.joysdk.acs.service.remote;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.joysdk.acs.exception.AcsException;
import com.joysdk.acs.service.ResourceService;
import com.joysdk.common.acs.model.Resource;
import com.joysdk.common.acs.service.remote.AcsServiceRemote;
import com.joysdk.common.exception.RemoteException;

@Service(value="acsRemoteService")
public class AcsServiceRemoteImpl implements AcsServiceRemote {

	@Autowired
	private ResourceService resourceService;
	
	@Override
	public List<Resource> getMenusByUserId(Long userId) throws RemoteException{
		try {
			return resourceService.getMenusByUserId(userId);
		} catch (AcsException e) {
			e.printStackTrace();
			throw new RemoteException();
		}
	}

	@Override
	public List<Resource> getUrlsByUserId(Long userId) throws RemoteException{
		try {
			return resourceService.getUrlsByUserId(userId);
		} catch (AcsException e) {
			e.printStackTrace();
			throw new RemoteException();
		}
	}

}
