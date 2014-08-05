package com.joysdk.common.util;

import java.text.ParseException;
import java.util.Date;
import java.util.Random;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;

import com.joysdk.common.exception.JoySdkException;

public class EncryptUtil {
    

    private static final int XOR_CODE=5;

    /**
     * 利用MD5进行加密
     * @param str 待加密的字符串
     * @return 加密后的字符串
     */
    public static String encoderByMd5(String str) {
        // 确定计算方法
        if(str == null)
            str="";
        return DigestUtils.md5Hex(str).substring(0, 18);
    }

    public static String encodeByRC4(String aInput, String aKey) {
        int[] iS=new int[256];
        byte[] iK=new byte[256];

        for(int i=0; i < 256; i++)
            iS[i]=i;

        int j=1;

        for(short i=0; i < 256; i++) {
            iK[i]=(byte)aKey.charAt((i % aKey.length()));
        }

        j=0;

        for(int i=0; i < 255; i++) {
            j=(j + iS[i] + iK[i]) % 256;
            int temp=iS[i];
            iS[i]=iS[j];
            iS[j]=temp;
        }

        int i=0;
        j=0;
        char[] iInputChar=aInput.toCharArray();
        char[] iOutputChar=new char[iInputChar.length];
        for(short x=0; x < iInputChar.length; x++) {
            i=(i + 1) % 256;
            j=(j + iS[i]) % 256;
            int temp=iS[i];
            iS[i]=iS[j];
            iS[j]=temp;
            int t=(iS[i] + (iS[j] % 256)) % 256;
            int iY=iS[t];
            char iCY=(char)iY;
            iOutputChar[x]=(char)(iInputChar[x] ^ iCY);
        }

        return new String(iOutputChar);

    }

    public static String encodeCookieValue(String aInput, String aKey) {
        String str=encodeByRC4(aInput, aKey);
        String base64Str="";
        try {
            base64Str=Base64.encodeBase64String(ConvertUtil.getBytesFromObject(str));
        } catch(Exception e) {
            e.printStackTrace();
        }
        return base64Str.replace("\r\n", "");
    }

    public static String decodeCookieValue(String base64Str, String aKey) {
        String str="";
        try {
            str=encodeByRC4(ConvertUtil.getObjectFromBytes(Base64.decodeBase64(base64Str)).toString(), aKey);
        } catch(Exception e) {
            e.printStackTrace();
        }
        return str;
    }

    /**
     * base64 加密解密方法
     * @param data
     * @return
     */
    public static String encodeByBase64(String data) {
        byte[] byteData=ConvertUtil.stringToBytes(data);
        return Base64.encodeBase64String(byteData);
    }

    public static String decodeByBase64(String encodeData) {
        byte[] byteData=Base64.decodeBase64(encodeData);
        return ConvertUtil.bytesToString(byteData);
    }

    /**
     * des 加密 解密方法封装
     * @param data
     * @param key
     * @return
     * @throws Exception 
     */
    public static String encodeByDES(String data, String key) throws Exception {
        return DES.encode(data, key);
    }

    public static String decodeByDES(String data, String key) throws Exception {
        return DES.decode(data, key);
    }

    public static String getUserRealPassword(String encodePwd, String key) throws JoySdkException{
//        String encodeKey=encodeByBase64(key);
        try {
            return decodeByDES(encodePwd, key);
        } catch(Exception e) {
            e.printStackTrace();
            throw new JoySdkException("encode error", e);
        }
    }

    /**
     * 获得加密的ticket
     * @param ticket
     * @return String
     */
    public static String encryptedTicket(String ticket) {
        Random rand=new Random();

        String formtDate=DateUtil.formatDateTime(new Date(), DateUtil.FORMAT_SIX);
        final char[] dateChar=formtDate.toCharArray(); // 将日期转化成字符数组

        String[] dateToHex=new String[12]; // 用于存储转化为十六进制的日期
        int randomNum=rand.nextInt(10); // 产生随机数0~9
        for(int j=0; j < dateChar.length; j++) { // 将日期与随机数取异或
            int xor=(dateChar[j] - 0x30) ^ randomNum;
            dateToHex[j]=Integer.toHexString(xor); // 将结果转化成十六进制并注入
        }

        StringBuilder finalTicket=new StringBuilder(ticket); // 生成加入日期后的ticket
        finalTicket.insert(6, dateToHex[0] + dateToHex[1] + dateToHex[2] + dateToHex[3]);
        finalTicket.insert(14, dateToHex[4] + dateToHex[5] + dateToHex[6] + dateToHex[7]);
        finalTicket.insert(22, dateToHex[8] + dateToHex[9] + dateToHex[10] + dateToHex[11]);
        finalTicket.append(Integer.toHexString(randomNum ^ XOR_CODE)); // ticket最后一位加入生成的随机数
        return finalTicket.toString();
    }

