package com.joysdk.game.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.joysdk.game.dao.KeyStoreMapper;
import com.joysdk.game.model.KeyStore;
import com.joysdk.game.service.KeyStoreService;

@Service
public class KeyStoreServiceImpl implements KeyStoreService {

    @Autowired
    private KeyStoreMapper keyStoreMapper;
    
    public void createKeyStore(KeyStore keystore) {
        keyStoreMapper.createKeyStore(keystore);
    }

    public List<KeyStore> getKeyStoresByCpId(int cpId) {
       return keyStoreMapper.getKeyStoresByCpId(cpId);
    }
    
    public KeyStore getKeyStoresByFileId(String fileId){
        return keyStoreMapper.getKeyStoresByFileId(fileId);
    }
    
    public void deleteKeyStoresById(int id) {
        keyStoreMapper.deleteKeyStoresById(id);
    }

}
