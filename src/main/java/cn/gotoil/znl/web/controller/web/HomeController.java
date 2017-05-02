package cn.gotoil.znl.web.controller.web;

import cn.gotoil.bill.web.annotation.Authentication;
import cn.gotoil.bill.web.interceptor.authentication.AuthenticationType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Suyj <suyajiang@gotoil.cn> on 2017/2/23.16:45
 */
@Controller
//@RequestMapping("/")
@Authentication(authenticationType = AuthenticationType.None)
public class HomeController {



    @RequestMapping(value = "/",method=RequestMethod.GET)

    public String home(Model model,HttpServletRequest httpServletRequest) {

        model.addAttribute("selectedItem", "index"  );

        return "index";
    }

    @RequestMapping(value = "/index",method=RequestMethod.GET)
    public String index_(Model model,HttpServletRequest httpServletRequest) {

        model.addAttribute("selectedItem", "index"  );

        return "index";
    }

    @RequestMapping(value = "/web/modifyPwd",method=RequestMethod.GET)
    public String modifyPwd(Model model,HttpServletRequest httpServletRequest) {

//        model.addAttribute("selectedItem", "index"  );

        return "users/modpwd";
    }


    @RequestMapping(value = "/web/index",method=RequestMethod.GET)
    public String index(Model model,HttpServletRequest httpServletRequest) {

        model.addAttribute("selectedItem", "index"  );


        return "index";
    }


    @RequestMapping(value="/web/logout",method=RequestMethod.GET)
    public @ResponseBody Object logout(RedirectAttributes redirectAttributes ){
        //使用权限管理工具进行用户的退出，跳出登录，给出提示信息

        return null  ;
    }
}
