package com.znl.config.property;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

/**
 * Created by Suyj <suyajiang@gotoil.cn> on 2017/4/14.16:50
 *
 * springboot yml 不支持静态变量通过这种方法来处理。
 */
@Configuration
public class ConstantsInitializer {

    @Value("${allinpay.syb_cusid}")
    private String syb_cusid;
    @Value("${allinpay.syb_appid}")
    private String syb_appid;
    @Value("${allinpay.syb_appkey}")
    private String syb_appkey;
    @Value("${allinpay.syb_apiurl}")
    private String syb_apiurl;
 ;
    @Value("${allinpay.gatewayconsts.merchantid}")
    private String merchantid;
    @Value("${allinpay.gatewayconsts.merchantkey}")
    private String merchantkey;
    @Value("${allinpay.gatewayconsts.url_unionregister}")
    private String url_unionregister;
    @Value("${allinpay.gatewayconsts.url_ordersubmit}")
    private String url_ordersubmit;
    @Value("${allinpay.gatewayconsts.url_orderquery}")
    private String url_orderquery;
    @Value("${allinpay.gatewayconsts.url_orderquerybatch}")
    private String url_orderquerybatch;
    @Value("${allinpay.gatewayconsts.url_refund}")
    private String url_refund;
    @Value("${allinpay.gatewayconsts.url_refundstatus}")
    private String url_refundstatus;

    @PostConstruct
    public void initConstans(){
        SybConstants.SYB_CUSID =syb_cusid;
        SybConstants.SYB_APPID =syb_appid;
        SybConstants.SYB_APPKEY=syb_appkey;
        SybConstants.SYB_APIURL=syb_apiurl;

        SybConstants.GateWayConsts.MERCHANTID=merchantid;
        SybConstants.GateWayConsts.MERCHANTKEY=merchantkey;
        SybConstants.GateWayConsts.URL_UNIONREGISTER=url_unionregister;
        SybConstants.GateWayConsts.URL_ORDERSUBMIT=url_ordersubmit;
        SybConstants.GateWayConsts.URL_ORDERQUERY=url_orderquery;
        SybConstants.GateWayConsts.URL_ORDERQUERYBATCH=url_orderquerybatch;
        SybConstants.GateWayConsts.URL_REFUND=url_refund;
        SybConstants.GateWayConsts.URL_REFUNDSTATUS=url_refundstatus;
    }
}
