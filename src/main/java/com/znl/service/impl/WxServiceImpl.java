package com.znl.service.impl;

import com.alibaba.fastjson.JSON;
import com.znl.common.tools.db.RedisUtil;
import com.znl.common.tools.http.HttpUtil;
import com.znl.config.SysConsts;
import com.znl.config.property.WeiXinChatConfig;
import com.znl.model.domain.AppUser;
import com.znl.model.domain.WxUser;
import com.znl.service.AppUserService;
import com.znl.service.WxService;
import org.apache.commons.lang3.RandomStringUtils;
import org.bson.types.ObjectId;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Created by Suyj <suyajiang@gotoil.cn> on 2017/1/18.10:02
 */
@Service
public class WxServiceImpl implements WxService {

    @Autowired
    private StringRedisTemplate stringRedisTemplate ;

    @Autowired
    private WeiXinChatConfig weiXinChatConfig;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private AppUserService appUserServer;

    /**
     * 创建一个sessionId
     * @param wxJsCode
     */
    @Override
    public Map getWxSession(String wxJsCode){
        StringBuffer sb = new StringBuffer();
        sb.append("appid=").append(weiXinChatConfig.getAppId());
        sb.append("&secret=").append(weiXinChatConfig.getSecret());
//        sb.append("&js_code=").append(wxJsCode);
        sb.append("&code=").append(wxJsCode);
        sb.append("&grant_type=").append(weiXinChatConfig.getGrantType());

        String res = HttpUtil.sendGet(weiXinChatConfig.getSessionHost() ,sb.toString());
        if(res == null || res.equals("")){
            return null;
        }
        System.out.println("响应: "+ res);
        return JSON.parseObject(res, Map.class);
    }


    /**
     * 缓存微信openId和session_key
     * @param wxOpenId		微信用户唯一标识
     * @param wxSessionKey	微信服务器会话密钥
     * @param expires		会话有效期, 以秒为单位, 例如2592000代表会话有效期为30天
     * @return
     */
    @Override
    public String create3rdSession(String wxOpenId, String wxSessionKey, Long expires){
        String thirdSessionKey = RandomStringUtils.randomAlphanumeric(64);
        StringBuffer sb = new StringBuffer();
        sb.append(wxSessionKey).append("#").append(wxOpenId);
        redisUtil.add(thirdSessionKey, expires, sb.toString());
        return thirdSessionKey;
    }

    /**
     * 判断微信登录的用户时候有app的用户，如果没有就创建一个
     * @param wxUser
     */
    @Override
    public void proccess(WxUser wxUser) {
        AppUser appUser = new AppUser();
        BeanUtils.copyProperties(wxUser,appUser);
        appUser.setId(wxUser.getOpenId());
        appUser.setWxOpenId(wxUser.getOpenId());
        appUser.setAppId(wxUser.getWatermark().getAppid());
        appUser.setAvatar(wxUser.getAvatarUrl());
        appUserServer.process(appUser);
    }
}
