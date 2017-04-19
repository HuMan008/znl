package cn.gotoil.znl.service;

import cn.gotoil.znl.web.message.request.alipay.AlipayPayRequest;
import com.alipay.api.AlipayApiException;
import cn.gotoil.znl.web.message.request.alipay.AlipayQueryRequest;

/**
 * Created by wh on 2017/4/18.
 */
public interface AlipayService {

    /**
     *  app支付
     * **/
    String  app_pay(AlipayPayRequest alipayPayRequest) throws AlipayApiException ;

    /***
     * wap支付
     */
    String wap_pay(AlipayPayRequest alipayPayRequest) throws AlipayApiException ;

    /**
     *  支付结果查询
     * **/
    String query( AlipayQueryRequest alipayQueryRequest ) throws AlipayApiException ;
}
