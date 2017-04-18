package com.znl.web.controller.v1;

import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;
import com.znl.config.AlipayConfig;
import com.znl.service.AlipayService;
import com.znl.web.message.request.alipay.AlipayQueryRequest;
import com.znl.web.message.request.alipay.AlipayPayRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by wh on 2017/4/18.
 */
@Controller
@RequestMapping("/alipay/v1")
public class AlipayController {

    @Autowired
    private AlipayService  alipayService;

    @RequestMapping(value = "/wap/pay",method = RequestMethod.GET)
    public String pay(Model model, HttpServletRequest request ) throws UnsupportedEncodingException {

        return  "alipay/pay";
    }

    @RequestMapping(value = "/wap/pay",method = RequestMethod.POST )
    public void wap_pay(@ModelAttribute @Valid AlipayPayRequest alipayPayRequest
            ,BindingResult bindingResult
            ,HttpServletRequest request,HttpServletResponse response) throws IOException {

        PrintWriter writer = response.getWriter();
        if(bindingResult.hasErrors()){
            String errorMsg = bindingResult.getAllErrors().get(0).getDefaultMessage();
            response.setContentType("application/json;charset=UTF-8");
            writer.write(errorMsg);
            writer.flush();
            return;
        }

        try {
            response.setContentType("text/html;charset=UTF-8");
            writer.write(alipayService.wap_pay(alipayPayRequest));
            writer.flush();
        } catch (AlipayApiException e) {
            e.printStackTrace();
            response.setContentType("application/json;charset=UTF-8");
            writer.write(e.getErrMsg());
            writer.flush();
        }

    }


    @RequestMapping(value = "/query",method = RequestMethod.GET)
    public String query(Model model, HttpServletRequest request ) throws UnsupportedEncodingException {

        return  "alipay/query";
    }

    @RequestMapping(value = "/query" ,method = RequestMethod.POST,produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String  query(  @ModelAttribute @Valid AlipayQueryRequest alipayQueryRequest
            ,BindingResult bindingResult
            ,HttpServletRequest request,HttpServletResponse response ) throws IOException {


        try {

            return alipayService.query( alipayQueryRequest );
        } catch (AlipayApiException e) {
            e.printStackTrace();
            return e.getMessage();
        }


    }

    @RequestMapping(value = "/return_url" )
    @ResponseBody
    public String return_url(Model model, HttpServletRequest request ) throws UnsupportedEncodingException, AlipayApiException {

        //获取支付宝GET过来反馈信息
        Map<String,String> params = new HashMap<String,String>();
        Map requestParams = request.getParameterMap();
        for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            //乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
//            valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
            params.put(name, valueStr);
        }

        //获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以下仅供参考)//
        //商户订单号

//        String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"),"UTF-8");
        String out_trade_no = request.getParameter("out_trade_no");

        //支付宝交易号

//        String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"),"UTF-8");
        String trade_no = request.getParameter("trade_no");

        //获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以上仅供参考)//
        //计算得出通知验证结果
        //boolean AlipaySignature.rsaCheckV1(Map<String, String> params, String publicKey, String charset, String sign_type)
        boolean verify_result = AlipaySignature.rsaCheckV1(params, AlipayConfig.ALIPAY_PUBLIC_KEY, AlipayConfig.CHARSET, AlipayConfig.SIGNTYPE);

//        jc.set("alipay_return_url", new Gson().toJson(params) );
//        jc.set("alipay_return_url_res", verify_result+"" );

        // TRADE_FINISHED(表示交易已经成功结束，并不能再对该交易做后续操作);
        //        TRADE_SUCCESS(表示交易已经成功结束，可以对该交易做后续操作，如：分润、退款等);
        if(verify_result){//验证成功
            //////////////////////////////////////////////////////////////////////////////////////////
            //请在这里加上商户的业务逻辑程序代码
            //该页面可做页面美工编辑
            return  "success<br/>";
            //——请根据您的业务逻辑来编写程序（以上代码仅作参考）——

            //////////////////////////////////////////////////////////////////////////////////////////
        }else{
            //该页面可做页面美工编辑
            return "fail";
        }
    }


    @RequestMapping(value = "/notify_url" )
    @ResponseBody
    public String notify_url(Model model, HttpServletRequest request ) throws UnsupportedEncodingException, AlipayApiException {

        //获取支付宝POST过来反馈信息
        Map<String,String> params = new HashMap<String,String>();
        Map requestParams = request.getParameterMap();
        for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            //乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
            //valueStr = new String(valueStr.getBytes("ISO-8859-1"), "gbk");
            params.put(name, valueStr);
        }
        //获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以下仅供参考)//
        //商户订单号

//        String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"),"UTF-8");
        String out_trade_no = request.getParameter("out_trade_no");
        //支付宝交易号

//        String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"),"UTF-8");
        String trade_no = request.getParameter("trade_no");

        //交易状态
//        String trade_status = new String(request.getParameter("trade_status").getBytes("ISO-8859-1"),"UTF-8");
        String trade_status = request.getParameter("trade_status");

        //获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以上仅供参考)//
        //计算得出通知验证结果
        //boolean AlipaySignature.rsaCheckV1(Map<String, String> params, String publicKey, String charset, String sign_type)
        boolean verify_result = AlipaySignature.rsaCheckV1(params, AlipayConfig.ALIPAY_PUBLIC_KEY, AlipayConfig.CHARSET, AlipayConfig.SIGNTYPE);

//        jc.set("alipay_notify_url", new Gson().toJson(params) );
//        jc.set("alipay_notify_url_status", trade_status );
//        jc.set("alipay_notify_url_res", verify_result+"" );

        if(verify_result){//验证成功
            //////////////////////////////////////////////////////////////////////////////////////////
            //请在这里加上商户的业务逻辑程序代码

            //——请根据您的业务逻辑来编写程序（以下代码仅作参考）——

            if(trade_status.equals("TRADE_FINISHED")){
                //判断该笔订单是否在商户网站中已经做过处理
                //如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
                //请务必判断请求时的total_fee、seller_id与通知时获取的total_fee、seller_id为一致的
                //如果有做过处理，不执行商户的业务程序

                //注意：
                //如果签约的是可退款协议，退款日期超过可退款期限后（如三个月可退款），支付宝系统发送该交易状态通知
                //如果没有签约可退款协议，那么付款完成后，支付宝系统发送该交易状态通知。
            } else if (trade_status.equals("TRADE_SUCCESS")){
                //判断该笔订单是否在商户网站中已经做过处理
                //如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
                //请务必判断请求时的total_fee、seller_id与通知时获取的total_fee、seller_id为一致的
                //如果有做过处理，不执行商户的业务程序

                //注意：
                //如果签约的是可退款协议，那么付款完成后，支付宝系统发送该交易状态通知。
            }

            //——请根据您的业务逻辑来编写程序（以上代码仅作参考）——
            return  "success";	//请不要修改或删除

            //////////////////////////////////////////////////////////////////////////////////////////
        }else{//验证失败
            return "fail";
        }
    }


    /***
     *  生成APP支付订单信息
     * **/
    @RequestMapping(value = "/app/pay"  ,produces = "application/json;charset=UTF-8" )
    @ResponseBody
    public String formOrder( @ModelAttribute @Valid AlipayPayRequest alipayPayRequest
            ,BindingResult bindingResult
            ,HttpServletResponse response  )     {

        try {
            return alipayService.app_pay(alipayPayRequest);
        } catch (AlipayApiException e) {
            e.printStackTrace();
            return  e.getMessage();
        }
    }
}
