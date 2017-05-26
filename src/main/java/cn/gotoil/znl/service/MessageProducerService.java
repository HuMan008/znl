package cn.gotoil.znl.service;

import com.alibaba.fastjson.JSONObject;

/**
 * Created by think on 2017/5/25.
 */
public interface MessageProducerService {
    void delayMsg(JSONObject msg, int delay);
}
