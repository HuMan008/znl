package com.znl.web.controller.v1;

import com.alibaba.fastjson.JSONObject;
import com.znl.classes.OrderHelper;
import com.znl.common.tools.date.DateUtils;
import com.znl.config.property.SybConstants;
import com.znl.common.union.SybUtil;
import com.znl.config.property.SysConfig;
import com.znl.exception.MicroServerException;
import com.znl.exception.ValidatorEnum;
import com.znl.exception.WebException;
import com.znl.exception.WebExceptionEnum;
import com.znl.service.UnionService;
import com.znl.web.controller.BaseController;
import com.znl.web.message.request.union.*;
import com.znl.web.message.response.union.PayResultResponse;
import com.znl.web.message.response.union.UnionRegisterRespon4API;
import com.znl.web.message.response.union.UnionRegisterResponse;
import com.znl.web.message.response.union.WxPayInfoResponse;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
@Controller
public class UnionController extends BaseController {

    private Logger logger  = LoggerFactory.getLogger(UnionController.class);


    @Autowired
    private UnionService unionService;

    @Autowired
    private SysConfig sysConfig;

    /**
     * 微信支付
     * @param code
     * @param wxPayRequest
     * @param bindingResult
     * @param httpServletRequest
     * @return
     * @throws Exception
     */
    @RequestMapping("dopay")
    public Object pay(@RequestParam @NotNull String code,
                      @ModelAttribute @Valid WxPayRequest  wxPayRequest,
                      BindingResult bindingResult,
                      HttpServletRequest httpServletRequest) throws Exception{
        if(bindingResult.hasErrors()){
            return new WebException(ValidatorEnum.RequestArgVailiError);

        }
        Map<String,Object> wxSessionMap =  unionService.getWxSession(code);
        if(wxSessionMap==null || wxSessionMap.isEmpty()){
            return  new WebException(WebExceptionEnum.NoSupportWechatCode);
        }
        //获取异常
        if(wxSessionMap.containsKey("errcode")){
            return new MicroServerException(-1, wxSessionMap.get("errcode").toString());
        }
        String wxOpenId = (String)wxSessionMap.get("openid");
//        String wxOpenId = "oQxUFuCHo8J0HeYa-20oK-MGEYGc"   ;
        String reqsn = String.valueOf(System.currentTimeMillis());

//        Map<String, String> map =unionService.unionWxPay(trxamt,reqsn,"","--body--","--备注--",wxOpenId,"authcode","");
        Map<String,String> map = unionService.unionWxPay(wxPayRequest,wxOpenId);
        if("SUCCESS".equals(map.get("retcode"))){
            String payInfo = map.get("payinfo");
            System.out.println(payInfo);
            WxPayInfoResponse wxPayInfoResponse = JSONObject.parseObject(payInfo,WxPayInfoResponse.class);

            ModelAndView modelAndView = new ModelAndView("union/wxpay");
            modelAndView.addObject("appId",wxPayInfoResponse.getAppId());
            modelAndView.addObject("timeStamp",wxPayInfoResponse.getTimeStamp()) ;
            modelAndView.addObject("nonceStr",wxPayInfoResponse.getNonceStr()) ;
            modelAndView.addObject("prepay_id",wxPayInfoResponse.getaPackage()) ;
            modelAndView.addObject("paySign",wxPayInfoResponse.getPaySign()) ;

            return modelAndView ;

        }else {
//        }else if("FAIL".equals(map.get("retcode"))){
            return new ModelAndView("union/orderfail");
        }

    }


    @RequestMapping("wxpaynotify")
    public void wxpaynotify(HttpServletRequest request, HttpServletResponse response) throws Exception{
        logger.info("------------------------------------------------接收到通知-------------");
        System.out.println("接收到通知");
        request.setCharacterEncoding("gbk");//通知传输的编码为GBK
        response.setCharacterEncoding("gbk");
        TreeMap<String,String> params =SybUtil.getParams(request);//动态遍历获取所有收到的参数,此步非常关键,因为收银宝以后可能会加字段,动态获取可以兼容
        logger.info("参数+" +  params.toString());
        try {
            boolean isSign = SybUtil.validSign(params, SybConstants.SYB_APPKEY);// 接受到推送通知,首先验签
            System.out.println("验签结果:"+isSign);
            logger.info("验签结果"+isSign);
            //验签完毕进行业务处理
        } catch (Exception e) {//处理异常
            // TODO: handle exception
            e.printStackTrace();
        }
        finally{//收到通知,返回success
            response.getOutputStream().write("success".getBytes());
            response.flushBuffer();
        }
    }

