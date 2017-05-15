package cn.gotoil.znl.web.controller.web;

import cn.gotoil.bill.web.annotation.Authentication;
import cn.gotoil.bill.web.interceptor.authentication.AuthenticationType;
import cn.gotoil.znl.model.domain.App;
import cn.gotoil.znl.service.CommonPayService;
import cn.gotoil.znl.web.message.request.PayRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by wh on 2017/5/11.
 */
@Controller
@RequestMapping("/web/common/pay")
@Authentication(authenticationType = AuthenticationType.None)
public class CommonPayController {

    @Autowired
    private CommonPayService  commonPayService;

    @RequestMapping(value = "/pay" )
    public void index(Model model, HttpServletRequest request,
                        PayRequest payRequest ){

        commonPayService.pay(payRequest);

    }

}
