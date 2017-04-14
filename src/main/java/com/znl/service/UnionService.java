package com.znl.service;

import com.znl.web.message.request.union.*;
import com.znl.web.message.response.union.OrderQueryResponse;
import com.znl.web.message.response.union.UnionRegisterResponse;

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
}
