package cn.gotoil.znl.service.impl;

import cn.gotoil.znl.common.tools.ObjectHelper;
import cn.gotoil.znl.common.tools.http.HttpConnectionUtil;
import cn.gotoil.znl.common.tools.http.HttpUtil;
import cn.gotoil.znl.common.union.SybPayService;
import cn.gotoil.znl.config.property.SybConstants;
import cn.gotoil.znl.config.property.SysConfig;
import cn.gotoil.znl.config.property.WeChatConfig;
import cn.gotoil.znl.service.UnionService;
import cn.gotoil.znl.web.message.request.union.*;
import cn.gotoil.znl.web.message.response.union.BatchOrderQueryResponse;
import cn.gotoil.znl.web.message.response.union.UnionRegisterResponse;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
     * 通联 用户注册请求接口
     *
     * @param registerRequest
     * @return
     * @throws Exception
     */
    @Override
    public UnionRegisterResponse unionRegister(UnionRegisterRequest registerRequest) throws Exception {
        registerRequest.doSign();
        HttpConnectionUtil http = new HttpConnectionUtil(SybConstants.GateWayConsts.URL_UNIONREGISTER);
        http.init();
        byte[] bys = http.postParams(ObjectHelper.introspectStringValueMap(registerRequest), true);
        String result = new String(bys, "UTF-8");
        UnionRegisterResponse registerResponse = JSONObject.parseObject(result, UnionRegisterResponse.class);
        return registerResponse;
    }


    /**
     * 订单提交
     *
     * @param orderSubmitRequest
     * @return
     * @throws Exception
     */
    @Override
    public String orderSubmit(OrderSubmitRequest orderSubmitRequest) throws Exception {
        orderSubmitRequest.doSign();
        HttpConnectionUtil http = new HttpConnectionUtil(SybConstants.GateWayConsts.URL_ORDERSUBMIT);
        http.init();
        Map<String, String> params = ObjectHelper.introspectStringValueMapValueNotEmpty(orderSubmitRequest);
        byte[] bys = http.postParams(params, true);
        String result = new String(bys, "UTF-8");
        return result;
    }

    /**
     * 订单查询（单个）
     *
     * @param orderQueryRequest
     * @return
     * @throws Exception
     */
    @Override
    public String orderQuery(OrderQueryRequest orderQueryRequest) throws Exception {
        orderQueryRequest.doSign();
        HttpConnectionUtil http = new HttpConnectionUtil(SybConstants.GateWayConsts.URL_ORDERQUERY);
        http.init();
        Map<String, String> params = ObjectHelper.introspectStringValueMapValueNotEmpty(orderQueryRequest);
        byte[] bys = http.postParams(params, true);
        String result = new String(bys, "UTF-8");
        return result;
    }

    /**
     * 订单查询（多个）
     *
     * @param batchOrderQueryRequest
     * @return
     * @throws Exception
     */
    @Override
    public String batchOrderQuery(BatchOrderQueryRequest batchOrderQueryRequest) throws Exception {
        batchOrderQueryRequest.doSign();
//        HttpConnectionUtil http = new HttpConnectionUtil(SybConstants.GateWayConsts.URL_ORDERQUERYBATCH);
        HttpConnectionUtil http = new HttpConnectionUtil("http://cashier.allinpay.com/mchtoq/index.do");
        http.init();
        System.out.println("批量查询请求路径--" + SybConstants.GateWayConsts.URL_ORDERQUERYBATCH);
        Map<String, String> params = ObjectHelper.introspectStringValueMap(batchOrderQueryRequest);

        byte[] bys = http.postParams(params, true);
        String result = new String(bys, "UTF-8");
        return result;
    }


    /**
     * 退款申请
     *
     * @param refundRequest
     * @return
     * @throws Exception
     */
    @Override
    public String refund(RefundRequest refundRequest) throws Exception {
        refundRequest.doSign();
        HttpConnectionUtil http = new HttpConnectionUtil(SybConstants.GateWayConsts.URL_REFUND);
        http.init();
        Map<String, String> params = ObjectHelper.introspectStringValueMapValueNotEmpty(refundRequest);
        byte[] bys = http.postParams(params, true);
        String result = new String(bys, "UTF-8");
        //TODO 转成 RefundResponse
        return result;
    }


    /**
     * 退款申请状态查询
     *
     * @param refundStatusRequest
     * @return
     * @throws Exception
     */
    @Override
    public String refundStatus(RefundStatusRequest refundStatusRequest) throws Exception {
        refundStatusRequest.doSign();
        HttpConnectionUtil http = new HttpConnectionUtil(SybConstants.GateWayConsts.URL_REFUNDSTATUS);
        http.init();
        Map<String, String> params = ObjectHelper.introspectStringValueMapValueNotEmpty(refundStatusRequest);
        byte[] bys = http.postParams(params, true);
        String result = new String(bys, "UTF-8");
        return result;
    }

    @Override
    public Map getWxSession(String wxJsCode) {
        StringBuffer sb = new StringBuffer();
        sb.append("appid=").append(weChatConfig.getAppId());
        sb.append("&secret=").append(weChatConfig.getSecret());
//        sb.append("&js_code=").append(wxJsCode);
        sb.append("&code=").append(wxJsCode);
        sb.append("&grant_type=").append(weChatConfig.getGrantType());

        String res = HttpUtil.sendGet(weChatConfig.getSessionHost(), sb.toString());
        if (res == null || res.equals("")) {
            return null;
        }
        System.out.println("响应: " + res);
        return JSON.parseObject(res, Map.class);
    }

    @Override
    public String getWechatGrantUrl(){
//        https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx30d2a05655df7746
// &redirect_uri=http%3A%2F%2Ffastweb.guotongshiyou.com%2Fpay%2Fdopay?trxamt=1
// &response_type=code&scope=snsapi_base&state=123&&connect_redirect=1#wechat_redirect
        StringBuilder sb = new StringBuilder("https://open.weixin.qq.com/connect/oauth2/authorize");
        sb.append("?appid=").append(weChatConfig.getAppId())
                .append("&redirect_uri=").append(URLEncoder.encode(sysConfig.getFullServerHostAddress()+"/pay/dopay"))
                .append("&response_type=code")
                .append("&scope=snsapi_base")
                .append("&connect_redirect=1")
                .append("#wechat_redirect");
        return sb.toString();
    }


   /* @Override
    public Map<String, String> unionWxPay(long trxamt, String reqsn, String paytype, String body, String remark, String acct, String authcode,
                                          String limit_pay) throws Exception {
        // limit_pay 暂时只对微信支付有效,仅支持no_credi
        return sybPayService.pay(trxamt, reqsn, paytype.isEmpty() ? "W02" : paytype, body, remark, acct, authcode, sysConfig.getFullServerHostAddress(), limit_pay);


    }*/

    @Override
    public Map<String, String> unionWxPay(WxPayRequest wxPayRequest, String openId
           /* long trxamt,String reqsn,String paytype,String body,String remark,String acct,String authcode,
                                         String limit_pay*/) throws Exception {
        // limit_pay 暂时只对微信支付有效,仅支持no_credi
        return sybPayService.pay(wxPayRequest.getTrxamt(), wxPayRequest.getReqsn(), wxPayRequest.getPayType()
                , wxPayRequest.getBody(), wxPayRequest.getRemark(), openId, "",
                weChatConfig.getNotifyUrl(),
                wxPayRequest.getLimitPay());


    }

    @Override
    public Object parseBatchOrderStr(String x) {

//        ERRORCODE=MOQ00010&ERRORMSG=查询失败
        if (x.contains("ERRORCODE")) {
            return ObjectHelper.stringToMap(x);
        }

 /*       008500189990304|1|1|N
        008500189990304|201704211050485587|58f97386eec61746a26bbbb6|20170421100447|1|20170421105440|1|3C555345523E3137303432313831313132383836373C2F555345523E||1
        008500189990304|201704211050485587|58f97386eec61746a26bbbb6|20170421100447|1|20170421105440|1|3C555345523E3137303432313831313132383836373C2F555345523E||1
        bN0QMp5SQpp83Dm2m4Io70WM5FrbIJZD+JO2YxISIhQXPsacW3I78GKmiPd1Tzl1BOREgIw04mQrUQBOEtLbMq8iutq+q2CTxZi9EaXbUZOewfUokv/i2egpWbVRu5uos8ywWFsd30r9sFLiZR+ooNW/xbBWRZ2p6qBIHFtUHJw=  */
        String[] xx = x.split("\\n");
        BatchOrderQueryResponse batchOrderQueryResponse = new BatchOrderQueryResponse();
        if (xx.length >= 3) {    //至少3行4行；
            String headerStr = xx[0];  //第一行是汇总行
            Pattern headerPattern = Pattern.compile("(\\d+)\\|(\\d+)\\|(\\d+)\\|(\\w)");
            Matcher headerMatcher = headerPattern.matcher(headerStr);
            if (headerMatcher.find() && headerMatcher.groupCount() >= 4) {
                batchOrderQueryResponse.setMerchantNo(headerMatcher.group(1));
                batchOrderQueryResponse.setCurrentPageRows(Integer.parseInt(headerMatcher.group(2)));
                batchOrderQueryResponse.setPages(Integer.parseInt(headerMatcher.group(3)));
                batchOrderQueryResponse.setHasNext("Y".equalsIgnoreCase(headerMatcher.group(4)));
                String signStr = xx[xx.length - 1];
                batchOrderQueryResponse.setSign(signStr);
                Pattern rowPattern = Pattern.compile("(\\d+)\\|(\\d+)\\|(.*)\\|(\\d+)\\|(\\d+)\\|(\\d+)\\|(\\d+)" +
                        "\\|(.*)\\|(.*)\\|(\\d)");
                List<BatchOrderQueryResponse.OrderMsg> list = new ArrayList<>();
                for (int i = 1; i < xx.length - 1; i++) {
                    String rowStr = xx[i];
                    Matcher rowMatcher = rowPattern.matcher(rowStr);
                    if (rowMatcher.find() && rowMatcher.groupCount() >= 10) {
                        BatchOrderQueryResponse.OrderMsg orderMsg = batchOrderQueryResponse.new OrderMsg();
                        orderMsg.setMerchantNo(rowMatcher.group(1));
                        orderMsg.setTlOrderNo(rowMatcher.group(2));
                        orderMsg.setOrderNo(rowMatcher.group(3));
                        orderMsg.setOrderDateTime(rowMatcher.group(4));
                        orderMsg.setOrderAmount(Long.parseLong(rowMatcher.group(5)));
                        orderMsg.setPayDateTime(rowMatcher.group(6));
                        orderMsg.setOrderRealPayAmount(Long.parseLong(rowMatcher.group(7)));
                        orderMsg.setExt1(rowMatcher.group(8));
                        orderMsg.setExt2(rowMatcher.group(9));
                        orderMsg.setResult(rowMatcher.group(10).equals("1") ? "支付成功" : rowMatcher.group(10).equals("0") ? "未支付" : "未知");

                        list.add(orderMsg);
                    } else {      //不匹配跳过
                        continue;
                    }
                }
                batchOrderQueryResponse.setList(list);
                return batchOrderQueryResponse;
            } else {
                return null;
            }


        }
        return null;
    }


}
