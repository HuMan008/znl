package com.znl.web.controller.v1;

import com.sun.xml.internal.ws.api.message.Packet;
import com.znl.model.domain.App;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;

/**
 * Created by wh on 2017/4/19.
 */
@Controller
@RequestMapping("/app/v1")
public class AppController {


    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public String list(Model model, HttpServletRequest request,App app ) throws UnsupportedEncodingException {

        model.addAttribute("app", app );
        model.addAttribute("stateList", App.StateEnum.getEnumList() );
        return  "test/list";
    }


    @RequestMapping(value = "/save",method = RequestMethod.GET)
    public String save(Model model, HttpServletRequest request ) throws UnsupportedEncodingException {

        return  "test/app_manage";
    }

    @RequestMapping(value = "/save",method = RequestMethod.POST )
    public String save_(Model model, HttpServletRequest request ) throws UnsupportedEncodingException {

        return  "test/app_manage";
    }

}
