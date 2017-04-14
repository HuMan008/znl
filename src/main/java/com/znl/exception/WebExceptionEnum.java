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

    SignatureError(9000, "Signature verify faild"),
    Entegrity(9001, "Request verify failed"),
    Timeout(9002, "Timeout"),
    FlightError(9003, "Unkown User"),
    RandomTokenDuplicated(9004, "Duplicated token."),

    UnknowUser(9005,"Not fund SessionId") ,

    NoSupportJsCode(9007," wx JS code is ERROR"),

    ParseWxUserInfoError(9006,"Parse WxUser Find Error"),

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
