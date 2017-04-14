package com.znl.config.property;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Created by Suyj <suyajiang@gotoil.cn> on 2017/4/14.17:20
 */
@Component
@ConfigurationProperties("sysconfig")
@Setter
@Getter
public class SysConfig {

    private String runServerSchema    ;
    private String runServerHostName;
}
