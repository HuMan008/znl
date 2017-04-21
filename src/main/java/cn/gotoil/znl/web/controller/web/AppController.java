package cn.gotoil.znl.web.controller.web;

import cn.gotoil.znl.common.tools.RetUtil;
import cn.gotoil.znl.config.define.PageInfo;
import cn.gotoil.znl.model.domain.App;
import cn.gotoil.znl.service.AppService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;

/**
 * Created by wh on 2017/4/19.
 */
@Controller
@RequestMapping("/web/app")
public class AppController {

    @Autowired
    private AppService  appService;


    @RequestMapping(value = "/save",method = RequestMethod.POST)
    @ResponseBody
    public Object save( @RequestBody App app , Model model, HttpServletRequest request  ){

        boolean flag = appService.save(app);

        if(flag){
            return  RetUtil.getRetValue(true,"保存成功！");
        }else{
            return  RetUtil.getRetValue(false,"保存失败！");
        }
    }


    @RequestMapping(value = "/edit",method = RequestMethod.GET)
    public String index(Model model, HttpServletRequest request,
                        @RequestParam(value = "id",required = false) String recordID ){

        App app = null;
        if(StringUtils.isEmpty(recordID)){
            model.addAttribute("actionType","新增应用");
            model.addAttribute("isReadOnly", false);
            app = new App();
        }else {
            model.addAttribute("actionType","编辑应用");
            model.addAttribute("isReadOnly", true );
            app = appService.findOne(  recordID );
        }

        model.addAttribute("selectedItem", "appManage"  );
        model.addAttribute("app", app );
        model.addAttribute("stateList", App.StateEnum.getEnumList() );
        return  "app/form";
    }

    @RequestMapping(value = "/updateStatus/{id}",method = RequestMethod.GET)
    @ResponseBody
    public Object updateStatus( @PathVariable("id")  String recordID,Model model, HttpServletRequest request  )     {

       boolean flag =  appService.updateStatus(recordID);
        if(flag){
            return RetUtil.getRetValue(true,"状态修改成功！");
        }else {
            return RetUtil.getRetValue(false,"状态修改失败！");
        }

    }

    @RequestMapping(value = "/index",method = RequestMethod.GET)
    public String index(Model model, HttpServletRequest request,
                       App app,
                       @RequestParam(defaultValue = "1") Integer pageNum,
                       @RequestParam(defaultValue = PageInfo.DefaultPageSize+"") Integer pageSize)     {

        PageInfo<App> list =  appService.getAppList(pageNum,pageSize,app);
        model.addAttribute("appList", list  );
        model.addAttribute("selectedItem", "appManage"  );
        model.addAttribute("app", app );
        model.addAttribute("stateList", App.StateEnum.getEnumList() );
        return  "app/index";
    }


    @RequestMapping(value = "/save",method = RequestMethod.GET)
    public String save(Model model, HttpServletRequest request ) throws UnsupportedEncodingException {

        return  "app/form";
    }



}
