package cn.gotoil.znl.service.impl;

import cn.gotoil.znl.model.enums.EnumNoticeDelayTime;
import cn.gotoil.znl.model.enums.EnumPayType;
import cn.gotoil.znl.service.MqRabbitServer;
import com.alibaba.fastjson.JSONObject;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Envelope;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.MessagePropertiesConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by think on 2017/5/22.
 */

public class MqRabbitServiceImpl implements MqRabbitServer {

    @Resource
    RabbitTemplate rabbitTemplate;


    @Override
    public void sendMsg(JSONObject msg, int delay) {
        final int xdelay= delay*1;
        rabbitTemplate.convertAndSend("gt.order.notify", (Object) msg,
                new MessagePostProcessor() {
                    @Override
                    public Message postProcessMessage(Message message)
                            throws AmqpException {
                        message.getMessageProperties().setDeliveryMode(MessageDeliveryMode.PERSISTENT);
                        message.getMessageProperties().setDelay(xdelay);
                        return message;
                    }
                });
    }
}
