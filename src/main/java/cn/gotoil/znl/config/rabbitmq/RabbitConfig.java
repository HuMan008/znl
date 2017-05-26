package cn.gotoil.znl.config.rabbitmq;

import cn.gotoil.znl.model.enums.EnumNoticeDelayTime;
import cn.gotoil.znl.service.MqRabbitServer;
import com.alibaba.fastjson.JSONObject;
import com.rabbitmq.client.Channel;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by think on 2017/5/23.
 */
//@Configuration
@SuppressWarnings("SpringJavaAutowiringInspection")
public class RabbitConfig1 {
    private static Logger logger = LoggerFactory.getLogger(RabbitConfig.class);
    private static final String queueName = "order.queue";
    private static final String exchangeName = "order.queue";

    @Autowired
    ConnectionFactory connectionFactory;

    @Autowired
    MqRabbitServer mqRabbitServer;

    @Bean
    @Scope("prototype")
    public RabbitTemplate rabbitTemplate() {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setQueue(queueName);
        template.setExchange(exchangeName);
        return template;
    }

    @Bean
    public TopicExchange topicExchange() {
        TopicExchange topicExchange = new TopicExchange(exchangeName);
        topicExchange.setDelayed(true);
        return topicExchange;
    }


    @Bean
    public Queue queue() {
        return new Queue(queueName, true);

    }

    @Bean
    Binding binding(Queue queue, Exchange delayExchange) {
        return BindingBuilder.bind(queue).to(delayExchange).with("gt.order.notify").noargs();
    }

@RabbitListener(queues = queueName)
    public SimpleMessageListenerContainer messageContainer() {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(connectionFactory);
        container.setQueues(queue());
        container.setExposeListenerChannel(true);
        container.setMaxConcurrentConsumers(1);
        container.setConcurrentConsumers(1);
        container.setAcknowledgeMode(AcknowledgeMode.AUTO);

        container.setMessageListener(new ChannelAwareMessageListener() {

            public void onMessage(Message message, Channel channel) throws Exception {
                System.out.println(message);
                /*byte[] body = message.getBody();
                System.err.println("receive msg : " + new String(body));
                channel.basicAck(message.getMessageProperties().getDeliveryTag(), false); //确认消息成功消费
                //这里开始做业务处理
                if (message != null) {
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
                            mqRabbitServer
                                    .delayMsg(notifyJson, spacingInterval);
                        } else {
                            logger.info("通知5次都失败，等待后台手工处理！");
                        }
                    }
                }*/
            }
        });

        return container;
    }

}
