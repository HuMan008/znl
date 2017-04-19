/*
 * Copyright (C) 2016.  Iusworks, Inc - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Airjet com.iusworks.airjet.web.exception.AirjetWebException
 *
 * Suyj <yizhishita@126.com>,  August 2016
 *
 * LastModified: 8/25/16 3:05 PM
 *
 */

package cn.gotoil.znl.exception;


public class WebException extends MicroServerException {

    public WebException(WebExceptionEnum exenum) {
        super(exenum.getCode(), exenum.getMessage());
    }

    public WebException(ValidatorEnum error) {
        super(error.getCode(),error.getMessage());
    }

}
