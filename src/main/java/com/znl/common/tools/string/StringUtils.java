/*
 * Copyright (C) 2016.  Iusworks, Inc - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Airjet com.iusworks.airjet.common.tools.string.StringUtils
 *
 * Suyj <yizhishita@126.com>,  August 2016
 *
 * LastModified: 8/30/16 9:42 AM
 *
 */

package com.znl.common.tools.string;


import com.znl.common.tools.encoder.Hash;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;

import javax.servlet.http.HttpServletRequest;
import java.security.MessageDigest;
import java.util.Date;
import java.util.Random;

public class StringUtils {

    public static String repeat(String base, int repeat) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < repeat; i++) {
            stringBuilder.append(base);
        }
        return stringBuilder.toString();
    }

    public static String md5Str(String str) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(str.getBytes());
            byte[] b = md.digest();
            int i;

            StringBuffer buf = new StringBuffer("");
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0)
                    i += 256;
                if (i < 16)
                    buf.append("0");
                buf.append(Integer.toHexString(i));
            }
            return buf.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }

    }

     //生成一个唯一ID
    public static String createUid(HttpServletRequest request) {
//        String startStr = request.getParameter("APPKEY")==null?"":request.getParameter("APPKEY").toString();
        String hash =Hash.md5(request.getRemoteHost()+ RandomStringUtils.randomAlphabetic(16)+new Date().getTime());
        return hash;
    }
}
