package cn.gotoil.znl.config.rabbitmq;

import cn.gotoil.bill.tools.date.DateUtils;
import cn.gotoil.znl.classes.RabbitMqSender;
import cn.gotoil.znl.model.enums.EnumNoticeDelayTime;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import javax.rmi.CORBA.UtilDelegate;
import java.util.Date;

/**
 * Created by think on 2017/5/23.
 */
@Configuration
@SuppressWarnings("SpringJavaAutowiringInspection")
public class RabbitConfig {

    private static Logger logger = LoggerFactory.getLogger(RabbitConfig.class);

    private static final String queueName = "order_queue";
    public static final String exchangeName = "order_exchange";


    public static final String routingKey = "gt.order.notify";

    @Autowired
    ConnectionFactory connectionFactory;

    @Bean
    public TopicExchange topicExchange() {
        TopicExchange topicExchange = new TopicExchange(exchangeName);
        topicExchange.setDelayed(true);
        topicExchange.setIgnoreDeclarationExceptions(true);
        return topicExchange;
    }


    @Bean
    public Queue queue() {
        //持久化队列 第二个参数
        return new Queue(queueName, true);
    }

    @Bean
    Binding binding() {
        return BindingBuilder.bind(queue()).to(topicExchange()).with(routingKey);
    }

    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public RabbitTemplate rabbitTemplate() {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setQueue(queueName);
        template.setExchange(exchangeName);
        return template;
    }

@Autowired
    RabbitMqSender rabbitMqSender;

    @Bean
    /**
     * 监听队列 queue
     */
    public SimpleMessageListenerContainer messageContainer() {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(connectionFactory);
        container.setQueues(queue());
        container.setExposeListenerChannel(true);
        container.setMaxConcurrentConsumers(1);
        container.setConcurrentConsumers(1);
        container.setAcknowledgeMode(AcknowledgeMode.MANUAL); //设置确认模式手工确认
        container.setMessageListener(new ChannelAwareMessageListener() {

            public void onMessage(Message message, com.rabbitmq.client.Channel channel) throws Exception {
                byte[] body = message.getBody();
                logger.info("接受收到消息:-->消息Id{}\n\t消息内容：\n{}" ,message.getMessageProperties().getCorrelationIdString(), new String(body));
                //开始处理body

                JSONObject notifyJson =JSONObject.parseObject(new String(body));
                String notifyUrl = notifyJson.getString("notifyUrl");
                String notifyContent = notifyJson.getString("notifyContent");
//            String result = HttpConnectionUtil.postMessage(notifyUrl, notifyContent);
                String result = "";
                if (StringUtils.isEmpty(result)) { // 通知失败 进入重发机制
                    int newNotifyCount = notifyJson.getIntValue("notifyCount") + 1; //已经通知的次数
                    if (newNotifyCount < 4) {
                        notifyJson.put("notifyCount", newNotifyCount);
                        int spacingInterval = EnumNoticeDelayTime.geByCount(newNotifyCount).getSecond();

                        rabbitMqSender.send(notifyJson.toJSONString(),spacingInterval);
                        logger.info("发送了延迟消息ID【{}】\n发送时间 {}\n延迟{}秒\n第{}次",message.getMessageProperties().getCorrelationIdString(), DateUtils.simpleDatetimeFormatter().format(new Date()),spacingInterval/1000,newNotifyCount);
                    } else {
                        logger.info("通知5次都失败，等待后台手工处理！");
                    }
                }


                channel.basicAck(message.getMessageProperties().getDeliveryTag(), false); //确认消息成功消费

            }

        });
        return container;
    }






}
