package cn.gotoil.znl;

/**
 * Created by Suyj <suyajiang@gotoil.cn> on 2017/1/18.8:49
 */


import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.xml.bind.annotation.XmlRegistry;

@SpringBootApplication
@EnableScheduling
@EnableRabbit
@EnableAutoConfiguration
@ImportResource("classpath*:rabbitmq.xml")
@ComponentScan("cn.gotoil")
public class ZnlApplication {

    private static ApplicationContext applicationContext;


    public static void main(String[] args) {

        SpringApplication.run(ZnlApplication.class, args);

    }


    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }


}
