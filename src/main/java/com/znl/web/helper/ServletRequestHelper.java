/*
 * Copyright (C) 2017.  Iusworks, Inc - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Dolphin cn.gotoil.dolphin.web.helper.ServletRequestHelper
 *
 * cluries <cluries@me.com>,  February 2017
 *
 * LastModified: 2/14/17 9:36 AM
 *
 */

package com.znl.web.helper;


import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

public class ServletRequestHelper {

    public static String appId() {
        return httpServletRequest().getHeader("AppId");
    }


    public static String userAgent() {
        return httpServletRequest().getHeader("User-Agent");
    }

    public static UserAgentAnalyzer userAgentAnalyzer() {
        return new UserAgentAnalyzer(userAgent());
    }

    public static HttpServletRequest httpServletRequest() {
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        return servletRequestAttributes.getRequest();
    }
}
