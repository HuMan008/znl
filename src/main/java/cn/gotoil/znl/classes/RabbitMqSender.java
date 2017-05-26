package cn.gotoil.znl.classes;

import cn.gotoil.bill.tools.date.DateUtils;
import cn.gotoil.znl.config.rabbitmq.RabbitConfig;
import cn.gotoil.znl.model.enums.EnumNoticeDelayTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Created by think on 2017/5/26.
 */
@Component
public class RabbitMqSender implements RabbitTemplate.ConfirmCallback {

    private Logger logger = LoggerFactory.getLogger(RabbitMqSender.class);

    @Autowired
    private RabbitTemplate rabbitTemplate;


    public  void send(String msg, int delayTime){

        CorrelationData correlationId = new CorrelationData("GTSY"+ DateUtils.simpleDatetimeFormatter().format(new Date()));
        rabbitTemplate.setBeforePublishPostProcessors(new MessagePostProcessor() {
            @Override
            public Message postProcessMessage(Message message) throws AmqpException {
                message.getMessageProperties().setDelay(delayTime);
                message.getMessageProperties().setCorrelationIdString(correlationId+"");
                return message;
            }
        });
        rabbitTemplate.setConfirmCallback(this);
        logger.info("【消息发送】-->延迟{}\n 内容{}",delayTime,msg);
        rabbitTemplate.convertAndSend(RabbitConfig.exchangeName, RabbitConfig.routingKey, msg,
                correlationId);
    }



    /**
     * 消息的回调，主要是实现RabbitTemplate.ConfirmCallback接口
     * 注意，消息回调只能代表成功消息发送到RabbitMQ服务器，不能代表消息被成功处理和接受
     */
    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {

        if (ack) {
            logger.info(" 回调id:" + correlationData +"-------" +"消息已到达MQ");
        } else {
            logger.info(" 回调id:" + correlationData +"-------" +"消息没到达MQ？？？？");
        }
    }
}
