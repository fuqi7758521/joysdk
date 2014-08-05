package com.joysdk.common.util;

import java.io.FileInputStream;
import java.security.KeyStore;
import java.security.cert.Certificate;

import com.joysdk.common.web.context.SystemProperties;

import sun.security.tools.KeyTool;

public class KeyStoreUtil {

    public static void createKeyStoreFile(String fileName, String alias, String commonName, String orgName, String storePass, String mainPass) throws Exception{
        String[] arstringCommand = new String[] {  
                    "-genkey", // -genkey表示生成密钥  
                    "-validity", // -validity指定证书有效期(单位：天)，这里是36000天  
                    "36500",  
                    "-keysize",//     指定密钥长度  
                    "2048",  
                    "-alias", // -alias指定别名，这里是ss  
                    alias,  
                    "-keyalg", // -keyalg 指定密钥的算法 (如 RSA DSA（如果不指定默认采用DSA）)  
                    "RSA",
                    "-keystore", // -keystore指定存储位置，这里是d:/demo.keystore  
                    SystemProperties.getProperty("keystore.save.dir")+fileName+".keystore", 
                    "-dname",// CN=(名字与姓氏), OU=(组织单位名称), O=(组织名称), L=(城市或区域名称),  
                                // ST=(州或省份名称), C=(单位的两字母国家代码)"  
                    "CN=("+commonName+"), OU=("+orgName+"), O=("+orgName+"), L=(BJ), ST=(BJ), C=(CN)",  
                    "-storepass", // 指定密钥库的密码(获取keystore信息所需的密码)  
                    storePass,
                    "-keypass",// 指定别名条目的密码(私钥的密码)  
                    mainPass,  
                    "-v"// -v 显示密钥库中的证书详细信息  
            };  
        KeyTool.main(arstringCommand);
    }
    
    public static void checkCert(String filepath, String pwd, String alias) throws Exception {
        String keystoreFilename=filepath;
        char[] password=pwd.toCharArray();
        FileInputStream fIn=new FileInputStream(keystoreFilename);
        KeyStore keystore=KeyStore.getInstance("JKS");
        keystore.load(fIn, password);
        Certificate cert=keystore.getCertificate(alias);
        System.out.println(cert);
    }
    
    
    public static void main(String[] args) throws Exception {
        createKeyStoreFile("sssss", "test", "yunyoyo", "yunyoyo", "yunyoyo", "yunyoyo");
//        
//        String[] arstringCommand = new String[] {  
////          "cmd ", "/k", "start", // cmd Shell命令
////                  "keytool",  
//                  "-genkey", // -genkey表示生成密钥  
//                  "-validity", // -validity指定证书有效期(单位：天)，这里是36000天  
//                  "36500",  
//                  "-keysize",//     指定密钥长度  
//                  "1024",  
//                  "-alias", // -alias指定别名，这里是ss  
//                  "yunyoyo",  
//                  "-keyalg", // -keyalg 指定密钥的算法 (如 RSA DSA（如果不指定默认采用DSA）)  
//                  "RSA",
//                  "-keystore", // -keystore指定存储位置，这里是d:/demo.keystore  
//                  "/usr/local/temp/yunyoyo.keystore", 
//                  "-dname",// CN=(名字与姓氏), OU=(组织单位名称), O=(组织名称), L=(城市或区域名称),  
//                              // ST=(州或省份名称), C=(单位的两字母国家代码)"  
//                  "CN=(yunyoyo), OU=(yunyoyo), O=(yunyoyo), L=(BJ), ST=(BJ), C=(CN)",  
//                  "-storepass", // 指定密钥库的密码(获取keystore信息所需的密码)  
//                  "yunyoyo",
//                  "-keypass",// 指定别名条目的密码(私钥的密码)  
//                  "yunyoyo",  
//                  "-v"// -v 显示密钥库中的证书详细信息  
//          };  
//        
//        KeyTool.main(arstringCommand);
    }
}
