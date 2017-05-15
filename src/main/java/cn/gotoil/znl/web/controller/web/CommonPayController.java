package cn.gotoil.znl.web.controller.web;

import cn.gotoil.bill.web.annotation.Authentication;
import cn.gotoil.bill.web.interceptor.authentication.AuthenticationType;
import cn.gotoil.znl.model.domain.App;
import org.apache.commons.lang3.StringUtils;
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


    @RequestMapping(value = "/edit",method = RequestMethod.GET)
    public String index(Model model, HttpServletRequest request,
                        @RequestParam(value = "id",required = false) String recordID ){


        model.addAttribute("selectedItem", "appManage"  );
        model.addAttribute("app", app );
        model.addAttribute("stateList", App.StateEnum.getEnumList() );
        return  "app/form";
    }

}
