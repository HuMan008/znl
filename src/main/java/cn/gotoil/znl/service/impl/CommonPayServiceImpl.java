package cn.gotoil.znl.service.impl;

import cn.gotoil.bill.tools.ObjectHelper;
import cn.gotoil.znl.adapter.PayAccountAdapter;
import cn.gotoil.znl.adapter.PayConfigTarget;
import cn.gotoil.znl.common.tools.SerialNumberUtil;
import cn.gotoil.znl.config.define.AlipayConfig;
import cn.gotoil.znl.config.property.SybConstants;
import cn.gotoil.znl.config.property.SysConfig;
import cn.gotoil.znl.config.property.UnionConsts;
import cn.gotoil.znl.model.domain.*;
import cn.gotoil.znl.model.enums.EnumPayType;
import cn.gotoil.znl.model.enums.TimeUnitEnum;
import cn.gotoil.znl.model.repository.*;
import cn.gotoil.znl.service.AlipayService;
import cn.gotoil.znl.service.CommonPayService;
import cn.gotoil.znl.service.UnionService;
import cn.gotoil.znl.web.message.request.PayRequest;
import cn.gotoil.znl.web.message.request.alipay.AlipayPayRequest;
import cn.gotoil.znl.web.message.request.union.AppPayRequest;
import cn.gotoil.znl.web.message.request.union.OrderSubmitRequest;
import cn.gotoil.znl.web.message.request.union.WxPayRequest;
import cn.gotoil.znl.web.message.response.alipay.WapPayResponse;
import com.alipay.api.AlipayApiException;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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

    @Autowired
    private UnionService unionService;

    @Autowired
    private SysConfig sysConfig;

    public  String  sdkPay(PayRequest request) throws AlipayApiException, UnsupportedEncodingException {

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
        if(payType!=null && payType.getCode().equals(Order.PayTypeEnum.Zhifubao_SDK.getCode())) {

            //
            AlipayPayRequest alipayPayRequest = new AlipayPayRequest();
            alipayPayRequest.setSubject( order.getDescp() );
            alipayPayRequest.setTotal_amount( order.getOrderFee()/(1.0*100) );
            alipayPayRequest.setTimeout_express(  request.getTimeout_minute() + TimeUnitEnum.Minute.getCode() );
            alipayPayRequest.setDesc( "" );
            alipayPayRequest.setProduct_code( AlipayConfig.Product_Code );
            alipayPayRequest.setExtendParams( request.getExtra_param() );
            alipayPayRequest.setOut_trade_no(  order.getAppOrderID() );

            AccountAlipaySDK configSdk =
                    jpaAccountForZhifubaoSDKRepository.findOne(  appPayAccount.getPayAccountID()   );

            AlipayConfig alipayConfig = new AlipayConfig();
            alipayConfig.setRSA_PRIVATE_KEY( configSdk.getPrivateKey() );
            alipayConfig.setALIPAY_PUBLIC_KEY( configSdk.getPublicKey()  );
            alipayConfig.setAPPID( configSdk.getAppID()  );
            return  alipayService.app_pay(alipayPayRequest,alipayConfig);
        }else if(EnumPayType.UnionSdk.getCode().equals(request.getPayType())) {
            AppPayRequest appPayRequest = unionService.payRequest2UnionSdkRequest(request);
           return ObjectHelper.jsonString(appPayRequest);
        }
        //4,支付日志 入库

        return  "";
    }

    public ModelAndView wapPay(PayRequest request, Model model, RedirectAttributes attributes, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse){

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
        order.setAppuserID( request.getAppUserID()  );
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
        if(payType!=null && payType.getCode().equals(Order.PayTypeEnum.Zhifubao_WAP.getCode())) {
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
            attributes.addFlashAttribute("wapPayResponse",wapPayResponse);
            return  new ModelAndView("redirect:/alipay/v1/wap/pay");
        }else if(EnumPayType.UnionGateWay.getCode().equals(request.getPayType())){
            try{
                OrderSubmitRequest orderSubmitRequest = (OrderSubmitRequest)unionService.payRequest2UnionRequest(request);
                model.addAttribute("unionOrder", orderSubmitRequest);
//                redirectAttributes.a("unionOrder",orderSubmitRequest);
                model.addAttribute("url", SybConstants.GateWayConsts.URL_ORDERSUBMIT);//表单提交的url;
//                redirectAttributes.addAttribute("url",SybConstants.GateWayConsts.URL_ORDERSUBMIT);
//                return  new Mo"redirect:/pay/web/ordersubmit";
                return new ModelAndView("union/ordersubmit");
            }
            catch (Exception e){
                e.printStackTrace();
            }


        } else if(EnumPayType.UnionWechatJs.getCode().equals(request.getPayType())){ //通联微信支付

            WxPayRequest wxPayRequest = unionService.payRequest2UnionWechatRequest(request);
            Map<String, Object> map = new HashMap<>();
            String mydirect =sysConfig.getFullServerHostAddress()+"/pay/dopay?t="+System.currentTimeMillis()+"&";
            try{map = ObjectHelper.introspect(wxPayRequest);
                String x = cn.gotoil.znl.common.tools.ObjectHelper.getUrlParamsByMap(map);
                mydirect = mydirect+x;
                mydirect=URLEncoder.encode(mydirect,"UTF-8");
            }catch (Exception e){
                e.printStackTrace();
            }
            String url = "https://open.weixin.qq.com/connect/oauth2/authorize" +
                    "?appid="+wxPayRequest.getWxAppid() +
                    "&redirect_uri=" + mydirect+
                    "&response_type=code&scope=snsapi_base&state="+RandomStringUtils.randomAscii(4)+/*ObjectHelper.jsonString(wxPayRequest)*/""+"&connect_redirect=1#wechat_redirect";
            return new ModelAndView("redirect:"+url);


        }else{
            //TODO:

        }
        //4,支付日志 入库

        return  null;
    }

}
