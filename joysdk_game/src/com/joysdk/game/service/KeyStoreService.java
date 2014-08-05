package com.joysdk.game.service;

import java.util.List;

import com.joysdk.game.model.KeyStore;


public interface KeyStoreService {
    public void createKeyStore(KeyStore keystore);
    
    public List<KeyStore> getKeyStoresByCpId(int cpId);
    
    public KeyStore getKeyStoresByFileId(String fileId);

    public void deleteKeyStoresById(int id);
}
