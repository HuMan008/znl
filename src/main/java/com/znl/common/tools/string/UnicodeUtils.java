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
 * LastModified: 8/30/16 1:04 AM
 *
 */

package com.znl.common.tools.string;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UnicodeUtils {

    /**
     * @param string
     * @return
     */
    public static String decodeUnicode(String string) {
        Pattern pattern = Pattern.compile("(\\\\u(\\p{XDigit}{4}))");
        Matcher matcher = pattern.matcher(string);
        char ch;
        while (matcher.find()) {
            ch = (char) Integer.parseInt(matcher.group(2), 16);
            string = string.replace(matcher.group(1), ch + "");
        }
        return string;
    }

}
