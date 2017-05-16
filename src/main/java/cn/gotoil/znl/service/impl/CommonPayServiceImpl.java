package cn.gotoil.znl.service.impl;

import cn.gotoil.bill.tools.string.StringUtils;
import cn.gotoil.znl.common.tools.SerialNumberUtil;
import cn.gotoil.znl.config.define.AlipayConfig;
import cn.gotoil.znl.config.define.PayBaseConfig;
import cn.gotoil.znl.exception.ZnlError;
import cn.gotoil.znl.model.domain.*;
import cn.gotoil.znl.model.repository.*;
import cn.gotoil.znl.service.AlipayService;
import cn.gotoil.znl.service.CommonPayService;
import cn.gotoil.znl.web.message.request.PayRequest;
import cn.gotoil.znl.web.message.request.alipay.AlipayPayRequest;
import cn.gotoil.znl.web.message.response.alipay.WapPayResponse;
import com.alipay.api.AlipayApiException;
import org.apache.commons.lang3.RandomStringUtils;
import org.hibernate.exception.DataException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Date;

/**
 * Created by wh on 2017/5/11.
 */
@Service
public class CommonPayServiceImpl implements CommonPayService {

    @Autowired
    private AlipayService alipayService;

    @Autowired
    private  JPAAccountForZhifubaoSDKRepository  jpaAccountForZhifubaoSDKRepository;

    @Autowired
    private JPAAppPayAccountRepository jpaAppPayAccountRepository;

    @Autowired
    private JPAOrderRepository jpaOrderRepository;

    @Autowired
    private SerialNumberUtil serialNumberUtil;

    @Autowired
    private JPAAppRepository jpaAppRepository;

    @Autowired
    private RestTemplate restTemplate;


    public  String  sdkPay(PayRequest request) throws AlipayApiException {

        //返回字符串
        String returnStr = "";
        //0,参数校验
        Order.PayTypeEnum payType = Order.PayTypeEnum.getEnumByCode( request.getPayType() );

        App app = jpaAppRepository.findOne( request.getAppID() );
        if(null == request.getSubject() || org.apache.commons.lang3.StringUtils.isEmpty(request.getSubject()) ){
            request.setSubject( app.getDefaultProductname() );
        }
        if( 0 == request.getTimeout_minute()   ){
            request.setTimeout_minute( app.getDefaultOrderExpriseTime() );
        }
        if(null == request.getNotify_url_actual() || org.apache.commons.lang3.StringUtils.isEmpty(request.getNotify_url_actual()) ){
            request.setNotify_url_actual(  app.getDefaultNotifyUrl() );
        }
        if(null == request.getReturn_url_actual() || org.apache.commons.lang3.StringUtils.isEmpty(request.getReturn_url_actual()) ){
            request.setReturn_url_actual(  app.getDefaultReturnUrl() );
        }
        if(null == request.getOrder_id_actual() || org.apache.commons.lang3.StringUtils.isEmpty(request.getOrder_id_actual()) ){
            request.setOrder_id_actual( RandomStringUtils.random(32)  );
        }
        //1
        AppPayAccount appPayAccount = jpaAppPayAccountRepository.findByAppIDAndPayType( request.getAppID(),request.getPayType() );


        //2,虚拟网关 订单数据 入库形成订单
        long expireTimeLong  = System.currentTimeMillis()+60*1000*request.getTimeout_minute();
        Order order = new Order();
        order.setStatus( Order.StateEnum.Paying.getCode() );
        order.setOrderExpireTime( new Date( expireTimeLong) );
        order.setExpire_time_minute( request.getTimeout_minute() );
        order.setAppid( request.getAppID() );
        order.setAppOrderID( request.getOrder_id_actual()  );
        order.setAppuserID(  "" );
        order.setDescp( request.getSubject()  );
        order.setExtraParam( request.getExtra_param() );
        order.setID( System.currentTimeMillis()+serialNumberUtil.generateSerialNumber(SerialNumberUtil.NumberTypeEnum.Order, 8));
        order.setNotifyUrl( request.getNotify_url_actual()  );
        order.setOrderFee(  request.getAmount()  );
        order.setPayAccountID( appPayAccount.getPayAccountID() );
        order.setPayType( request.getPayType() );
        order.setRemark( ""  );
        order.setReturnUrl(  request.getReturn_url_actual() );
        order.setStatusUpdateTime( new Date(System.currentTimeMillis()) );
        order.setThirdOrderNO( "" );
        order.setVersion(1L );
        order.setCreatedAt( new Date() );
        order.setUpdateAt(  new Date() );
        order.setOrderActualFee( 0 );

        jpaOrderRepository.save( order );
        //3,发送支付请求
        if(payType.getCode().equals(Order.PayTypeEnum.Zhifubao_SDK.getCode())) {

            //
            AlipayPayRequest alipayPayRequest = new AlipayPayRequest();
            alipayPayRequest.setSubject( order.getDescp() );
            alipayPayRequest.setTotal_amount( order.getOrderFee()/(1.0*100) );
            alipayPayRequest.setTimeout_express(  request.getTimeout_minute() +"m");
            alipayPayRequest.setDesc( "" );
            alipayPayRequest.setProduct_code( AlipayConfig.Product_Code );
            alipayPayRequest.setExtendParams( request.getExtra_param() );
            alipayPayRequest.setOut_trade_no(  order.getAppOrderID() );

            AccountForZhifubaoSDK configSdk =
                    jpaAccountForZhifubaoSDKRepository.findOne(  appPayAccount.getPayAccountID()   );

            AlipayConfig alipayConfig = new AlipayConfig();
            alipayConfig.setRSA_PRIVATE_KEY( configSdk.getPrivateKey() );
            alipayConfig.setALIPAY_PUBLIC_KEY( configSdk.getPublicKey()  );
            alipayConfig.setAPPID( configSdk.getAppID()  );
            return  alipayService.app_pay(alipayPayRequest,alipayConfig);
        }else {
            //TODO:
        }
        //4,支付日志 入库

        return  "";
    }