    /**
     * 通联用户注册
     */
    @ResponseBody
    @RequestMapping("unionregister")
    public Object unionRegisterAction (@RequestParam(required = false) @Size(max =32,message = "请传入正确的参数") String puid, HttpServletRequest request,
                                 HttpServletResponse
            response){
        UnionRegisterRequest registerRequest = new UnionRegisterRequest();
        UnionRegisterRespon4API unionRegisterRespon4API = new UnionRegisterRespon4API();
        unionRegisterRespon4API.setPartnerUserId(puid);

        if(StringUtils.isEmpty(puid)){
            puid= com.znl.common.tools.string.StringUtils.createUid(request);
            unionRegisterRespon4API.setPartnerUserId(puid);
        }
        registerRequest.setPartnerUserId(puid);
        registerRequest.doSign();

        try {
            UnionRegisterResponse unionRegisterResponse = unionService.unionRegister(registerRequest);

            BeanUtils.copyProperties(unionRegisterResponse,unionRegisterRespon4API);

            return unionRegisterRespon4API;
        }catch (ConnectException e){
            e.printStackTrace();
            return new WebException(WebExceptionEnum.NetConnectTimeOut)  ;
        }catch (Exception e1)  {
            return new MicroServerException(4000,e1.getMessage());
        }
    }


    @RequestMapping(value = "ordersubmit",method = RequestMethod.POST)
    public Object orderSubmit( @ModelAttribute @Valid OrderSubmitRequest orderSubmitRequest, HttpServletRequest request, HttpServletResponse response,
                                     Model model) throws
            Exception{


//        orderSubmitRequest.setUnionUserId("170410792118546");
//        orderSubmitRequest.setOrderAmount(1);
//        orderSubmitRequest.setProductNum(1);
        orderSubmitRequest.setPickupUrl( SybConstants.GateWayConsts.URL_DOPICKUP);
        orderSubmitRequest.setReceiveUrl(SybConstants.GateWayConsts.URL_RECEIVEURL);    //
        if(StringUtils.isEmpty(orderSubmitRequest.getProductName())){
            orderSubmitRequest.setProductName("国通石油");
        }
        if(StringUtils.isEmpty(orderSubmitRequest.getOrderNo())){
            String orderNo  =  OrderHelper.createOrder(request);
            if(StringUtils.isEmpty(orderNo)){
                return new MicroServerException(9001,"APPKEY长度限制在0-8位;并且必须已字母或者数字开头，并且不能包含特殊字符")  ;
            }
            orderSubmitRequest.setOrderNo(orderNo);
        }
        if(orderSubmitRequest.getOrderAmount()==0){
            return new MicroServerException(9003,"订单金额不能小于1") ;
        }
        if(orderSubmitRequest.getProductNum()*orderSubmitRequest.getProductPrice()!=0 )  {
            if(orderSubmitRequest.getProductNum()*orderSubmitRequest.getProductPrice()!=orderSubmitRequest.getOrderAmount())  {
                return new MicroServerException(9002,"订单数量、单价与订单金额逻辑错误")  ;
            }
        }
        if(orderSubmitRequest.getOrderExpireDatetime()==0){
            orderSubmitRequest.setOrderExpireDatetime(10);
        }
        if(StringUtils.isEmpty(orderSubmitRequest.getPayType())){
            orderSubmitRequest.setPayType("0");
        }
        orderSubmitRequest.doSign();

        model.addAttribute("unionOrder",orderSubmitRequest);
        model.addAttribute("url" ,SybConstants.GateWayConsts.URL_ORDERSUBMIT);//表单提交的url;
        return new ModelAndView("union/ordersubmit") ;

    }

