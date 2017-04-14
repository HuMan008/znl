package com.znl.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.znl.common.tools.ObjectHelper;
import com.znl.common.tools.http.HttpConnectionUtil;
import com.znl.config.property.SybConstants;
import com.znl.service.UnionService;
import com.znl.web.message.request.union.*;
import com.znl.web.message.response.union.UnionRegisterResponse;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Created by Suyj <suyajiang@gotoil.cn> on 2017/4/7.13:38
 */
@Service
public class UnionServiceImpl implements UnionService {

    /**
     *通联 用户注册请求接口
     * @param registerRequest
     * @return
     * @throws Exception
     */
    @Override
    public UnionRegisterResponse unionRegister(UnionRegisterRequest registerRequest) throws Exception{
        registerRequest.doSign();
        HttpConnectionUtil http = new HttpConnectionUtil(SybConstants.GateWayConsts.URL_UNIONREGISTER);
        http.init();
        byte[] bys = http.postParams(ObjectHelper.introspectStringValueMap(registerRequest), true);
        String result = new String(bys,"UTF-8");
        UnionRegisterResponse registerResponse = JSONObject.parseObject(result  ,UnionRegisterResponse.class);
        return registerResponse;
    }


    /**
     * 订单提交
     * @param orderSubmitRequest
     * @return
     * @throws Exception
     */
    @Override
    public String orderSubmit(OrderSubmitRequest orderSubmitRequest) throws Exception {
        orderSubmitRequest.doSign();
        HttpConnectionUtil http = new HttpConnectionUtil(SybConstants.GateWayConsts.URL_ORDERSUBMIT);
        http.init();
        Map<String,String>  params  = ObjectHelper.introspectStringValueMapValueNotEmpty(orderSubmitRequest);
        byte[] bys = http.postParams(params, true);
        String result = new String(bys,"UTF-8");
        return result;
    }

    /**
     * 订单查询（单个）
     * @param orderQueryRequest
     * @return
     * @throws Exception
     */
    @Override
    public String orderQuery(OrderQueryRequest orderQueryRequest) throws Exception {
        orderQueryRequest.doSign();
        HttpConnectionUtil http = new HttpConnectionUtil(SybConstants.GateWayConsts.URL_ORDERQUERY);
        http.init();
        Map<String,String>  params  = ObjectHelper.introspectStringValueMapValueNotEmpty(orderQueryRequest);
        byte[] bys = http.postParams(params, true);
        String result = new String(bys,"UTF-8");
        return result;
    }

    /**
     * 订单查询（多个）
     * @param batchOrderQueryRequest
     * @return
     * @throws Exception
     */
    @Override
    public String batchOrderQuery(BatchOrderQueryRequest batchOrderQueryRequest) throws Exception {
        batchOrderQueryRequest.doSign();
        HttpConnectionUtil http = new HttpConnectionUtil(SybConstants.GateWayConsts.URL_ORDERQUERYBATCH);
        http.init();
        Map<String,String>  params  = ObjectHelper.introspectStringValueMapValueNotEmpty(batchOrderQueryRequest);
        byte[] bys = http.postParams(params,true);
        String result = new String(bys,"UTF-8");
        return result;
    }


    /**
     * 退款申请
     * @param refundRequest
     * @return
     * @throws Exception
     */
    @Override
    public String refund(RefundRequest refundRequest) throws Exception {
        refundRequest.doSign();
        HttpConnectionUtil http = new HttpConnectionUtil(SybConstants.GateWayConsts.URL_REFUND);
        http.init();
        Map<String,String>  params  = ObjectHelper.introspectStringValueMapValueNotEmpty(refundRequest);
        byte[] bys = http.postParams(params,true);
        String result = new String(bys,"UTF-8");
        //TODO 转成 RefundResponse
        return result;
    }


    /**
     * 退款申请状态查询
     * @param refundStatusRequest
     * @return
     * @throws Exception
     */
    @Override
    public String refundStatus(RefundStatusRequest refundStatusRequest) throws Exception {
        refundStatusRequest.doSign();
        HttpConnectionUtil http = new HttpConnectionUtil(SybConstants.GateWayConsts.URL_REFUNDSTATUS);
        http.init();
        Map<String,String>  params  = ObjectHelper.introspectStringValueMapValueNotEmpty(refundStatusRequest);
        byte[] bys = http.postParams(params,true);
        String result = new String(bys,"UTF-8");
        return result;
    }



}
