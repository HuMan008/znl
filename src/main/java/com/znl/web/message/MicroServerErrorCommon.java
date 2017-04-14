/*
 * Copyright (C) 2016.  Iusworks, Inc - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Dolphin com.iusworks.dolphin.web.message.DolphinErrorCommon
 *
 * Suyj <yizhishita@126.com>,  November 2016
 *
 * LastModified: 11/4/16 3:46 PM
 *
 */

package com.znl.web.message;


import com.znl.web.message.MicroServerLogicError;

public enum MicroServerErrorCommon implements MicroServerLogicError {
    Forbidden(403, "Forbidden"),
    NotFound(404, "Not Found"),
    Unknown(9999, "Unknow Error");


    private int code;

    private String message;


    MicroServerErrorCommon(int code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
