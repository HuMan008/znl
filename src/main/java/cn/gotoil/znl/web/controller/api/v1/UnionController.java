package cn.gotoil.znl.web.controller.api.v1;

import cn.gotoil.bill.exception.BillException;
import cn.gotoil.bill.web.annotation.Authentication;
import cn.gotoil.bill.web.interceptor.authentication.AuthenticationType;
import cn.gotoil.znl.classes.OrderHelper;
import cn.gotoil.znl.common.tools.ObjectHelper;
import cn.gotoil.znl.common.tools.date.DateUtils;
import cn.gotoil.znl.common.union.SybUtil;
import cn.gotoil.znl.config.property.SybConstants;
import cn.gotoil.znl.config.property.SysConfig;
import cn.gotoil.znl.exception.UnionError;
import cn.gotoil.znl.exception.UnionException;
import cn.gotoil.znl.exception.ZnlError;
import cn.gotoil.znl.service.UnionService;
import cn.gotoil.znl.web.controller.BaseController;
import cn.gotoil.znl.web.message.request.union.*;
import cn.gotoil.znl.web.message.response.union.*;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.bouncycastle.util.encoders.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.net.ConnectException;
import java.util.Date;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by Suyj <suyajiang@gotoil.cn> on 2017/4/7.10:48
 */