    /**
     * 订单查询
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "orderquery")
    public String orderQuery (HttpServletRequest request, HttpServletResponse response,Model model) throws Exception{
        OrderQueryRequest orderQueryRequest = new OrderQueryRequest();
//        PayResultResponse{merchantId='008500189990304', version='v1.0', language='1', signType='0', issuerId='', paymentOrderId='201704111646298209', orderNo='order_001', orderDatetime='20170411164619', orderAmount=1, payDatetime='20170411164636', payAmount=1, ext1='<USER>170410792118546</USER>', ext2='', payResult='1', returnDatetime='20170411164642'}
        orderQueryRequest.setOrderDatetime("20170411164619");
        orderQueryRequest.setOrderNo("order_001");

        orderQueryRequest.doSign();
     /*   OrderQueryResponse  orderQueryResponse = (OrderQueryResponse)ObjectHelper.mapToObject(unionService.orderQuery(orderQueryRequest),OrderQueryResponse.class); ;
        System.out.println(orderQueryResponse.toString());*/
        System.out.println(unionService.orderQuery(orderQueryRequest));
        //payDatetime=20170411164636&userName=&credentialsType=&pan=&txOrgId=&ext1=%3CUSER%3E170410792118546%3C%2FUSER%3E&payAmount=1&returnDatetime=20170411164743&credentialsNo=&issuerId=&signMsg=6EB48C82DE9840A2B181F3E1CC6C1E72&payType=0&language=1&errorCode=&merchantId=008500189990304&orderDatetime=20170411164619&version=v1.0&orderNo=order_001&ext2=&signType=0&orderAmount=1&extTL=&paymentOrderId=201704111646298209&payResult=1&
        return "";
    }

    /**
     * 订单查询（批量）
     * @param request
     * @param response
     * @param model
     * @return
     * todo  未通过
     * @throws Exception
     */
    @RequestMapping(value = "batchorderquery")
    public String batchOrderQuery(HttpServletRequest request, HttpServletResponse response,Model model) throws Exception{
        BatchOrderQueryRequest batchOrderQueryRequest = new BatchOrderQueryRequest();
        batchOrderQueryRequest.setBeginDateTime(DateUtils.simpleDateFormatter().format(new Date()).replace("-","")+"00");
        batchOrderQueryRequest.setEndDateTime(DateUtils.simpleDateFormatter().format(new Date()).replace("-","")+"23");
        batchOrderQueryRequest.doSign();
        String result = unionService.batchOrderQuery(batchOrderQueryRequest);
        System.out.println(result);
        return "";
    }


    @RequestMapping(value = "refund")
    public String refund(HttpServletRequest request,HttpServletResponse response,Model mode) throws Exception{
        RefundRequest refundRequest = new RefundRequest() ;
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
     * @param request
     * @param response
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "refundstatus")
    public String refundStatus(HttpServletRequest request,HttpServletResponse response ,Model model) throws Exception{
        RefundStatusRequest refundStatusRequest= new RefundStatusRequest();
        refundStatusRequest.setMchtRefundOrderNo("refund_order_001");
        refundStatusRequest.setOrderNo("order_001");
        refundStatusRequest.setRefundDatetime("20170411164619");
        refundStatusRequest.setRefundAmount(1);
        refundStatusRequest.doSign();
       String x = unionService.refundStatus(refundStatusRequest)   ;
        System.out.println(x);
        return "";

    }




    /**
     * 同步支付结果
     * @param request
     * @param response
     * @param model
     * @return
     */
     @RequestMapping(value = "dopickup")
    public String doPickup(PayResultResponse payResultResponse, HttpServletRequest request, HttpServletResponse response,Model model) {
         System.out.println(payResultResponse.toString());
         //PayResultResponse{merchantId='008500189990304', version='v1.0', language='1', signType='0', issuerId='', paymentOrderId='201704111646298209', orderNo='order_001', orderDatetime='20170411164619', orderAmount=1, payDatetime='20170411164636', payAmount=1, ext1='<USER>170410792118546</USER>', ext2='', payResult='1', returnDatetime='20170411164642'}
         return null;
     }
    @RequestMapping(value = "receiveurl")
    public String  receiveUrl(HttpServletRequest request,HttpServletResponse response) {
        System.out.println(request.getParameterMap().toString());
        return null;
    }



}
