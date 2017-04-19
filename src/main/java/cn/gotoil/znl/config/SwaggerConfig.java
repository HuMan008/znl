package cn.gotoil.znl.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Swagger的配置  目的是提供API说明及测试页面
 * 访问方式  http://127.0.0.1:8080/swagger/index.html
 * http://localhost:18081/swagger-ui.html
 */
@Configuration
@EnableWebMvc
@EnableSwagger2

public class SwaggerConfig {

    @Bean
    public Docket api() {
        Docket docket = new Docket(DocumentationType.SWAGGER_2)
                .select()
//                .apis(RequestHandlerSelectors.any())
                .apis(RequestHandlerSelectors.basePackage("com.znl.web.controller.v1"))
                .paths(PathSelectors.any())
//                .paths(PathSelectors.regex("\\s+Controller"))
                .build();
//        docket.apiInfo(new ApiInfo("", "彩票查询服务API", "1.0", "urn:tos", ApiInfo.DEFAULT_CONTACT, "国通石油", "http://www.gt680.com"));
        return docket;
    }

    /*private SpringSwaggerConfig springSwaggerConfig;

    @Autowired
    public void setSpringSwaggerConfig(SpringSwaggerConfig springSwaggerConfig) {
        this.springSwaggerConfig = springSwaggerConfig;
    }

    public SwaggerSpringMvcPlugin customImplementation() {
        return new SwaggerSpringMvcPlugin(this.springSwaggerConfig)
                .apiInfo(new ApiInfo("api", "desc", null, null, null, null))
                .useDefaultResponseMessages(false)
                .includePatterns("/test*//*");
    }*/


}