@RestController
@Api(value = "通联支付", description = "通联支付")
public class UnionController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(UnionController.class);

    @Autowired
    private UnionService unionService;

    @Autowired
    private SysConfig sysConfig;




    @RequestMapping(value = "towechatpay")
    @Authentication(authenticationType = AuthenticationType.None)
    public Object toPay(@RequestParam @NotNull String code){
        //先登录微信

        WxPayRequest wxOrder = new WxPayRequest();
        ModelAndView modelAndView = new ModelAndView("union/wxorder");
        wxOrder.setAuthCode(code);
        modelAndView.addObject("wxOrder",wxOrder)  ;
        modelAndView.addObject("url","/pay/dopay");
        return modelAndView;
    }




    /**
     * 微信支付
     *
     * @param wxPayRequest
     * @param bindingResult
     * @param httpServletRequest
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "dopay",method = RequestMethod.POST)
    @Authentication(authenticationType = AuthenticationType.None)
    public Object pay(
            @Valid WxPayRequest wxPayRequest,
                      BindingResult bindingResult,
                      HttpServletRequest httpServletRequest) throws Exception {


        Map<String, Object> wxSessionMap = unionService.getWxSession(wxPayRequest.getAuthCode());
        if (wxSessionMap == null || wxSessionMap.isEmpty()) {
//            new WebException(WebExceptionEnum.NoSupportWechatCode);
            return new BillException(UnionError.WxCodeNoSupport);
        }
        //获取异常
        if (wxSessionMap.containsKey("errcode")) {
            return  new UnionException(3101, wxSessionMap.get("errcode").toString());
        }
        String wxOpenId = (String) wxSessionMap.get("openid");
//        String wxOpenId = "oQxUFuCHo8J0HeYa-20oK-MGEYGc"   ;
        String reqsn = String.valueOf(System.currentTimeMillis());

//        Map<String, String> map =unionService.unionWxPay(trxamt,reqsn,"","--body--","--备注--",wxOpenId,"authcode","");
        Map<String, String> map = unionService.unionWxPay(wxPayRequest, wxOpenId);
        if ("SUCCESS".equals(map.get("retcode"))) {
            String payInfo = map.get("payinfo");
            System.out.println(payInfo);
            WxPayInfoResponse wxPayInfoResponse = JSONObject.parseObject(payInfo, WxPayInfoResponse.class);

            ModelAndView modelAndView = new ModelAndView("union/wxpay");
            modelAndView.addObject("appId", wxPayInfoResponse.getAppId());
            modelAndView.addObject("timeStamp", wxPayInfoResponse.getTimeStamp());
            modelAndView.addObject("nonceStr", wxPayInfoResponse.getNonceStr());
            modelAndView.addObject("prepay_id", wxPayInfoResponse.getaPackage());
            modelAndView.addObject("paySign", wxPayInfoResponse.getPaySign());

            return modelAndView;

        } else {
//        }else if("FAIL".equals(map.get("retcode"))){
            return new ModelAndView("union/orderfail");
        }

    }

    @Authentication(authenticationType = AuthenticationType.None)
    @RequestMapping("wxpaynotify")
    public void wxpaynotify(HttpServletRequest request, HttpServletResponse response) throws Exception {
        logger.info("------------------------------------------------接收到通知-------------");
        request.setCharacterEncoding("gbk");//通知传输的编码为GBK
        response.setCharacterEncoding("gbk");
        TreeMap<String, String> params = SybUtil.getParams(request);//动态遍历获取所有收到的参数,此步非常关键,因为收银宝以后可能会加字段,动态获取可以兼容
        logger.info("参数+" + params.toString());
        try {
            boolean isSign = SybUtil.validSign(params, SybConstants.SYB_APPKEY);// 接受到推送通知,首先验签
            logger.info("验签结果" + isSign);
            //验签完毕进行业务处理
            if(isSign){
                unionService.processWechatNotify(params);
            }
        } catch (Exception e) {//处理异常
            // TODO: handle exception
            e.printStackTrace();
        } finally {//收到通知,返回success
            response.getOutputStream().write("success".getBytes());
            response.flushBuffer();
        }
    }

    /**
     * 通联用户注册
     */
    @ResponseBody
    @RequestMapping(value = "unionregister", method = RequestMethod.GET)
    @ApiOperation(value = "通联用户注册")
    public Object unionRegisterAction(@RequestParam(required = false) @Size(max = 32, message = "请传入正确的参数") String puid, HttpServletRequest request,
                                      HttpServletResponse
                                              response) {
        UnionRegisterRequest registerRequest = new UnionRegisterRequest();
        UnionRegisterRespon4API unionRegisterRespon4API = new UnionRegisterRespon4API();
        unionRegisterRespon4API.setPartnerUserId(puid);

        if (StringUtils.isEmpty(puid)) {
            puid = OrderHelper.createUid(request);
            unionRegisterRespon4API.setPartnerUserId(puid);
        }
        registerRequest.setPartnerUserId(puid);
        registerRequest.doSign();

        try {
            UnionRegisterResponse unionRegisterResponse = unionService.unionRegister(registerRequest);

            BeanUtils.copyProperties(unionRegisterResponse, unionRegisterRespon4API);

            return unionRegisterRespon4API;
        } catch (ConnectException e) {
            e.printStackTrace();
            return ZnlError.NetConnectTimeOut;
        } catch (Exception e1) {
            e1.printStackTrace();
            return new BillException(4000, e1.getMessage());
        }
    }

    @Authentication(authenticationType = AuthenticationType.None)
    @RequestMapping(value = "ordersubmit", method = {RequestMethod.GET,RequestMethod.POST})
    public Object orderSubmit(@ModelAttribute @Valid OrderSubmitRequest orderSubmitRequest, HttpServletRequest request, HttpServletResponse response,
                              Model model) throws
            Exception {


//        orderSubmitRequest.setUnionUserId("170410792118546");
//        orderSubmitRequest.setOrderAmount(1);
//        orderSubmitRequest.setProductNum(1);
        orderSubmitRequest.setPickupUrl(SybConstants.GateWayConsts.URL_DOPICKUP);
        orderSubmitRequest.setReceiveUrl(SybConstants.GateWayConsts.URL_RECEIVEURL);    //
        if (StringUtils.isEmpty(orderSubmitRequest.getProductName())) {
            orderSubmitRequest.setProductName("国通石油");
        }
        if (StringUtils.isEmpty(orderSubmitRequest.getOrderNo())) {
            String orderNo = OrderHelper.createOrder(request);
            if (StringUtils.isEmpty(orderNo)) {
                return UnionError.AppIdError;
            }
            orderSubmitRequest.setOrderNo(orderNo);
        }
        if (orderSubmitRequest.getOrderAmount() == 0) {
            return UnionError.OrderAmonutError;
        }
        if (orderSubmitRequest.getProductNum() * orderSubmitRequest.getProductPrice() != 0) {
            if (orderSubmitRequest.getProductNum() * orderSubmitRequest.getProductPrice() != orderSubmitRequest.getOrderAmount()) {
                return UnionError.OrderFeeError;
            }
        }
        if (orderSubmitRequest.getOrderExpireDatetime() == 0) {
            orderSubmitRequest.setOrderExpireDatetime(10);
        }
        if (StringUtils.isEmpty(orderSubmitRequest.getPayType())) {
            orderSubmitRequest.setPayType("0");
        }
        orderSubmitRequest.doSign();

        model.addAttribute("unionOrder", orderSubmitRequest);
        model.addAttribute("url", SybConstants.GateWayConsts.URL_ORDERSUBMIT);//表单提交的url;
        return new ModelAndView("union/ordersubmit");

    }

    /**
     * 订单查询
     *
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "orderquery/{orderDate:^\\d{14}$}/{orderNo:^.*$}", method = RequestMethod.GET)
    public Object orderQueryAction(HttpServletRequest request, HttpServletResponse response, Model model,
                                   @PathVariable String orderDate,
                                   @PathVariable String orderNo
    ) throws Exception {
        OrderQueryRequest orderQueryRequest = new OrderQueryRequest();
//        PayResultResponse{merchantId='008500189990304', version='v1.0', language='1', signType='0', issuerId='', paymentOrderId='201704111646298209', orderNo='order_001', orderDatetime='20170411164619', orderAmount=1, payDatetime='20170411164636', payAmount=1, ext1='<USER>170410792118546</USER>', ext2='', payResult='1', returnDatetime='20170411164642'}
        orderQueryRequest.setOrderDatetime(orderDate);
        orderQueryRequest.setOrderNo(orderNo);

        orderQueryRequest.doSign();

        String reStr = unionService.orderQuery(orderQueryRequest);

        Map map2 = ObjectHelper.stringToMap(reStr);
        OrderQueryResponse orderQueryResponse = (OrderQueryResponse) ObjectHelper.mapToObject(map2, OrderQueryResponse.class);
        return orderQueryResponse;
    }

    /**
     * 订单查询（批量）
     *
     * @param request
     * @param response
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "batchorderquery/{pageNo:^\\d+$}", method = RequestMethod.GET)
    @ResponseBody
    public Object batchOrderQueryAction(HttpServletRequest request, HttpServletResponse response, Model model, @PathVariable int pageNo) throws Exception {
        BatchOrderQueryRequest batchOrderQueryRequest = new BatchOrderQueryRequest();
        batchOrderQueryRequest.setPageNo(pageNo);
        batchOrderQueryRequest.setBeginDateTime(DateUtils.simpleDateFormatter().format(new Date()).replace("-", "") + "00");
        batchOrderQueryRequest.setEndDateTime(DateUtils.simpleDateFormatter().format(new Date()).replace("-", "") + "23");
        batchOrderQueryRequest.doSign();
        String result = unionService.batchOrderQuery(batchOrderQueryRequest);
        String x = new String(Base64.decode(result), "UTF-8");
        System.out.println(x);
        response.setCharacterEncoding("UTF-8");
        Object o = unionService.parseBatchOrderStr(x);
        return o;
    }


    @RequestMapping(value = "refund")
    public String refund(HttpServletRequest request, HttpServletResponse response, Model mode) throws Exception {
        RefundRequest refundRequest = new RefundRequest();
        refundRequest.setOrderNo("order_001");
        refundRequest.setRefundAmount(1);
        refundRequest.setMchtRefundOrderNo("refund_order_001");
        refundRequest.setOrderDatetime("20170411164619");
        System.out.println(unionService.refund(refundRequest));
//        merchantId=008500189990304&version=v2.3&signType=0&orderNo=a001ab1&orderAmount=1&orderDatetime=20170410172329&refundAmount=1&refundDatetime=20170411114424&refundResult=20&mchtRefundOrderNo=refund_001&returnDatetime=20170411114424&signMsg=F8AEA00FBA519BAA25ABC41CDA711EFC
//      merchantId=008500189990304&version=v2.3&signType=0&orderNo=order_001&orderAmount=1&orderDatetime=20170411164619&refundAmount=1&refundDatetime=20170411164933&refundResult=20&mchtRefundOrderNo=refund_order_001&returnDatetime=20170411164933&signMsg=75C181E22A64DC8C055B8F8E627FDA5E
//      todo 转换成 RefundResponse
        return "";
    }

    /**
     * todo  未通过
     *
     * @param request
     * @param response
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "refundstatus")
    public String refundStatus(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
        RefundStatusRequest refundStatusRequest = new RefundStatusRequest();
        refundStatusRequest.setMchtRefundOrderNo("refund_order_001");
        refundStatusRequest.setOrderNo("order_001");
        refundStatusRequest.setRefundDatetime("20170411164619");
        refundStatusRequest.setRefundAmount(1);
        refundStatusRequest.doSign();
        String x = unionService.refundStatus(refundStatusRequest);
        System.out.println(x);
        return "";

    }


    /**
     * 同步支付结果
     *
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "dopickup")
    @Authentication(authenticationType = AuthenticationType.None)
    public String doPickup(PayResultResponse payResultResponse, HttpServletRequest request, HttpServletResponse response, Model model) {
        logger.info("------------------通联支付同步通知------------------------");
        //通联支付同步通知
        System.out.println(payResultResponse.toString());
        //PayResultResponse{merchantId='008500189990304', version='v1.0', language='1', signType='0', issuerId='', paymentOrderId='201704111646298209', orderNo='order_001', orderDatetime='20170411164619', orderAmount=1, payDatetime='20170411164636', payAmount=1, ext1='<USER>170410792118546</USER>', ext2='', payResult='1', returnDatetime='20170411164642'}
        return null;
    }

    @RequestMapping(value = "receiveurl")
    @Authentication(authenticationType = AuthenticationType.None)
    public String receiveUrl(PayResultResponse payResultResponse,HttpServletRequest request, HttpServletResponse response) throws Exception {
        logger.info("------------------通联支付异步通知------------------------");
        request.setCharacterEncoding("gbk");//通知传输的编码为GBK
        response.setCharacterEncoding("gbk");
        TreeMap<String, String> params = SybUtil.getParams(request);//动态遍历获取所有收到的参数,此步非常关键,因为收银宝以后可能会加字段,动态获取可以兼容
        logger.info("参数+" + params.toString());
        try {

//            boolean isSign = SybUtil.validSign(params, SybConstants.SYB_APPKEY);// 接受到推送通知,首先验签
           boolean isSign =params.get("signMsg")!=null &&   params.get("signMsg").toString().equals(payResultResponse.doSign(SybConstants.GateWayConsts.MERCHANTKEY));
            logger.info("验签结果" + isSign);
            //验签完毕进行业务处理
            if(isSign){
                unionService.processAllinpayNotify(payResultResponse);
            }
        } catch (Exception e) {//处理异常
            // TODO: handle exception
            e.printStackTrace();
        } finally {//收到通知,返回success
            response.getOutputStream().write("success".getBytes());
            response.flushBuffer();
        }





        return null;
    }

    @Authentication(authenticationType = AuthenticationType.None)
    @RequestMapping("/sdk/paydata")
    public Object payData(@Valid AppPayRequest appPayRequest,HttpServletRequest request,HttpServletResponse response )throws Exception{
        if(StringUtils.isEmpty(appPayRequest.getOrderNo())){
            appPayRequest.setOrderNo(OrderHelper.createOrder(request));
        }
        if(StringUtils.isEmpty(appPayRequest.getProductName())){
            appPayRequest.setProductName(sysConfig.getDefaultProductName());
        }
        appPayRequest.doSign();
        Map<String,String> map = ObjectHelper.introspectStringValueMapValueNotEmpty(appPayRequest) ;
        return map;
    }


    @RequestMapping("sdk/receiveurl")
    @Authentication(authenticationType = AuthenticationType.None)
    public void skdNotify(PayResultResponse payResultResponse,HttpServletRequest request,HttpServletResponse response) throws Exception{
       logger.info("-------SKD 异步通知---------------");System.out.println(payResultResponse.toString());
        System.out.println("request :\t\n "+request.getParameterMap().toString());


        request.setCharacterEncoding("gbk");//通知传输的编码为GBK
        response.setCharacterEncoding("gbk");

        TreeMap<String, String> params = SybUtil.getParams(request);//动态遍历获取所有收到的参数,此步非常关键,因为收银宝以后可能会加字段,动态获取可以兼容
        logger.info("参数+" + params.toString());
        boolean sisSign =params.get("signMsg")!=null &&   params.get("signMsg").toString().equals(payResultResponse.doSign(SybConstants.SDK.MERCHANTKEY));
        System.out.println("苏亚江的验签结果："+sisSign);







    }


}
