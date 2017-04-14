package com.znl.config.property;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Created by Suyj <suyajiang@gotoil.cn> on 2017/1/19.14:30
 */
@Component
@ConfigurationProperties("wxapp")
@Setter
@Getter
public class WeiXinChatConfig {

    private String sessionHost;
    private String appId;
    private String secret;
    private String grantType;

}
