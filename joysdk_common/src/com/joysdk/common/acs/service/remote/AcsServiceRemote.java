package com.joysdk.common.acs.service.remote;

import java.util.List;

import com.joysdk.common.acs.model.Resource;
import com.joysdk.common.exception.RemoteException;

public interface AcsServiceRemote {
	
	List<Resource> getMenusByUserId(Long userId) throws RemoteException;
	
	List<Resource> getUrlsByUserId(Long userId) throws RemoteException;
}
