package cn.gotoil.znl;

/**
 * Created by Suyj <suyajiang@gotoil.cn> on 2017/1/18.8:49
 */



        import org.springframework.boot.SpringApplication;
        import org.springframework.boot.autoconfigure.SpringBootApplication;
        import org.springframework.context.ApplicationContext;
        import org.springframework.context.annotation.Bean;
        import org.springframework.context.annotation.ComponentScan;
        import org.springframework.scheduling.annotation.EnableScheduling;
        import org.springframework.transaction.annotation.EnableTransactionManagement;
        import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableScheduling
@EnableTransactionManagement
@ComponentScan("cn.gotoil")
public class ZnlApplication {

    private static ApplicationContext applicationContext;


    public static void main(String[] args) {

        SpringApplication.run(ZnlApplication.class, args);

//        applicationContext = SpringApplication.run(ZnlApplication.class, args);

//        issueServer.checkHaveLose(GameType.CQSSC.getCode(),GameType.CQSSC.getName(),200);
    }

    @Bean
//    @LoadBalanced
    protected RestTemplate restTemplate() {
        return new RestTemplate();
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