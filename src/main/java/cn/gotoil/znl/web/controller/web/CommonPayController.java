package cn.gotoil.znl.web.controller.web;

import cn.gotoil.bill.web.annotation.Authentication;
import cn.gotoil.bill.web.interceptor.authentication.AuthenticationType;
import cn.gotoil.znl.model.domain.App;
import cn.gotoil.znl.service.CommonPayService;
import cn.gotoil.znl.web.message.request.PayRequest;
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
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
    public ModelAndView wapPay( HttpServletRequest request,Model model,HttpServletResponse httpServletResponse,
                         PayRequest payRequest , RedirectAttributes redirectAttributes){

        ModelAndView modelAndView = commonPayService.wapPay(payRequest,model,redirectAttributes,request,httpServletResponse);

        return  modelAndView;
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

}
