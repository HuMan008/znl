/*
 * Copyright (C) 2017.  Iusworks, Inc - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Dolphin cn.gotoil.dolphin.web.helper.UserAgentAnalyzer
 *
 * cluries <cluries@me.com>,  February 2017
 *
 * LastModified: 1/4/17 10:14 AM
 *
 */

package cn.gotoil.znl.web.helper;

import cn.gotoil.znl.model.enums.OS;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by cluries on 07/11/2016.
 * <p>
 * PubC/$appVersion($appBuild) ($OS $OSVersion;$Device;) Api-$ApiVersion
 */
public class UserAgentAnalyzer {

    private OS os = OS.Unknow;

    private String osVersion;

    private String appVersion;

    private Integer appBuild;


    private String device;

    private Integer apiVersion;

    private String userAgent = null;

    private boolean validated = false;


    private static final Pattern catchPattern = Pattern.compile("^PubC/" +
            "([\\d\\\\.]+?)\\((\\d+)\\)\\s+" +
            "\\((iOS|Android)\\s+([\\d\\\\.\\w\\-_\\s]+?)\\s*;" +
            "\\s*([\\w\\s\\-\\\\._]+?)\\s*;\\s*\\)" +
            "\\s+Api-(\\d+)$");


    public UserAgentAnalyzer(String userAgent) {
        this.userAgent = userAgent;
        analyze();
    }

    private void analyze() {
        if (userAgent == null ) {
            return;
        }

        Matcher matcher = catchPattern.matcher(userAgent);

        if (matcher.find() && matcher.groupCount() >= 6) {
            validated = true;
            appVersion = matcher.group(1);
            appBuild = Integer.valueOf(matcher.group(2));
            os = OS.fromName(matcher.group(3));
            osVersion = matcher.group(4);
            device = matcher.group(5);
            apiVersion = Integer.valueOf(matcher.group(6));
        }
    }


    public OS getOs() {
        return os;
    }

    public String getAppVersion() {
        return appVersion;
    }

    public Integer getAppBuild() {
        return appBuild;
    }

    public String getOsVersion() {
        return osVersion;
    }

    public String getDevice() {
        return device;
    }

    public Integer getApiVersion() {
        return apiVersion;
    }

    public String getUserAgent() {
        return userAgent;
    }


    public boolean isValidated() {
        return validated;
    }

    @Override
    public String toString() {
        return "UserAgentAnalyzer{" +
                "os=" + os +
                ", osVersion='" + osVersion + '\'' +
                ", appVersion='" + appVersion + '\'' +
                ", appBuild=" + appBuild +
                ", device='" + device + '\'' +
                ", apiVersion=" + apiVersion +
                ", validated=" + validated +
                '}';
    }
//
//    public static void main(String args[]) {
//        String ua = "PubC/1.2.1(1022) (iOS 10.0.1;iPhone 6 Plus;) Api-1";
//        UserAgentAnalyzer userAgentAnalyzer = new UserAgentAnalyzer(ua);
//        System.out.print(userAgentAnalyzer);
//    }
}
