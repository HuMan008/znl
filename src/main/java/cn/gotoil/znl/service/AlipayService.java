package cn.gotoil.znl.service;

import cn.gotoil.znl.config.define.AlipayConfig;
import cn.gotoil.znl.web.message.request.alipay.AlipayPayRequest;
import com.alipay.api.AlipayApiException;
import cn.gotoil.znl.web.message.request.alipay.AlipayQueryRequest;

import java.io.UnsupportedEncodingException;

/**
 * Created by wh on 2017/4/18.
 */
public interface AlipayService {

    /**
     *  支付结果查询
     * **/
    String query( String orderVirtualID ) throws AlipayApiException ;

    String wap_pay(String orderVirtualID) throws AlipayApiException, UnsupportedEncodingException;

    /**
     *  app支付
     * **/
    String  app_pay(AlipayPayRequest alipayPayRequest,AlipayConfig alipayConfig) throws AlipayApiException, UnsupportedEncodingException;

    /***
     * wap支付
     */
    String wap_pay(AlipayPayRequest alipayPayRequest,AlipayConfig alipayConfig) throws AlipayApiException, UnsupportedEncodingException;

    /**
     *  支付结果查询
     * **/
    String query( AlipayQueryRequest alipayQueryRequest,AlipayConfig alipayConfig ) throws AlipayApiException ;
}
