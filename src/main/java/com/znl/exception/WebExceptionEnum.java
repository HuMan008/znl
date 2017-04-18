/*
 * Copyright (C) 2016.  Iusworks, Inc - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Airjet com.iusworks.airjet.web.exception.WebExceptionEnum
 *
 * Suyj <yizhishita@126.com>,  August 2016
 *
 * LastModified: 8/25/16 3:06 PM
 *
 */

package com.znl.exception;


public enum WebExceptionEnum {

    NetConnectTimeOut(1001,"Connection timed out") ,

    SignatureError(9000, "Signature verify faild"),

    NoSupportWechatCode(9007," wx code is ERROR"),
    ValidateError(9100, "Input Validate Error"),
    ;

    private int code;

    private String message;

    WebExceptionEnum(int code, String message) {
        this.message = message;
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
