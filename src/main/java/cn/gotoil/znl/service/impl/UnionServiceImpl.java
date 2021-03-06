package cn.gotoil.znl.service.impl;

import cn.gotoil.bill.exception.BillException;
import cn.gotoil.znl.adapter.PayAccountAdapter;
import cn.gotoil.znl.adapter.PayConfigTarget;
import cn.gotoil.znl.classes.RedisHashHelper;
import cn.gotoil.znl.common.tools.ObjectHelper;
import cn.gotoil.znl.common.tools.http.HttpConnectionUtil;
import cn.gotoil.znl.common.union.SybPayService;
import cn.gotoil.znl.config.property.SybConstants;
import cn.gotoil.znl.config.property.SysConfig;
import cn.gotoil.znl.config.property.UnionConsts;
import cn.gotoil.znl.config.property.WeChatConfig;
import cn.gotoil.znl.exception.ZnlError;
import cn.gotoil.znl.model.domain.*;
import cn.gotoil.znl.model.enums.EnumPayType;
import cn.gotoil.znl.model.enums.union.PayResult;
import cn.gotoil.znl.model.repository.JPAAccount4UnionGateWayRepository;
import cn.gotoil.znl.model.repository.JPAAppPayAccountRepository;
import cn.gotoil.znl.service.UnionService;
import cn.gotoil.znl.web.message.request.PayRequest;
import cn.gotoil.znl.web.message.request.union.*;
import cn.gotoil.znl.web.message.response.union.BatchOrderQueryResponse;
import cn.gotoil.znl.web.message.response.union.PayResultResponse;
import cn.gotoil.znl.web.message.response.union.UnionRegisterResponse;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipError;

/**
 * Created by Suyj <suyajiang@gotoil.cn> on 2017/4/7.13:38
 */
@Service
public class UnionServiceImpl implements UnionService {

    private Logger logger  = LoggerFactory.getLogger(UnionService.class);

    @Autowired
    private WeChatConfig weChatConfig;
    @Autowired
    private SysConfig sysConfig;

    private SybPayService sybPayService = new SybPayService();

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private JPAAppPayAccountRepository jpaAppPayAccountRepository;

    @Autowired
    private PayAccountAdapter payAccountAdapter;

    @Autowired
    private JPAAccount4UnionGateWayRepository jpaAccount4UnionGateWayRepository;

    @Autowired
    private RedisHashHelper redisHashHelper;

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

