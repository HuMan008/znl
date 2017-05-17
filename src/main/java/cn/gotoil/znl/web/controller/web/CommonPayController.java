package cn.gotoil.znl.web.controller.web;

import cn.gotoil.bill.web.annotation.Authentication;
import cn.gotoil.bill.web.interceptor.authentication.AuthenticationType;
import cn.gotoil.znl.model.domain.App;
import cn.gotoil.znl.service.CommonPayService;
import cn.gotoil.znl.web.message.request.PayRequest;
import cn.gotoil.znl.web.message.request.QueryRequest;
import cn.gotoil.znl.web.message.response.alipay.WapPayResponse;
import com.alipay.api.AlipayApiException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;

/**
 * Created by wh on 2017/5/11.
 */
@Controller
@RequestMapping("/web/common/pay")
@Authentication(authenticationType = AuthenticationType.None)
public class CommonPayController {

    @Autowired
    private CommonPayService  commonPayService;

    /**网页支付**/
    @RequestMapping(value = "/wapPay" )
    @Authentication(authenticationType = AuthenticationType.None)
    public String wapPay(Model model, HttpServletRequest request,
                        PayRequest payRequest ){

        String forwardUrl = commonPayService.wapPay(payRequest);

        return  forwardUrl;
    }

    /**sdk支付**/
    @RequestMapping(value = "/sdkPay" )
    @ResponseBody
    @Authentication(authenticationType = AuthenticationType.None)
    public String sdkPay(Model model, HttpServletRequest request,
                        PayRequest payRequest ) throws AlipayApiException, UnsupportedEncodingException {

        String str = commonPayService.sdkPay(payRequest);

        return  str;
    }

    /**订单 支付状态 主动查询**/
    @RequestMapping(value = "/orderStatusQuery" )
    @ResponseBody
    @Authentication(authenticationType = AuthenticationType.None)
    public String orderStatusQuery(Model model, HttpServletRequest request,
                                   QueryRequest queryRequest ) throws AlipayApiException, UnsupportedEncodingException {

        String str = commonPayService.orderStatusQuery(queryRequest);

        return  str;
    }

}
