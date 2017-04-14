/*
 * Copyright (C) 2016.  Iusworks, Inc - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Airjet com.iusworks.airjet.common.tools.encoder.Hmac
 *
 * Suyj <yizhishita@126.com>,  August 2016
 *
 * LastModified: 8/30/16 1:02 AM
 *
 */

package com.znl.common.tools.encoder;


import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;


public class Hmac {

    private static final String HMAC_SHA1_ALGORITHM = "HmacSHA1";

    public static String SHA1(String string, String key)
            throws SignatureException, NoSuchAlgorithmException, InvalidKeyException {
        SecretKeySpec signingKey = new SecretKeySpec(key.getBytes(), HMAC_SHA1_ALGORITHM);
        Mac mac = Mac.getInstance(HMAC_SHA1_ALGORITHM);
        mac.init(signingKey);
        return ByteHexUtils.byte2hex(mac.doFinal(string.getBytes()));
    }

}