    /**
     * 获得解密后的ticket
     * @param ticket
     * @return String
     */
    public static String decipheredTicket(String ticket) {
        StringBuilder decipheredTicket=new StringBuilder();
        decipheredTicket.append(ticket.substring(0, 6)).append(ticket.substring(10, 14)).append(ticket.substring(18, 22)).append(
            ticket.substring(26, 44));
        return decipheredTicket.toString();
    }
    
    /**
     * 获得最后操作时间
     * @param ticket
     * @return Date
     * @throws ParseException
     */
    public static Date getLastActTime(String ticket) throws JoySdkException {
        StringBuilder hexLoginTime=new StringBuilder();
        hexLoginTime.append(ticket.substring(6, 10)).append(ticket.substring(14, 18)).append(ticket.substring(22, 26));// 获取ticket中的日期信息
        final char[] loginTimeChar=hexLoginTime.toString().toCharArray();// 将字符串转为字符数组

        int randomNum=Integer.parseInt(ticket.substring(ticket.length() - 1), 16) ^ XOR_CODE;// 加密时所用的随机数

        Integer[] time=new Integer[12];

        // 将日期信息解密
        for(int j=0; j < loginTimeChar.length; j++) {
            String a=String.valueOf(loginTimeChar[j]);
            time[j]=Integer.parseInt(a, 16) ^ randomNum;// 十六进制转为十进制并求异或，将日期还原
        }

        StringBuilder loginTime=new StringBuilder();
        // 循环组装，获得解密后的登陆时间
        for(int j=0; j < time.length; j++) {
            loginTime.append(time[j]);
        }

        Date fmtLoginTime=DateUtil.toDate(loginTime.toString(), DateUtil.FORMAT_SIX);// 将字符串转Date
        return fmtLoginTime;
    }
    
    
    /**
     * 获取指定长度的密码
     * @param passLenth
     * @return
     */
    public static String getPass(int passLenth) {
        String buffer="0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        StringBuilder sb=new StringBuilder();
        Random r=new Random();
        int range=buffer.length();
        for(int i=0; i < passLenth; i++) {
            // 生成指定范围类的随机数0—字符串长度(包括0、不包括字符串长度)
            sb.append(buffer.charAt(r.nextInt(range)));
        }
        return encodeByBase64(sb.toString());
    }
    
    /**
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        // String inputStr = "测试aqbcd_查重";
//        String inputStr="jfhksjkfljdkljfkjshfukjrefbrh;ejkfndjsnfmcnjdsjknjkshbjfdklsnfm,bnmsmm,bfdmsnfsdnfds,mm,";
//        String key="abcdefg";
//
//        String ss=encodeCookieValue(inputStr, key);
//        System.out.println(Base64.encodeBase64String(ConvertUtil.getBytesFromObject(inputStr)) + "|" + ss.replace("\r\n", ""));
//        System.out.println(decodeCookieValue(ss.replace("\r\n", ""), key));

        // String str = EncodeByRC4(inputStr,key);
        // String base64Str="";
        // base64Str = new BASE64Encoder().encode(ConvertUtil.getBytesFromObject(str));
        // System.out.println(base64Str);
        //
        // //打印加密后的字符串
        // System.out.println(str);
        //
        // //打印解密后的字符串
        // System.out.println(EncodeByRC4(str,key));
        // System.out.println(EncodeByRC4(ConvertUtil.getObjectFromBytes(new
        // BASE64Decoder().decodeBuffer(base64Str)).toString(),key));
        
        String s="C81n6xM7";//STI2RFlMWWo=
        System.out.println(encodeByBase64(s));
        //ZzE4Tmc0TW8=
        //QzgxbjZ4TTc=
    }

}
