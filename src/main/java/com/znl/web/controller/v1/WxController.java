package com.znl.web.controller.v1;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.ImmutableMap;
import com.znl.common.tools.db.RedisUtil;
import com.znl.common.tools.encoder.AES;
import com.znl.config.SysConsts;
import com.znl.exception.MicroServerException;
import com.znl.exception.WebException;
import com.znl.exception.WebExceptionEnum;
import com.znl.model.domain.WxUser;
import com.znl.service.WxService;
import com.znl.web.controller.BaseController;
import io.swagger.annotations.ApiOperation;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;
import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * Created by Suyj <suyajiang@gotoil.cn> on 2017/1/18.9:18
 */
@RestController
public class WxController extends BaseController {

    @Autowired
    private WxService wxService;

    @Autowired
    private RedisUtil redisUtil;

//    @ResponseBody
    @ApiOperation(value = "微信用户登录后，给他一个Session Id")
    @RequestMapping(value = "/getSession", method = RequestMethod.GET)
    public Object getSessionId(@RequestParam @NotNull String code,Model model) {
        if("undefined".equals(code)){
            return null;
        }
        Map<String,Object> wxSessionMap =  wxService.getWxSession(code);
        if(wxSessionMap==null || wxSessionMap.isEmpty()){
            return  new WebException(WebExceptionEnum.NoSupportJsCode);
        }
        //获取异常
        if(wxSessionMap.containsKey("errcode")){
            return new MicroServerException(-1, wxSessionMap.get("errcode").toString());
        }
        String wxOpenId = (String)wxSessionMap.get("openid");
        String wxSessionKey = (String)wxSessionMap.get("session_key");
        Long expires =Long.parseLong( wxSessionMap.get("expires_in").toString()) ;
        model.addAttribute("openid",wxOpenId);
        return  new ModelAndView("union/wxpay");
//        String thirdSesion = wxService.create3rdSession(wxOpenId, wxSessionKey, expires);
//        return   ImmutableMap.of("sessionId",thirdSesion)  ;
    }

    @RequestMapping("wxpay")
    public Object pay(HttpServletRequest request , HttpServletResponse response ,Model model){
        System.out.println(11);
        return ""  ;
    }


    @ApiOperation(value = "解密用户数据获取openId")
    @RequestMapping(value = "/decodeUserInfo", method = RequestMethod.GET)
    @ResponseBody
    public Object decodeUserInfo(@RequestParam(required = true, value = "encryptedData") String encryptedData,
                                 @RequestParam(required = true, value = "iv") String iv,
                                 @RequestParam(required = true, value = "sessionId") String sessionId) {

        //从缓存中获取session_key

        Object wxSessionObj = redisUtil.get(sessionId);
        if(null == wxSessionObj){
            return new WebException(WebExceptionEnum.UnknowUser);
        }
        String wxSessionStr = (String)wxSessionObj;
        String sessionKey = wxSessionStr.split("#")[0];

        AES aes = new AES();
        byte[] resultByte = aes.decrypt(Base64.decodeBase64(encryptedData), Base64.decodeBase64(sessionKey), Base64.decodeBase64(iv));
        if (null != resultByte && resultByte.length > 0) {
            String userInfo = null;
            try {
                userInfo = new String(resultByte, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            System.out.println(userInfo);

            WxUser wxUser =JSON.parseObject(userInfo, WxUser.class)  ;
            wxService.proccess(wxUser) ;
            return userInfo;
        }

        return new WebException(WebExceptionEnum.ParseWxUserInfoError);
    }
}
