package cn.gotoil.znl.service;

import cn.gotoil.znl.web.message.request.PayRequest;
import com.alipay.api.AlipayApiException;

import java.io.UnsupportedEncodingException;

/**
 * Created by wh on 2017/5/11.
 */
public interface CommonPayService {

    String  wapPay(PayRequest request);

    String  sdkPay(PayRequest request) throws AlipayApiException, UnsupportedEncodingException;

}