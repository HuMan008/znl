package cn.gotoil.znl;

/**
 * Created by Suyj <suyajiang@gotoil.cn> on 2017/1/18.8:49
 */


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
//@EnableScheduling
@ComponentScan({"cn.gotoil.znl", "cn.gotoil.bill"})
public class ZnlApplication {

    private static ApplicationContext applicationContext;


    public static void main(String[] args) {

        SpringApplication.run(ZnlApplication.class, args);

    }


    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }


}
