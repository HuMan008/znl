package com.znl.web.controller.v1;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.constraints.NotNull;

/**
 * Created by Suyj <suyajiang@gotoil.cn> on 2017/4/13.18:06
 */
@Controller
public class UnionController01 {

    @RequestMapping("/pay")
    public String toPay(@RequestParam @NotNull String code,RedirectAttributes attr){
        attr.addAttribute("code",code);
        return "forward:/v1/pay";
    }
}
