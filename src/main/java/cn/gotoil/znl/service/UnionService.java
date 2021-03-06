package cn.gotoil.znl.service;

import cn.gotoil.znl.web.message.request.PayRequest;
import cn.gotoil.znl.web.message.request.union.*;
import cn.gotoil.znl.web.message.response.union.PayResultResponse;
import cn.gotoil.znl.web.message.response.union.UnionRegisterResponse;

import java.util.Map;
import java.util.TreeMap;

/**
 * Created by Suyj <suyajiang@gotoil.cn> on 2017/4/7.13:57
 */
public interface UnionService {

    Object payRequest2UnionRequest(PayRequest payRequest) throws Exception;


    UnionRegisterResponse unionRegister(UnionRegisterRequest registerRequest) throws Exception;

    Object unionRegister(String puid)  throws Exception;

    String orderSubmit(OrderSubmitRequest orderSubmitRequest) throws Exception;

    String orderQuery(OrderQueryRequest orderQueryRequest) throws Exception;

    String batchOrderQuery(BatchOrderQueryRequest batchOrderQueryRequest) throws Exception;

    String refund(RefundRequest refundRequest) throws Exception;

    String refundStatus(RefundStatusRequest refundStatusRequest) throws Exception;

    Map getWxSession(String wxJsCode,WxPayRequest wxPayRequest);
/*
    Map<String,String> unionWxPay(long trxamt, String reqsn, String paytype, String body, String remark, String acct, String authcode,
                                  String limit_pay) throws Exception;*/

    String getWechatGrantUrl();

    Map<String,String> unionWxPay(WxPayRequest wxPayRequest, String openId
           /* long trxamt,String reqsn,String paytype,String body,String remark,String acct,String authcode,
                                         String limit_pay*/) throws Exception;

    Object parseBatchOrderStr(String x);

    void processWechatNotify(TreeMap<String, String> params) throws Exception;

    void processAllinpayNotify(PayResultResponse payResult);

    AppPayRequest payRequest2UnionSdkRequest(PayRequest request);

    WxPayRequest payRequest2UnionWechatRequest(PayRequest request);
}
