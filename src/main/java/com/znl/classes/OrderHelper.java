package com.znl.classes;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Suyj <suyajiang@gotoil.cn> on 2017/4/18.11:34
 */
public class OrderHelper {
    public static String createOrder(HttpServletRequest request) {
        String startStr = request.getParameter("APPKEY")==null?"":request.getParameter("APPKEY").toString();
        if(StringUtils.isEmpty(startStr)){
            startStr = request.getParameterMap().get("APPKEY")==null?"":request.getParameterMap().get("APPKEY").toString();
        }
        if(startStr.length()>8){
            return "";
        }
        if(StringUtils.isNotEmpty(startStr)&& !(Character.isDigit(startStr.charAt(0)) || Character.isLetter(startStr.charAt(0))) ){
            return "";
        }
        if(StringUtils.isEmpty(startStr)){
            return "API_"+ RandomStringUtils.randomNumeric(32) ;
        }
            return startStr+ "_"+RandomStringUtils.randomNumeric(32)  ;

    }
}
