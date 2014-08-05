package com.joysdk.common.util;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;


public class DES {

    /**
     * 已知密钥的情况下加密
     */
    public static String encode(String str, String key) throws Exception {
        byte[] rawKey=Base64.decodeBase64(key);
        IvParameterSpec sr=new IvParameterSpec(rawKey);
        DESKeySpec dks=new DESKeySpec(rawKey);
        SecretKeyFactory keyFactory=SecretKeyFactory.getInstance("DES");
        SecretKey secretKey=keyFactory.generateSecret(dks);

        javax.crypto.Cipher cipher=Cipher.getInstance("DES/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, sr);

        byte data[]=str.getBytes("UTF8");
        byte encryptedData[]=cipher.doFinal(data);
        return Base64.encodeBase64String(encryptedData);
    }

    /**
     * 已知密钥的情况下解密
     */
    public static String decode(String str, String key) throws Exception {
        byte[] rawKey=Base64.decodeBase64(key);
        IvParameterSpec sr=new IvParameterSpec(rawKey);
        DESKeySpec dks=new DESKeySpec(rawKey);
        SecretKeyFactory keyFactory=SecretKeyFactory.getInstance("DES");
        SecretKey secretKey=keyFactory.generateSecret(dks);
        Cipher cipher=Cipher.getInstance("DES/CBC/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, secretKey, sr);
        byte encryptedData[]=Base64.decodeBase64(str);
        byte decryptedData[]=cipher.doFinal(encryptedData);
        return new String(decryptedData, "UTF8").trim();
    }
    /**
     * 已知密钥的情况下解密
     * @param str
     * @param key
     * @param isBase64 表示key不要需要进行base64
     * @param charset
     * @throws Exception
     */
    public static String decode(String str,String key,boolean isBase64,String charset)throws Exception {
          if(isBase64){
              return decode(str, key);
          }
            // 生成密钥
           SecretKey deskey = new SecretKeySpec(key.getBytes(), "DESede");
           // 解密
           Cipher c1 = Cipher.getInstance("DESede");
           c1.init(Cipher.DECRYPT_MODE, deskey);
          return new String(c1.doFinal(Base64.decodeBase64(str)),charset);
    }
    
    
    public static void main(String[] args) throws Exception {
        String key="RGhYdjlpaDY=";
        String str="wef90ewif0kjsddsfsdf"; 
        String tt=DES.encode(str, key); //eRt4WK/HMzoCqgtct5ehensIJvzS4tcH
        System.out.println(tt);
    }
}
