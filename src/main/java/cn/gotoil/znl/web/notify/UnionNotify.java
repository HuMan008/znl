package cn.gotoil.znl.web.notify;

import cn.gotoil.bill.web.annotation.Authentication;
import cn.gotoil.bill.web.interceptor.authentication.AuthenticationType;
import cn.gotoil.znl.common.union.SybUtil;
import cn.gotoil.znl.config.property.SybConstants;
import cn.gotoil.znl.service.UnionService;
import cn.gotoil.znl.web.message.response.union.PayResultResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.TreeMap;

/**
 * Created by Suyj <suyajiang@gotoil.cn> on 2017/5/2.9:46
 * 通联通知类
 */
@Authentication(authenticationType = AuthenticationType.None)
@Controller
@Component
public class UnionNotify {

    private Logger logger = LoggerFactory.getLogger(UnionNotify.class);

    @Autowired
    private UnionService unionService;

    /**
     * 通联微信支付的回调
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping("/pay/wxpaynotify")
    public void wxpaynotify(HttpServletRequest request, HttpServletResponse response) throws Exception {
        logger.info("------------------------------------------------接收到通知-------------");
        request.setCharacterEncoding("gbk");//通知传输的编码为GBK
        response.setCharacterEncoding("gbk");
        TreeMap<String, String> params = SybUtil.getParams(request);//动态遍历获取所有收到的参数,此步非常关键,因为收银宝以后可能会加字段,动态获取可以兼容
        logger.info("参数+" + params.toString());
        try {
            boolean isSign = SybUtil.validSign4AllInPayWechatRequest(params, SybConstants.SYB_APPKEY);// 接受到推送通知,首先验签
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
     * 通联支付同步支付结果
     *
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "/pay/dopickup")
    @Authentication(authenticationType = AuthenticationType.None)
    public String doPickup(PayResultResponse payResultResponse, HttpServletRequest request, HttpServletResponse response, Model model) {
        logger.info("------------------通联支付同步通知------------------------");
        //通联支付同步通知
        System.out.println(payResultResponse.toString());
        //PayResultResponse{merchantId='008500189990304', version='v1.0', language='1', signType='0', issuerId='', paymentOrderId='201704111646298209', orderNo='order_001', orderDatetime='20170411164619', orderAmount=1, payDatetime='20170411164636', payAmount=1, ext1='<USER>170410792118546</USER>', ext2='', payResult='1', returnDatetime='20170411164642'}
        return null;
    }


    /**
     * 通联支付异步通知
     * @param payResultResponse
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/pay/receiveurl")
    @Authentication(authenticationType = AuthenticationType.None)
    public String receiveUrl(PayResultResponse payResultResponse,HttpServletRequest request, HttpServletResponse response) throws Exception {
        logger.info("------------------通联支付异步通知------------------------");
        request.setCharacterEncoding("gbk");//通知传输的编码为GBK
        response.setCharacterEncoding("gbk");
        TreeMap<String, String> params = SybUtil.getParams(request);//动态遍历获取所有收到的参数,此步非常关键,因为收银宝以后可能会加字段,动态获取可以兼容
        logger.info("参数+" + params.toString());
        try {

//            boolean isSign = SybUtil.validSign4AllInPayWechatRequest(params, SybConstants.SYB_APPKEY);// 接受到推送通知,首先验签
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


    /**
     * SDK的异步通知
     * @param payResultResponse
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping("/pay/sdk/receiveurl")
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
