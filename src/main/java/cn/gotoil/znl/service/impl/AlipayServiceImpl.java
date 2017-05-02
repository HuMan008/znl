package cn.gotoil.znl.service.impl;

import cn.gotoil.znl.config.define.AlipayConfig;
import cn.gotoil.znl.service.AlipayService;
import cn.gotoil.znl.web.message.request.alipay.AlipayPayRequest;
import cn.gotoil.znl.web.message.request.alipay.AlipayQueryRequest;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.domain.AlipayTradeQueryModel;
import com.alipay.api.domain.AlipayTradeWapPayModel;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.request.AlipayTradeQueryRequest;
import com.alipay.api.request.AlipayTradeWapPayRequest;
import com.alipay.api.response.AlipayTradeAppPayResponse;
import com.alipay.api.response.AlipayTradeQueryResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by wh on 2017/4/18.
 */
@Service
public class AlipayServiceImpl implements AlipayService {

    @Autowired
    private AlipayConfig alipayConfig;

    /***
     * wap支付
     */
    public String wap_pay(AlipayPayRequest alipayPayRequest) throws AlipayApiException {
        /**********************/
        // SDK 公共请求类，包含公共请求参数，以及封装了签名与验签，开发者无需关注签名与验签
        //调用RSA签名方式
        AlipayClient client = new DefaultAlipayClient(AlipayConfig.URL, alipayConfig.APPID, alipayConfig.RSA_PRIVATE_KEY, AlipayConfig.FORMAT, AlipayConfig.CHARSET, alipayConfig.ALIPAY_PUBLIC_KEY,AlipayConfig.SIGNTYPE);
        AlipayTradeWapPayRequest alipay_request=new AlipayTradeWapPayRequest();

        // 封装请求支付信息
        AlipayTradeWapPayModel model=new AlipayTradeWapPayModel();
        model.setOutTradeNo(alipayPayRequest.getOut_trade_no());
        model.setSubject(alipayPayRequest.getSubject());
        model.setTotalAmount( String.valueOf(alipayPayRequest.getTotal_amount()) );
        model.setBody(alipayPayRequest.getDesc());
        model.setTimeoutExpress(alipayPayRequest.getTimeout_express());
        model.setProductCode(alipayPayRequest.getProduct_code());
        alipay_request.setBizModel(model);
        // 设置异步通知地址
        alipay_request.setNotifyUrl(alipayConfig.notify_url);
        // 设置同步地址
        alipay_request.setReturnUrl(alipayConfig.return_url);


        // form表单生产
        String form = client.pageExecute(alipay_request).getBody();
        return  form;
    }

    /**
     *  支付结果查询
     * **/
    public String query( AlipayQueryRequest alipayQueryRequest ) throws AlipayApiException {

        //
        /**********************/
        // SDK 公共请求类，包含公共请求参数，以及封装了签名与验签，开发者无需关注签名与验签
        AlipayClient client = new DefaultAlipayClient(AlipayConfig.URL, alipayConfig.APPID, alipayConfig.RSA_PRIVATE_KEY, AlipayConfig.FORMAT, AlipayConfig.CHARSET, alipayConfig.ALIPAY_PUBLIC_KEY,AlipayConfig.SIGNTYPE);
        AlipayTradeQueryRequest alipay_request = new AlipayTradeQueryRequest();

        AlipayTradeQueryModel model=new AlipayTradeQueryModel();
        model.setOutTradeNo(alipayQueryRequest.getOut_trade_no());
        model.setTradeNo(alipayQueryRequest.getTrade_no());
        alipay_request.setBizModel(model);

        AlipayTradeQueryResponse alipay_response =client.execute(alipay_request);

        return alipay_response.getBody();
    }


    /**
     *  app支付
     * **/
    public  String  app_pay(AlipayPayRequest alipayPayRequest) throws AlipayApiException {

        //实例化客户端
        AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfig.URL , alipayConfig.APPID, alipayConfig.RSA_PRIVATE_KEY, AlipayConfig.FORMAT,
                AlipayConfig.CHARSET, alipayConfig.ALIPAY_PUBLIC_KEY, AlipayConfig.SIGNTYPE);

        //实例化具体API对应的request类,类名称和接口名称对应,当前调用接口名称：alipay.trade.app.pay
        AlipayTradeAppPayRequest request = new AlipayTradeAppPayRequest();

        //SDK已经封装掉了公共参数，这里只需要传入业务参数。以下方法为sdk的model入参方式(model和biz_content同时存在的情况下取biz_content)。
        AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();
        model.setBody(alipayPayRequest.getDesc());
        model.setSubject(alipayPayRequest.getSubject());
        model.setOutTradeNo(alipayPayRequest.getOut_trade_no());
        model.setTimeoutExpress( alipayPayRequest.getTimeout_express() );
        model.setTotalAmount(alipayPayRequest.getTotal_amount()+"");
        model.setProductCode( alipayPayRequest.getProduct_code() );
        request.setBizModel( model );
        request.setNotifyUrl( alipayConfig.notify_url );
        request.setReturnUrl( alipayConfig.return_url );

        //这里和普通的接口调用不同，使用的是sdkExecute

        AlipayTradeAppPayResponse response = alipayClient.sdkExecute(request);

        return  response.getBody() ;

    }
}
