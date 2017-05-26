package cn.gotoil.znl.service.impl;

import cn.gotoil.znl.service.MessageProducerService;
import com.alibaba.fastjson.JSONObject;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by think on 2017/5/25.
 */
@Service
public class MessageProducerServiceImpl  implements MessageProducerService {

    @Autowired
    private AmqpTemplate delayMsgTemplate;
    @Override
    public void delayMsg(JSONObject msg, int delay) {
        final int xdelay= delay*1;
        delayMsgTemplate.convertAndSend("order.delay.notify", (Object) msg,
                new MessagePostProcessor() {

                    @Override
                    public Message postProcessMessage(Message message)
                            throws AmqpException {
                        System.out.println(msg);
                        // TODO Auto-generated method stub
                        message.getMessageProperties().setDelay(xdelay);
                        return message;
                    }
                });
    }
}
