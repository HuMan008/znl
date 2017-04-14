/*
 * Copyright (C) 2016.  Iusworks, Inc - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Airjet com.iusworks.airjet.common.tools.stream.StreamUtils
 *
 * Suyj <yizhishita@126.com>,  August 2016
 *
 * LastModified: 8/30/16 1:20 AM
 *
 */

package com.znl.common.tools.encoder;


import java.io.*;

public class StreamUtils {

    public final static String stringFromInputStream(InputStream in) throws IOException {
        if (null == in) {
            return null;
        }
        StringBuilder stringBuilder = new StringBuilder();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
        char[] charBuffer = new char[512];
        int bytesRead;
        while ((bytesRead = bufferedReader.read(charBuffer, 0, charBuffer.length)) > 0) {
            stringBuilder.append(charBuffer, 0, bytesRead);
        }
        return stringBuilder.toString();
    }

    public final static byte[] bytesFromInputStream(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        byte[] b = new byte[1024];
        int bytesRead;
        while ((bytesRead = inputStream.read(b, 0, b.length)) > 0) {
            byteArrayOutputStream.write(b, 0, bytesRead);
        }
        return byteArrayOutputStream.toByteArray();
    }

}
