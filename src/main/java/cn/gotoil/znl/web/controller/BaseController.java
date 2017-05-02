package cn.gotoil.znl.web.controller;

import cn.gotoil.znl.classes.OrderHelper;
import cn.gotoil.znl.config.property.SysConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by wh on 2017/4/21.
 */

@RestController
@RequestMapping("/pay")
public class BaseController {
    @Autowired
    protected OrderHelper orderHelper;

    @Autowired
    protected SysConfig sysConfig;
}
