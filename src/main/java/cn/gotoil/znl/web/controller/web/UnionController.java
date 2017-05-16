package cn.gotoil.znl.web.controller.web;

import cn.gotoil.bill.exception.BillException;
import cn.gotoil.bill.web.annotation.Authentication;
import cn.gotoil.bill.web.interceptor.authentication.AuthenticationType;
import cn.gotoil.bill.web.message.BillApiResponse;
import cn.gotoil.znl.adapter.PayAccountAdapter;
import cn.gotoil.znl.adapter.PayConfigTarget;
import cn.gotoil.znl.classes.OrderHelper;
import cn.gotoil.znl.common.tools.ObjectHelper;
import cn.gotoil.znl.common.tools.date.DateUtils;
import cn.gotoil.znl.config.property.SybConstants;
import cn.gotoil.znl.exception.UnionError;
import cn.gotoil.znl.exception.UnionException;
import cn.gotoil.znl.model.domain.Account4UnionGateWay;
import cn.gotoil.znl.model.domain.Account4UnionSDK;
import cn.gotoil.znl.model.enums.EnumPayType;
import cn.gotoil.znl.service.UnionService;
import cn.gotoil.znl.web.controller.BaseController;
import cn.gotoil.znl.web.message.request.union.*;
import cn.gotoil.znl.web.message.response.union.OrderQueryResponse;
import cn.gotoil.znl.web.message.response.union.WxPayInfoResponse;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.bouncycastle.util.encoders.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * Created by Suyj <suyajiang@gotoil.cn> on 2017/5/2.9:45
 */
@SuppressWarnings("all")
@RestController(value = "webUnionController")
@Authentication(authenticationType = AuthenticationType.None)
public class UnionController extends BaseController {


    @Autowired
    private OrderHelper orderHelper;

    @Autowired
    private UnionService unionService;

    @Autowired
    private PayAccountAdapter payAccountAdapter;


    @RequestMapping("/web/orderPage")
    public ModelAndView orderPage(HttpServletRequest request){
        ModelAndView modelAndView = new ModelAndView("union/orderPage");
        modelAndView.addObject("orderNo",orderHelper.createOrder(request));
        modelAndView.addObject("orderAmount",1);
        modelAndView.addObject("productName",sysConfig.getDefaultProductName())   ;
        return    modelAndView  ;
    }


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
    @RequestMapping(value = "dopay",method = RequestMethod.GET)
    @Authentication(authenticationType = AuthenticationType.None)
    public Object pay(
            @RequestParam String code,
            @Valid WxPayRequest wxPayRequest,
            BindingResult bindingResult,
            HttpServletRequest httpServletRequest) throws Exception {

        PayConfigTarget<Account4UnionSDK> payConfigTarget = payAccountAdapter.getPayconfig(EnumPayType.UnionSdk,"1");
        PayConfigTarget<Account4UnionGateWay> account4UnionGateWayPayConfigTarget = payAccountAdapter.getPayconfig(EnumPayType.UnionGateWay,"1");

        Map<String, Object> wxSessionMap = unionService.getWxSession(code);
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




    /**
     * 通联用户注册
     */
    @ResponseBody
    @RequestMapping(value = "/web/unionregister", method = RequestMethod.POST)
    @ApiOperation(value = "通联用户注册")
    public Object unionRegisterAction(@RequestParam(required = false) String puid, HttpServletRequest request,
                                      HttpServletResponse
                                              response) {
        Pattern pattern = Pattern.compile("^[0-9a-z]{1,32}");
        if(pattern.matcher(puid).matches()) {
            try {
                return  unionService.unionRegister(puid)  ;
            } catch (Exception e){
                return new BillApiResponse(e);
            }
        }else {
            return UnionError.puidError ;
        }


    }



    /**
     * 订单查询
     *
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "/web/orderquery", method = RequestMethod.POST)
    public Object orderQueryAction(HttpServletRequest request, HttpServletResponse response, Model model,
                                   @RequestParam String orderDate,
                                   @RequestParam String orderNo
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
    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public Object batchOrderQueryAction(HttpServletRequest request, HttpServletResponse response, Model model, @RequestParam(defaultValue = "1",required =
            true) int pageNo)
            throws
            Exception {
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


    /**

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
//        * todo  未通过 可能是要BASE64解码
        System.out.println(x);
        return "";

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


    @RequestMapping(value = "/web/ordersubmit", method = {RequestMethod.POST})
    public Object orderSubmit( @Valid OrderSubmitRequest orderSubmitRequest, HttpServletRequest request, HttpServletResponse response,
                              Model model) throws
            Exception {


//        orderSubmitRequest.setUnionUserId("170410792118546");
//        orderSubmitRequest.setOrderAmount(1);
//        orderSubmitRequest.setProductNum(1);

        if (StringUtils.isEmpty(orderSubmitRequest.getProductName())) {
            orderSubmitRequest.setProductName("国通石油");
        }
        if (StringUtils.isEmpty(orderSubmitRequest.getOrderNo())) {
            String orderNo = orderHelper.createOrder(request);
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
}
