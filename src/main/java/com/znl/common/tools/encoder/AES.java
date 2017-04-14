/**
 * com.iusworks.middleserver.common.tools.date
 * Created by Suyj on 2016/12/19.16:32
 * email :yizhishita@126.com  Tel:15340518770
 */

package com.znl.common.tools.encoder;

//import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.*;
import java.util.Base64;

public class AES {

    private static final String CipherAES_CBC_NoPadding = "AES/CBC/NoPadding";
    private static final String CipherAES_CBC_PKCS7Padding = "AES/CBC/PKCS7Padding";

    public static boolean initialized = false;
    private static final String CipherAES_CBC_PKCS5Padding = "AES/CBC/PKCS5Padding";

    /**
     * @param data
     * @param secret
     * @param iv
     * @return
     */
    public static byte[] decrypt(byte[] data, String secret, byte[] iv) {
        try {
            SecretKeySpec secretKeySpec = new SecretKeySpec(secret.getBytes(),
                    "AES");
            Cipher cipher = Cipher.getInstance(CipherAES_CBC_NoPadding);
            IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivParameterSpec);
            return cipher.doFinal(data);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @param data
     * @param secret
     * @param iv
     * @return
     */
    public static byte[] encrypt(byte[] data, String secret, byte[] iv) {
        try {
            SecretKeySpec secretKeySpec = new SecretKeySpec(secret.getBytes(),
                    "AES");
            IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);
            Cipher cipher = Cipher.getInstance(CipherAES_CBC_PKCS5Padding);
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivParameterSpec);
            return cipher.doFinal(data);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @param data
     * @param secret
     * @return
     */
    public static byte[] simpleDecrypt(String data, String secret) {
        byte[] iv = simpleIVInit(secret);

        try {
            SecretKeySpec secretKeySpec = new SecretKeySpec(secret.getBytes(),
                    "AES");
            Cipher cipher = Cipher.getInstance(CipherAES_CBC_PKCS5Padding);
            IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivParameterSpec);
            return cipher.doFinal(Base64.getDecoder().decode(URLDecoder.decode(data,
                    "UTF-8")));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * @param data
     * @param secret
     * @return
     * @throws UnsupportedEncodingException
     */
    public static String simpleEncrypt(byte[] data, String secret)
            throws UnsupportedEncodingException {
        byte[] iv = simpleIVInit(secret);

        byte[] result = encrypt(data, secret, iv);
        return URLEncoder.encode(Base64.getEncoder().encodeToString(result), "UTF-8");
    }


    /**
     * @param secret
     * @return
     */
    private static byte[] simpleIVInit(String secret) {
        byte[] iv = new byte[16];
        for (byte i = 0; i < 16; i++) {
            if (i < 4) {
                iv[i] = (byte) secret.charAt(i * 2);
            } else if (i < 12) {
                iv[i] = (byte) secret.charAt(i + 8);
            } else {
                iv[i] = (byte) secret.charAt(secret.length() - 16 + i);
            }
        }

        return iv;
    }


    /**
     * AES解密
     *
     * @param content 密文
     * @return
     * @throws InvalidAlgorithmParameterException
     * @throws NoSuchProviderException
     */

    public byte[] decrypt(byte[] content, byte[] keyByte, byte[] ivByte) {

        initialize();

        try {

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding");

            Key sKeySpec = new SecretKeySpec(keyByte, "AES");


            cipher.init(Cipher.DECRYPT_MODE, sKeySpec, generateIV(ivByte));// 初始化

            byte[] result = cipher.doFinal(content);

            return result;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {

            e.printStackTrace();

        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public static void initialize() {

        if (initialized) return;

        //todo
//        Security.addProvider(new BouncyCastleProvider());

        initialized = true;

    }

    //生成iv

    public static AlgorithmParameters generateIV(byte[] iv) throws Exception {

        AlgorithmParameters params = AlgorithmParameters.getInstance("AES");

        params.init(new IvParameterSpec(iv));

        return params;

    }


}
