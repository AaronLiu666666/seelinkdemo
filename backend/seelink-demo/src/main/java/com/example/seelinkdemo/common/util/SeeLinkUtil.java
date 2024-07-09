package com.example.seelinkdemo.common.util;

/**
 * @author liurong
 * @version 1.0
 * @date 2024/7/8 20:09
 */
public class SeeLinkUtil {

    public static String XXTeaDEC(String encodeData, String APP_SECRET) throws Exception {
        return XXTea.decrypt(encodeData,"UTF-8",ByteFormat.toHex(APP_SECRET.getBytes()));
    }

}

