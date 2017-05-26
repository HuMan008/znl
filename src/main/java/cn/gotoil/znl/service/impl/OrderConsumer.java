package cn.gotoil.znl.service.impl;

import cn.gotoil.znl.model.enums.EnumNoticeDelayTime;
import cn.gotoil.znl.service.MessageProducerService;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by think on 2017/5/25.
 */
public class OrderConsumer {

    private static Logger logger = LoggerFactory.getLogger(OrderConsumer.class);

    @Autowired
    private MessageProducerService messageProducerService;


    public void delayMsg(Object obj) {
        logger.info("[延时消息]" + obj);
        if (obj != null) {
            JSONObject notifyJson = JSONObject.parseObject(JSONObject.toJSONString(obj));
            String notifyUrl = notifyJson.getString("notifyUrl");
            String notifyContent = notifyJson.getString("notifyContent");
//            String result = HttpConnectionUtil.postMessage(notifyUrl, notifyContent);
            String result = "";
            if (StringUtils.isEmpty(result)) { // 通知失败 进入重发机制
                int newNotifyCount = notifyJson.getIntValue("notifyCount") + 1; //已经通知的次数
                if (newNotifyCount < 5) {
                    notifyJson.put("notifyCount", newNotifyCount);
                    int spacingInterval = EnumNoticeDelayTime.geByCount(newNotifyCount).getSecond();
                    ;
                    messageProducerService
                            .delayMsg(notifyJson, spacingInterval);
                } else {
                    logger.info("通知5次都失败，等待后台手工处理！");
                }
            }
        }
    }


}
