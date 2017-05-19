package cn.gotoil.znl.config.property;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

/**
 * Created by Suyj <suyajiang@gotoil.cn> on 2017/4/14.16:50
 * <p>
 * springboot yml 不支持静态变量通过这种方法来处理。
 */
@Configuration
public class ConstantsInitializer {
    //new -------------------------------------------------------------------------------------start


    @Value("${unionconfig.wechat.sessionHost}")
    private String union_wechat_sessionHost;
    @Value("${unionconfig.wechat.grantType}")
    private String union_wechat_grantType;
    @Value("${unionconfig.wechat.notifyUrl}")
    private String union_wechat_notifyUrl;
    @Value("${unionconfig.wechat.apiUrl}")
    private String union_wechat_apiUrl;


    @Value("${unionconfig.sdk.notifyUrl}")
    private String union_sdk_notifyUrl;

    @Value("${unionconfig.gateway.register}")
    private String union_gateway_register;
    @Value("${unionconfig.gateway.ordersubmit}")
    private String union_gateway_ordersubmit;
    @Value("${unionconfig.gateway.dopickup}")
    private String union_gateway_dopickup;
    @Value("${unionconfig.gateway.receiveurl}")
    private String union_gateway_receiveurl;
    @Value("${unionconfig.gateway.orderquery}")
    private String union_gateway_orderquery;
    @Value("${unionconfig.gateway.orderquerybatch}")
    private String union_gateway_orderquerybatch;
    @Value("${unionconfig.gateway.refund}")
    private String union_gateway_refund;
    @Value("${unionconfig.gateway.refundstatus}")
    private String union_gateway_refundstatus;




    //    new      --------------------------------------------------------------------------------------end
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
    @Value("${allinpay.gatewayconsts.url_dopickup}")
    private String url_dopickup;
    @Value("${allinpay.gatewayconsts.url_receiveurl}")
    private String url_receiveurl;
    @Value("${allinpay.gatewayconsts.url_orderquery}")
    private String url_orderquery;
    @Value("${allinpay.gatewayconsts.url_orderquerybatch}")
    private String url_orderquerybatch;
    @Value("${allinpay.gatewayconsts.url_refund}")
    private String url_refund;
    @Value("${allinpay.gatewayconsts.url_refundstatus}")
    private String url_refundstatus;


    @Value("${allinpay.sdk.url_appreceiveurl}")
    private String url_appreceiveurl;
    @Value("${allinpay.sdk.merchantid}")
    private String skdMerchantId;
    @Value("${allinpay.sdk.merchantkey}")
    private String sdkMerchantkey;


    @PostConstruct
    public void initConstans() {
        SybConstants.SYB_CUSID = syb_cusid;
        SybConstants.SYB_APPID = syb_appid;
        SybConstants.SYB_APPKEY = syb_appkey;
        SybConstants.SYB_APIURL = syb_apiurl;

        SybConstants.GateWayConsts.MERCHANTID = merchantid;
        SybConstants.GateWayConsts.MERCHANTKEY = merchantkey;
        SybConstants.GateWayConsts.URL_UNIONREGISTER = url_unionregister;
        SybConstants.GateWayConsts.URL_ORDERSUBMIT = url_ordersubmit;
        SybConstants.GateWayConsts.URL_ORDERQUERY = url_orderquery;
        SybConstants.GateWayConsts.URL_ORDERQUERYBATCH = url_orderquerybatch;
        SybConstants.GateWayConsts.URL_REFUND = url_refund;
        SybConstants.GateWayConsts.URL_REFUNDSTATUS = url_refundstatus;
        SybConstants.GateWayConsts.URL_DOPICKUP = url_dopickup;
        SybConstants.GateWayConsts.URL_RECEIVEURL = url_receiveurl;


        SybConstants.SDK.URL_APPRECEIVEURL = url_appreceiveurl;
        SybConstants.SDK.MERCHANTID = skdMerchantId;
        SybConstants.SDK.MERCHANTKEY = sdkMerchantkey;

// new -----------------------------------------------------------------------------------------start
//        通联微信JS
        UnionConsts.WechatJs.aipUrl = union_wechat_apiUrl;
        UnionConsts.WechatJs.sessionHost = union_wechat_sessionHost;
        UnionConsts.WechatJs.grantType = union_wechat_grantType;
        UnionConsts.WechatJs.notifyUrl = union_wechat_notifyUrl;

//        通联SDK
        UnionConsts.SDK.notifyUrl = union_sdk_notifyUrl;

//        通联网关
        UnionConsts.GateWay.register = union_gateway_register;
        UnionConsts.GateWay.orderSubmit = union_gateway_ordersubmit;
        UnionConsts.GateWay.pick = union_gateway_dopickup;
        UnionConsts.GateWay.receive = union_gateway_receiveurl;
        UnionConsts.GateWay.orderQuery = union_gateway_orderquery;
        UnionConsts.GateWay.orderBatchQuery = union_gateway_orderquerybatch;
        UnionConsts.GateWay.refund = union_gateway_refund;
        UnionConsts.GateWay.refundStatusQuery = union_gateway_refundstatus;


//new ---------------------------------------------------------------------------------------------------end
    }
}
