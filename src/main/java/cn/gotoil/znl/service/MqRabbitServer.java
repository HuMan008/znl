package cn.gotoil.znl.service;

import cn.gotoil.znl.model.enums.EnumNoticeDelayTime;
import com.alibaba.fastjson.JSONObject;

/**
 * Created by think on 2017/5/22.
 */
public interface MqRabbitServer {
    /**
     *
     * @param msg 消息内容
     * @param delay 延时时长
     */
    void sendMsg(JSONObject msg, int delay);
}