    public  String  wapPay(PayRequest request){

        //转发 路径
        String forwardUrl = "";
        //0,参数校验
        Order.PayTypeEnum payType = Order.PayTypeEnum.getEnumByCode( request.getPayType() );

        App app = jpaAppRepository.findOne( request.getAppID() );
        if(null == request.getSubject() || org.apache.commons.lang3.StringUtils.isEmpty(request.getSubject()) ){
            request.setSubject( app.getDefaultProductname() );
        }
        if( 0 == request.getTimeout_minute()   ){
            request.setTimeout_minute( app.getDefaultOrderExpriseTime() );
        }
        if(null == request.getNotify_url_actual() || org.apache.commons.lang3.StringUtils.isEmpty(request.getNotify_url_actual()) ){
            request.setNotify_url_actual(  app.getDefaultNotifyUrl() );
        }
        if(null == request.getReturn_url_actual() || org.apache.commons.lang3.StringUtils.isEmpty(request.getReturn_url_actual()) ){
            request.setReturn_url_actual(  app.getDefaultReturnUrl() );
        }
        if(null == request.getOrder_id_actual() || org.apache.commons.lang3.StringUtils.isEmpty(request.getOrder_id_actual()) ){
            request.setOrder_id_actual( RandomStringUtils.random(32)  );
        }
        //1
        AppPayAccount appPayAccount = jpaAppPayAccountRepository.findByAppIDAndPayType( request.getAppID(),request.getPayType() );


        //2,虚拟网关 订单数据 入库形成订单
        long expireTimeLong  = System.currentTimeMillis()+60*1000*request.getTimeout_minute();
        Order order = new Order();
        order.setStatus( Order.StateEnum.Paying.getCode() );
        order.setOrderExpireTime( new Date( expireTimeLong) );
        order.setExpire_time_minute( request.getTimeout_minute() );
        order.setAppid( request.getAppID() );
        order.setAppOrderID( request.getOrder_id_actual()  );
        order.setAppuserID(  "" );
        order.setDescp( request.getSubject()  );
        order.setExtraParam( request.getExtra_param() );
        order.setID( System.currentTimeMillis()+serialNumberUtil.generateSerialNumber(SerialNumberUtil.NumberTypeEnum.Order, 8));
        order.setNotifyUrl( request.getNotify_url_actual()  );
        order.setOrderFee(  request.getAmount()  );
        order.setPayAccountID( appPayAccount.getPayAccountID() );
        order.setPayType( request.getPayType() );
        order.setRemark( ""  );
        order.setReturnUrl(  request.getReturn_url_actual() );
        order.setStatusUpdateTime( new Date(System.currentTimeMillis()) );
        order.setThirdOrderNO( "" );
        order.setVersion(1L );
        order.setCreatedAt( new Date() );
        order.setUpdateAt(  new Date() );
        order.setOrderActualFee( 0 );

        jpaOrderRepository.save( order );
        //3,发送支付请求
        if(payType.getCode().equals(Order.PayTypeEnum.Zhifubao_WAP.getCode())) {
            forwardUrl =  "forward:/alipay/v1/wap/pay?";
            //发送http get请求
            WapPayResponse wapPayResponse = new WapPayResponse();
            wapPayResponse.setTotal_amount( order.getOrderFee()/(1.0*100) );
            wapPayResponse.setProduct_code( AlipayConfig.Product_Code ) ;
            wapPayResponse.setOut_trade_no( order.getAppOrderID()  );
            wapPayResponse.setDesc( "");
            wapPayResponse.setOrderVirtualID( order.getID() );
            wapPayResponse.setSubject( order.getDescp() );
//            restTemplate.getForObject( httpUrl  , String.class  );

            StringBuilder bd = new StringBuilder("");
            bd.append("product_code=").append(wapPayResponse.getProduct_code());
            bd.append("&out_trade_no=").append(wapPayResponse.getOut_trade_no());
            bd.append("&subject=").append(wapPayResponse.getSubject());
            bd.append("&total_amount=").append(wapPayResponse.getTotal_amount());
            bd.append("&desc=").append(wapPayResponse.getDesc());
            bd.append("&orderVirtualID=").append(wapPayResponse.getOrderVirtualID());
            return  forwardUrl+bd.toString();
        }else {
            //TODO:
        }
        //4,支付日志 入库

        return  "";
    }

}