    @Override
    public Object unionRegister(String puid)  throws Exception {
        UnionRegisterRequest registerRequest = new UnionRegisterRequest();
        registerRequest.setPartnerUserId(puid);
        registerRequest.doSign();
        HttpConnectionUtil http = new HttpConnectionUtil(SybConstants.GateWayConsts.URL_UNIONREGISTER);
        http.init();
        byte[] bys = http.postParams(ObjectHelper.introspectStringValueMap(registerRequest), true);
        String result = new String(bys, "UTF-8");
//        return JSONObject.toJSONString(ObjectHelper.stringToMap(result) ) ;
        UnionRegisterResponse registerResponse = JSONObject.parseObject(result, UnionRegisterResponse.class);
        return registerResponse;

       /*
        Pattern pattern = Pattern.compile("^[0-9a-z]{1,32}");
        if(pattern.matcher(puid).matches()) {

        }else {
            return UnionError.puidError ;
        }*/
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
    public Map getWxSession(String wxJsCode,WxPayRequest wxPayRequest) {
        StringBuffer sb = new StringBuffer();
        sb.append("appid=").append(wxPayRequest.getWxAppid());
        sb.append("&secret=").append(wxPayRequest.getWxAppKey());
//        sb.append("&js_code=").append(wxJsCode);
        sb.append("&code=").append(wxJsCode);
        sb.append("&grant_type=").append(UnionConsts.WechatJs.grantType);

        String res = HttpConnectionUtil.sendGet(UnionConsts.WechatJs.sessionHost, sb.toString());
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
        return sybPayService.pay(wxPayRequest.getCusid(),wxPayRequest.getAppid(), wxPayRequest.getTrxamt(), wxPayRequest.getReqsn(), wxPayRequest.getPayType()
                , wxPayRequest.getBody(), wxPayRequest.getRemark(), openId, "",
                wxPayRequest.getNotifyUrl(),
                wxPayRequest.getLimitPay(),
                wxPayRequest.getValidtime(),
                wxPayRequest.getAppKey());


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


    /**
     * 微信支付回调业务逻辑处理
     * @param params
     */
    @Override
    public void processWechatNotify(TreeMap<String, String> params) throws Exception {

        NotifyBean notifyBean =(NotifyBean)ObjectHelper.map2Object(params, NotifyBean.class);
        String state = getNotifyKey(notifyBean.getAppid(),notifyBean.getChnltrxid());
        //只有redis里和当前返回的回调为0000时不处理，其他都处理
        if(PayResult.wx_PaySuccess.getCode().equals(notifyBean.getTrxstatus()) &&  PayResult.wx_PaySuccess.getCode().equals(state) ) {
           // do nothing
            System.out.println("------以前处理过，这次我什么都不做----------");
        } else{
            //业务处理  todo
            System.out.println(notifyBean.toString());
            storeNotifyKey(notifyBean.getAppid(),notifyBean.getChnltrxid(),notifyBean.getTrxstatus());
        }



    }


    private String getNotifyKey(String appId,String chnltrxid) {
        String redisKey = redisKey4Notify(appId);
        HashOperations<String, String, String> hashOperations = stringRedisTemplate.opsForHash();
        String state1 = hashOperations.get(redisKey, chnltrxid);

        return state1;
    }

    private String storeNotifyKey(String appId,String chnltrxid,String state) {
        String redisKey = redisKey4Notify(appId);
        HashOperations<String, String, String> hashOperations = stringRedisTemplate.opsForHash();
        hashOperations.put(redisKey,chnltrxid,state);
        return state;
    }

    @Override
    public void processAllinpayNotify(PayResultResponse payResultResponse) {
        String state = getNotifyKey(payResultResponse.getPaymentOrderId(),payResultResponse.getOrderNo());
        if(PayResult.allInPay_PaySuccess.getCode().equals(payResultResponse.getPayResult()) && PayResult.allInPay_PaySuccess.getCode().equals(state)  ){
            
        } else{
            //// TODO: 2017/4/27  
            System.out.println("处理通联异步通知"+ payResultResponse.toString());
            storeNotifyKey(payResultResponse.getPaymentOrderId(),payResultResponse.getOrderNo(),payResultResponse.getPayResult());
        }
       
    }

    private String redisKey4Notify(String appId){
        return "NOTIFY:"+appId;
    }


    @Override
    public WxPayRequest payRequest2UnionWechatRequest(PayRequest payRequest) {
        AppPayAccount appPayAccount = jpaAppPayAccountRepository.findByAppIDAndPayType( payRequest.getAppID(),payRequest.getPayType() );
        if(null ==appPayAccount){
            throw new BillException(ZnlError.AppUnSupportPayeType);
        }


        //找到配置
        PayConfigTarget<Account4UnionWechatJs> payConfigTarget = payAccountAdapter.getPayconfig(EnumPayType.UnionWechatJs,appPayAccount.getPayAccountID());
        if(payConfigTarget==null){
            throw new BillException(ZnlError.GetAccountConfigError);
        }

        WxPayRequest wxPayRequest = new WxPayRequest();
        wxPayRequest.setAppid(payConfigTarget.getConfig().getUnionAppId());
        wxPayRequest.setAppKey(payConfigTarget.getConfig().getUnionAppKey());
        wxPayRequest.setCusid(payConfigTarget.getConfig().getUnionUserId());
        wxPayRequest.setWxAppid(payConfigTarget.getConfig().getWechatAppId());
        wxPayRequest.setWxAppKey(payConfigTarget.getConfig().getWechatAppKey());
        wxPayRequest.setBody(payRequest.getSubject());
        wxPayRequest.setReqsn(payRequest.getOrder_id_actual());
        wxPayRequest.setNotifyUrl(UnionConsts.WechatJs.notifyUrl);
        wxPayRequest.setValidtime(payRequest.getTimeout_minute());

        return wxPayRequest;
    }

    public AppPayRequest payRequest2UnionSdkRequest(PayRequest payRequest){

        AppPayAccount appPayAccount = jpaAppPayAccountRepository.findByAppIDAndPayType( payRequest.getAppID(),payRequest.getPayType() );
        if(null ==appPayAccount){
            throw new BillException(ZnlError.AppUnSupportPayeType);
        }
        //找appUserId是否用过通联网关支付
        String unionId =findUnionUserIdByAppUserId(appPayAccount,payRequest.getAppUserID()) ;
        if(StringUtils.isEmpty(unionId)){
            throw new BillException(ZnlError.UnionRegersterFail);
        }

        //找到配置
        PayConfigTarget<Account4UnionSDK> payConfigTarget = payAccountAdapter.getPayconfig(EnumPayType.UnionSdk,appPayAccount.getPayAccountID());
        if(payConfigTarget==null){
            throw new BillException(ZnlError.GetAccountConfigError);
        }

        AppPayRequest appPayRequest = new AppPayRequest();

        appPayRequest.setReceiveUrl(UnionConsts.SDK.notifyUrl);
        appPayRequest.setMerchantId(payConfigTarget.getConfig().getMerchantId());
        appPayRequest.setOrderNo(payRequest.getOrder_id_actual());
        appPayRequest.setOrderAmount(payRequest.getAmount());
        appPayRequest.setOrderDatetime(cn.gotoil.znl.common.tools.date.DateUtils.SimpleDatetimeNoConnectorFormat().format(DateUtils.addMinutes(new Date(),payRequest.getTimeout_minute())));
        appPayRequest.setUnionUserId(unionId);
        appPayRequest.setProductName(payRequest.getSubject());
        appPayRequest.setExt2(payRequest.getExtra_param());
        try {
            appPayRequest.doSign(payConfigTarget.getConfig().getMerchantKey());
        }catch (UnsupportedEncodingException e){
            logger.error("加签转码错误！");
        }
        return appPayRequest;

    }


    /**
     * 把统一请求订单请求转换成通联需要的订单
     * @param payRequest
     * @return
     */
    @Override
    public Object payRequest2UnionRequest(PayRequest payRequest) throws Exception{
        //通联网关
        if(EnumPayType.UnionGateWay.getCode().equals(payRequest.getPayType())){
            //根据请求找到支付账户
            AppPayAccount appPayAccount = jpaAppPayAccountRepository.findByAppIDAndPayType( payRequest.getAppID(),payRequest.getPayType() );
            if(null ==appPayAccount){
                throw new BillException(ZnlError.AppUnSupportPayeType);
            }
            //找appUserId是否用过通联网关支付
            String unionId =findUnionUserIdByAppUserId(appPayAccount,payRequest.getAppUserID()) ;
            if(StringUtils.isEmpty(unionId)){
                throw new BillException(ZnlError.UnionRegersterFail);
            }

            //找到配置
            PayConfigTarget<Account4UnionGateWay> payConfigTarget = payAccountAdapter.getPayconfig(EnumPayType.UnionGateWay,appPayAccount.getPayAccountID());
            if(payConfigTarget==null){
                throw new BillException(ZnlError.GetAccountConfigError);
            }

            OrderSubmitRequest orderSubmitRequest = new OrderSubmitRequest();
            orderSubmitRequest.setPickupUrl(UnionConsts.GateWay.pick);
            orderSubmitRequest.setReceiveUrl(UnionConsts.GateWay.receive);
            orderSubmitRequest.setMerchantId(payConfigTarget.getConfig().getMerchantId());
            orderSubmitRequest.setOrderNo(payRequest.getOrder_id_actual());
            orderSubmitRequest.setOrderAmount(payRequest.getAmount());
            orderSubmitRequest.setUnionUserId(unionId);
            orderSubmitRequest.setOrderExpireDatetime(payRequest.getTimeout_minute());
            orderSubmitRequest.setProductName(payRequest.getSubject());
            orderSubmitRequest.setPayType("0");
            orderSubmitRequest.doSign(payConfigTarget.getConfig().getMerchantKey());

            return orderSubmitRequest;
        }
        return null;
    }

    public String findUnionUserIdByAppUserId(AppPayAccount payAccount,String appUserId) {
        if(StringUtils.isEmpty(appUserId)) {
            appUserId = RandomStringUtils.randomAlphabetic(32);
        }
        String redisKey = redisKeyForAppUser(payAccount.getAppID(), appUserId);
        String unionUserId = (String)stringRedisTemplate.opsForHash().get(redisKey,appUserId);
        if (unionUserId != null) {
            return unionUserId;
        }
        //找到这个AppId对应的通联网关配置信息
        Account4UnionGateWay account4UnionGateWay=jpaAccount4UnionGateWayRepository.getOne(payAccount.getPayAccountID());

        UnionRegisterRequest unionRegisterRequest = new UnionRegisterRequest();
        unionRegisterRequest.setMerchantId(account4UnionGateWay.getMerchantId());
        unionRegisterRequest.setPartnerUserId(appUserId);
        unionRegisterRequest.doSign(account4UnionGateWay.getMerchantKey());

        HttpConnectionUtil http = new HttpConnectionUtil(SybConstants.GateWayConsts.URL_UNIONREGISTER);
        try {
            http.init();
            byte[] bys = http.postParams(ObjectHelper.introspectStringValueMap(unionRegisterRequest), true);
            String result = new String(bys, "UTF-8");
            UnionRegisterResponse registerResponse = JSONObject.parseObject(result, UnionRegisterResponse.class);
            if("0000".equals(registerResponse.getResultCode()) ||"0006".equals(registerResponse.getResultCode())){
                    stringRedisTemplate.opsForHash().put(redisKey,appUserId,registerResponse.getUserId());
                return  registerResponse.getUserId();
            }else {
                return "";
            }
        }catch (Exception e ){
            logger.error(e.getMessage());
            return "";
        }

    }


    private static String redisKeyForAppUser(String appId,String appUserId) {
        return "unionpay_user_" + appId+":"+appUserId;
    }


}
