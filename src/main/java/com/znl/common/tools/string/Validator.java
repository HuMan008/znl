/*
 * Copyright (C) 2016.  Iusworks, Inc - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Airjet com.iusworks.airjet.common.tools.string.Validator
 *
 * Suyj <yizhishita@126.com>,  September 2016
 *
 * LastModified: 9/13/16 9:14 AM
 *
 */

package com.znl.common.tools.string;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

public class Validator {

    private static final Pattern emailPattern = Pattern.compile("^.+@([A-Za-z0-9-]+\\.)+[A-Za-z]{2}[A-Za-z]*$");
    private static final Pattern mobileFormatPattern = Pattern.compile("^1\\d{10}$");
    private static final Set<String> chinaMobileRange = new HashSet<>();

    static {
        String[] ranges = {"130", "131", "132", "133", "134", "135", "136", "137", "138", "139",
                "145", "147", "149", "150", "151", "152", "153", "155", "156", "157", "158", "159",
                "170", "171", "173", "175", "176", "177", "178",
                "180", "181", "182", "183", "184", "185", "186", "187", "188", "189"};
        for (String r : ranges) {
            chinaMobileRange.add(r);
        }
    }

    public static boolean isEmail(String string) {
        return emailPattern.matcher(string).matches();
    }

    /**
     * https://zh.wikipedia.org/wiki/%E4%B8%AD%E5%9B%BD%E5%86%85%E5%9C%B0%E7%A7%BB%E5%8A%A8%E7%BB%88%E7%AB%AF%E9%80%9A%E8%AE%AF%E5%8F%B7%E7%A0%81
     *
     * @param string
     * @return
     */
    public static boolean isChinaMobile(String string) {
        if (string == null) {
            return false;
        }

        if (string.length() != 11) {
            return false;
        }

        if (!mobileFormatPattern.matcher(string).matches()) {
            return false;
        }

        String be = string.substring(0, 3);
        return chinaMobileRange.contains(be);
    }


    public static boolean isChinaID(String string) {
        return true;
    }


//    public static void main(String[] args) throws Exception {
//
//
//        boolean s = (Boolean) Validator.class.getMethod("isChinaMobile", String.class).invoke(Validator.class, "13637702080");
//        System.out.println(s);
//        System.out.println(isEmail("system@Google.com"));
//        System.out.println(isEmail("system@Google.co.cn"));
//        System.out.println(isEmail("cluries@me.com"));
//        System.out.println(isEmail("system@Google.co..m"));
//    }
//
//    public static Object staticMehtodInvoke(Class clazz, String name, Class<?>... args) {
//        Method method = null;
//        try {
//            method = clazz.getMethod(name, args);
//        } catch (NoSuchMethodException nex) {
//
//        }
//
//        return method;
//    }
}
