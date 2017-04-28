package cn.gotoil.znl;

/**
 * Created by Suyj <suyajiang@gotoil.cn> on 2017/1/18.8:49
 */



        import cn.gotoil.znl.config.define.AlipayConfig;
        import org.springframework.boot.SpringApplication;
        import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
        import org.springframework.boot.autoconfigure.SpringBootApplication;
        import org.springframework.boot.autoconfigure.web.MultipartAutoConfiguration;
        import org.springframework.boot.context.properties.EnableConfigurationProperties;
        import org.springframework.context.ApplicationContext;
        import org.springframework.context.annotation.ComponentScan;
        import org.springframework.scheduling.annotation.EnableAsync;
        import org.springframework.scheduling.annotation.EnableScheduling;
        import org.springframework.transaction.annotation.EnableTransactionManagement;

//@EnableAutoConfiguration(exclude = {MultipartAutoConfiguration.class})
@SpringBootApplication
@EnableScheduling
@EnableTransactionManagement
@EnableAsync
@ComponentScan({"cn.gotoil.znl", "cn.gotoil.bill"})
@EnableConfigurationProperties(AlipayConfig.class)
public class Application {

    private static ApplicationContext applicationContext;


    public static void main(String[] args) {
        applicationContext = SpringApplication.run(Application.class, args);
//        issueServer.checkHaveLose(GameType.CQSSC.getCode(),GameType.CQSSC.getName(),200);
    }


    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    /*@Bean(destroyMethod = "shutdown")
    RedissonClient redisson() throws IOException {
        Config config = new Config();
        //集群的时候
*//*        config.useClusterServers()
                .addNodeAddress("201.201.201.173:6379");*//*
        //单机版
        config.useSingleServer().setAddress("201.201.201.173:6379");

        return Redisson.create(config);
    }

    @Bean
    CacheManager cacheManager(RedissonClient redissonClient) {
        Map<String, CacheConfig> config = new HashMap<String, CacheConfig>();
        // 创建一个名称为"testMap"的缓存，过期时间ttl为24秒钟，同时最长空闲时maxIdleTime为12秒钟。
        config.put("testMap", new CacheConfig(24 * 60 * 1000, 12 * 60 * 1000));
        return new RedissonSpringCacheManager(redissonClient, config);
    }*/
}
