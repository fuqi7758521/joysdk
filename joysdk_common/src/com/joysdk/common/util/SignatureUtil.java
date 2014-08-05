package com.joysdk.common.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Properties;

import javax.crypto.Cipher;

public class SignatureUtil {

    private static String publicKeyDir=null;

    private static String privateKeyDir=null;

    private static ObjectInputStream oInputStream;

    private static ObjectInputStream oInputStream2;

    static {
        ClassLoader cl=Thread.currentThread().getContextClassLoader();
        InputStream infile=cl.getResourceAsStream("cowboy-common.properties");

        Properties props=new Properties();
        try {
            props.load(infile);

            publicKeyDir=props.getProperty("public.key.dir");
            privateKeyDir=props.getProperty("private.key.dir");
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception {
        String before="tim chen";
        System.err.println("Origianl String: " + before);

        String sigString=signature(before);
        System.err.println("Sig String: " + sigString);

        System.err.println(veriSig(before, sigString));
    }

    public static String signature(String oriString) throws Exception {
        byte[] plainText=oriString.getBytes("UTF8");
        // 获得一个RSA的Cipher类，使用公钥加密
        Cipher cipher=Cipher.getInstance("RSA/ECB/PKCS1Padding");
        // System.out.println("\n" + cipher.getProvider().getInfo());

        // System.out.println("\n用公钥加密...");
        // Cipher.ENCRYPT_MODE意思是加密，从一对钥匙中得到公钥 key.getPublic()
        cipher.init(Cipher.ENCRYPT_MODE, getPublicKey());
        // 用公钥进行加密，返回一个字节流
        byte[] cipherText=cipher.doFinal(plainText);
        // 转化为16进制串
        String after1=byteToHexString(cipherText);
        // System.out.println("用公钥加密完成："+after1);
        return after1;
    }

    // 使用私钥解密
    public static boolean veriSig(String oriString, String sigString) throws Exception {
        byte[] cipherText=hexStringToByte(sigString);

        // 获得一个RSA的Cipher类，使用公钥加密
        Cipher cipher=Cipher.getInstance("RSA/ECB/PKCS1Padding");

        // System.out.println("\n用私钥解密...");
        // Cipher.DECRYPT_MODE意思是解密，从一对钥匙中得到私钥 key.getPrivate()
        cipher.init(Cipher.DECRYPT_MODE, getPrivateKey());
        // 用私钥进行解密，返回一个字节流
        byte[] newPlainText=cipher.doFinal(cipherText);

        String after2=new String(newPlainText, "UTF8");
        // System.out.println("用私钥解密完成："+after2);

        return after2.equals(oriString);
    }

    private static PublicKey getPublicKey() throws IOException, ClassNotFoundException {
        final File pKeyFile=new File(publicKeyDir);
        FileInputStream inputStream=new FileInputStream(pKeyFile);
        oInputStream=new ObjectInputStream(inputStream);

        return (PublicKey)oInputStream.readObject();
    }

    private static PrivateKey getPrivateKey() throws IOException, ClassNotFoundException {
        final File pKeyFile=new File(privateKeyDir);
        FileInputStream inputStream=new FileInputStream(pKeyFile);
        oInputStream2=new ObjectInputStream(inputStream);

        return (PrivateKey)oInputStream2.readObject();
    }

    private static String byteToHexString(byte msg[]) {
        int len=msg.length;

        StringBuffer strBuf=new StringBuffer();
        char aline[]=new char[32];

        for(int i=0; i < 32; i++)
            aline[i]=' ';

        int idx=0;
        int lineIdx=0;
        for(int i=0; i < len; i++) {
            if(i / 16 > lineIdx) {
                lineIdx++;
                idx=0;
                strBuf.append(new String(aline));

                for(int k=0; k < 32; k++)
                    aline[k]=' ';
            }

            int val=msg[i] & 255;
            String str=Integer.toHexString(val).toUpperCase();

            if(str.length() > 1) {
                aline[2 * idx]=str.charAt(0);
                aline[2 * idx + 1]=str.charAt(1);
            } else {
                aline[2 * idx]='0';
                aline[2 * idx + 1]=str.charAt(0);
            }

            idx++;
        }
        if(idx != 0)
            strBuf.append(new String(aline));

        return strBuf.toString().trim();
    }

    private static byte[] hexStringToByte(String s) {
        byte bs[]=new byte[s.length()];
        byte bytes[]=new byte[s.length() / 2];

        for(int i=0; i < s.length(); i++)
            if(s.charAt(i) >= 'A' && s.charAt(i) <= 'F')
                bs[i]=(byte)((s.charAt(i) - 65) + 10);
            else if(s.charAt(i) >= 'a' && s.charAt(i) <= 'f')
                bs[i]=(byte)((s.charAt(i) - 97) + 10);
            else
                bs[i]=(byte)(s.charAt(i) - 48);

        for(int i=0; i < bs.length / 2; i++)
            bytes[i]=(byte)(bs[i * 2] << 4 ^ bs[i * 2 + 1]);

        return bytes;
    }
}
