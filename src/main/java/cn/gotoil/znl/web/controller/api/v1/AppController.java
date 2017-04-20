package cn.gotoil.znl.web.controller.api.v1;

import cn.gotoil.znl.config.define.PageInfo;
import cn.gotoil.znl.model.domain.App;
import cn.gotoil.znl.service.AppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;

/**
 * Created by wh on 2017/4/19.
 */
@Controller
@RequestMapping("/app/v1")
public class AppController {

    @Autowired
    private AppService  appService;

    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public String list(Model model, HttpServletRequest request,
                       App app,
                       @RequestParam(defaultValue = "1") Integer pageNum,
                       @RequestParam(defaultValue = PageInfo.DefaultPageSize+"") Integer pageSize) throws UnsupportedEncodingException {

        PageInfo<App> list =  appService.getAppList(pageNum,pageSize,app);
        model.addAttribute("appList", list  );
        model.addAttribute("app", app );
        model.addAttribute("stateList", App.StateEnum.getEnumList() );
        return  "app/list";
    }


    @RequestMapping(value = "/save",method = RequestMethod.GET)
    public String save(Model model, HttpServletRequest request ) throws UnsupportedEncodingException {

        return  "app/app_manage";
    }

    @RequestMapping(value = "/save",method = RequestMethod.POST )
    public String save_(Model model, HttpServletRequest request ) throws UnsupportedEncodingException {

        return  "app/app_manage";
    }

}
