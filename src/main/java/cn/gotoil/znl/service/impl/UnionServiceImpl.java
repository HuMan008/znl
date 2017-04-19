package cn.gotoil.znl.service.impl;

import cn.gotoil.znl.common.tools.ObjectHelper;
import cn.gotoil.znl.web.message.request.union.*;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import cn.gotoil.znl.common.tools.http.HttpConnectionUtil;
import cn.gotoil.znl.common.tools.http.HttpUtil;
import cn.gotoil.znl.common.union.SybPayService;
import cn.gotoil.znl.config.property.SybConstants;
import cn.gotoil.znl.config.property.SysConfig;
import cn.gotoil.znl.config.property.WeChatConfig;
import cn.gotoil.znl.service.UnionService;
import cn.gotoil.znl.web.message.response.union.UnionRegisterResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Created by Suyj <suyajiang@gotoil.cn> on 2017/4/7.13:38
 */
@Service
public class UnionServiceImpl implements UnionService {

    @Autowired
    private WeChatConfig weChatConfig;
    @Autowired
    private SysConfig sysConfig;

    private SybPayService sybPayService = new SybPayService();



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
        System.out.println("批量查询请求路径--"+SybConstants.GateWayConsts.URL_ORDERQUERYBATCH);
        Map<String,String>  params  = ObjectHelper.introspectStringValueMap(batchOrderQueryRequest);

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

     @Override
    public Map getWxSession(String wxJsCode){
        StringBuffer sb = new StringBuffer();
        sb.append("appid=").append(weChatConfig.getAppId());
        sb.append("&secret=").append(weChatConfig.getSecret());
//        sb.append("&js_code=").append(wxJsCode);
        sb.append("&code=").append(wxJsCode);
        sb.append("&grant_type=").append(weChatConfig.getGrantType());

        String res = HttpUtil.sendGet(weChatConfig.getSessionHost() ,sb.toString());
        if(res == null || res.equals("")){
            return null;
        }
        System.out.println("响应: "+ res);
        return JSON.parseObject(res, Map.class);
    }


    @Override
    public Map<String,String> unionWxPay(long trxamt,String reqsn,String paytype,String body,String remark,String acct,String authcode,
                                          String limit_pay) throws Exception{
                // limit_pay 暂时只对微信支付有效,仅支持no_credi
       return sybPayService.pay(trxamt, reqsn, paytype.isEmpty()?"W02":paytype, body, remark, acct, authcode, sysConfig.getFullServerHostAddress() ,limit_pay);


    }

    @Override
    public Map<String,String> unionWxPay(WxPayRequest wxPayRequest, String openId
           /* long trxamt,String reqsn,String paytype,String body,String remark,String acct,String authcode,
                                         String limit_pay*/) throws Exception{
        // limit_pay 暂时只对微信支付有效,仅支持no_credi
        return sybPayService.pay(wxPayRequest.getTrxamt(), wxPayRequest.getReqsn(), wxPayRequest.getPayType()
                , wxPayRequest.getBody(), wxPayRequest.getRemark(), openId, wxPayRequest.getAuthCode(),
                weChatConfig.getNotifyUrl(),
                wxPayRequest.getLimitPay());


    }


}
