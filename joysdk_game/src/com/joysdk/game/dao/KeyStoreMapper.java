package com.joysdk.game.dao;

import java.util.List;

import com.joysdk.game.model.KeyStore;


public interface KeyStoreMapper {

    public void createKeyStore(KeyStore keystore);
    
    public List<KeyStore> getKeyStoresByCpId(int cpId);
    
    public KeyStore getKeyStoresByFileId(String fileId);
    
    public void deleteKeyStoresById(int id);
}
