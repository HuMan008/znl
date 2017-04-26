package cn.gotoil.znl.service;

import cn.gotoil.znl.web.message.request.union.*;
import cn.gotoil.znl.web.message.response.union.UnionRegisterResponse;

import java.util.Map;

/**
 * Created by Suyj <suyajiang@gotoil.cn> on 2017/4/7.13:57
 */
public interface UnionService {
    UnionRegisterResponse unionRegister(UnionRegisterRequest registerRequest) throws Exception;

    String orderSubmit(OrderSubmitRequest orderSubmitRequest) throws Exception;

    String orderQuery(OrderQueryRequest orderQueryRequest) throws Exception;

    String batchOrderQuery(BatchOrderQueryRequest batchOrderQueryRequest) throws Exception;

    String refund(RefundRequest refundRequest) throws Exception;

    String refundStatus(RefundStatusRequest refundStatusRequest) throws Exception;

    Map getWxSession(String wxJsCode);
/*
    Map<String,String> unionWxPay(long trxamt, String reqsn, String paytype, String body, String remark, String acct, String authcode,
                                  String limit_pay) throws Exception;*/

    String getWechatGrantUrl();

    Map<String,String> unionWxPay(WxPayRequest wxPayRequest, String openId
           /* long trxamt,String reqsn,String paytype,String body,String remark,String acct,String authcode,
                                         String limit_pay*/) throws Exception;

    Object parseBatchOrderStr(String x);
}
