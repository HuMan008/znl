package com.znl.service;

import com.znl.model.domain.WxUser;

import java.util.Map;

/**
 * Created by Suyj <suyajiang@gotoil.cn> on 2017/1/18.10:16
 */
public interface WxService {
    Map getWxSession(String wxJsCode);

    String create3rdSession(String wxOpenId, String wxSessionKey, Long expires);

    void proccess(WxUser wxUser);
}
