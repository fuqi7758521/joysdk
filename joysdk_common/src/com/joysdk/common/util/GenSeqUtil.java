package com.joysdk.common.util;

import java.util.Date;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.commons.lang.StringUtils;

/**
 * 生成唯一序列号工具类
 * @author Sunxc
 *
 */
public class GenSeqUtil {

    private static final ReentrantLock orderIdLock=new ReentrantLock();

    private static int serialNum=0;

    private static Random rd=new Random(47);
    
    private static final char[] CHARS=new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f',
        'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D',
        'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};

    /**
     * 生成订单号
     * @param spno
     * @param channelId
     * @param gameId
     * @param serverId
     * @param orderlength 目标订单的长度
     * @return
     */
    public static String genOrderId(Long channelId, Long gameId, Long serverId, Long orderlength) {
        int gameLength=4;
        String channelid=channelId.toString();
        while(channelid.length() < 4) {
            channelid="0" + channelid;
        }

        String gid=gameId.toString() == null ? "" : gameId.toString();
        if(orderlength != null) {
            gameLength=4 - (25 - orderlength.intValue());
        }

        while(gid.length() < gameLength) {
            gid="0" + gid;
        }
        String sid=serverId.toString() == null ? "" : serverId.toString();
        while(sid.length() < 4) {
            sid="0" + sid;
        }
        StringBuilder sb=new StringBuilder(channelid);
        sb.append(gid);
        sb.append(sid);

        try {
            orderIdLock.lock();
            int m=serialNum / CHARS.length;
            int n=serialNum % CHARS.length;

            sb.append(CHARS[m]);
            sb.append(CHARS[n]);
            if(serialNum >= (62 * 62 - 1)) {
                serialNum=0;
            } else {
                serialNum++;
            }
            String temp=Long.toString(System.currentTimeMillis(), 36);
            sb.append(temp);
        } finally {
            orderIdLock.unlock();
        }
        return sb.toString();
    }

    /**
     * 生成订单号(全数字)
     * @param spno
     * @param channelId
     * @param gameId
     * @param serverId
     * @param orderlength 目标订单的长度
     * @return
     */
    public static String getOrderIdFullNumberic(Long channelId, Long gameId, Long serverId, Long orderlength) {
        int gameLength=3;
        String channelid=channelId.toString();
        while(channelid.length() < 3) {
            channelid="0" + channelid;
        }

        String gid=gameId.toString() == null ? "" : gameId.toString();
        if(orderlength != null) {
            gameLength=3 - (25 - orderlength.intValue());
        }

        while(gid.length() < gameLength) {
            gid="0" + gid;
        }
        String sid=serverId.toString() == null ? "" : serverId.toString();
        while(sid.length() < 3) {
            sid="0" + sid;
        }
        StringBuilder sb=new StringBuilder(channelid);
        sb.append(gid);
        sb.append(sid);

        try {
            orderIdLock.lock();
            sb.append(System.currentTimeMillis());// 拼接当前时间戳
        } finally {
            orderIdLock.unlock();
        }
        return sb.toString();
    }

    /**
     * 获取订单号</br> 订单号的格式为{MMddHHmmssSSS}+{自增码}
     * @return {MMddHHmmssSSS}+{自增码}
     */
    public static String getSequenceString() {
        try {
            orderIdLock.lock();

            StringBuilder sb=new StringBuilder();
            String time=DateUtil.formatDateTime(new Date(), "MMddHHmmssSSS");

            sb.append(time);
            sb.append(serialNum++);
            sb.append(rd.nextInt(99)+1);

            return sb.toString();
        } finally {
            orderIdLock.unlock();
        }
    }
    
    public static String getUUID() {  
        UUID uuid = UUID.randomUUID();  
        String str = uuid.toString();  
        // 去掉"-"符号  
//        String temp = str.substring(0, 8) + str.substring(9, 13) + str.substring(14, 18) + str.substring(19, 23) + str.substring(24);  
        return StringUtils.replace(str, "-", "");  
    }  
    
    public static void main(String[] args) {
        for(int i=0;i<100;i++){
            String s=getSequenceString();
            System.out.println(s);
        }
    }
}
