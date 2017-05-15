package cn.gotoil.znl.config.define;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

/**
 * Created by wh on 2017/4/12.
 */
@Getter
@Setter
@Component
public class AlipayConfig extends  PayBaseConfig{

    // 商户appid
    public  String APPID ;


    // 支付宝公钥
    public  String ALIPAY_PUBLIC_KEY  ;

    // 私钥 pkcs8格式的
    public  String RSA_PRIVATE_KEY  ;

    // 请求网关地址
    public static final String URL = "https://openapi.alipay.com/gateway.do";
    // 编码
    public static final String CHARSET = "UTF-8";
    // 返回格式
    public static final String FORMAT = "json";

    public static final String Product_Code = "QUICK_WAP_PAY";


    // 日志记录目录
    public static final String log_path = "/log";
    // RSA2
    public static final String SIGNTYPE = "RSA2";


    //--------------------------------------------------------------------------------------------
    // 服务器异步通知页面路径 需http://或者https://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    public static final  String notify_url="http://120.25.91.237:8086/alipay/v1/notify_url"  ;

    // 页面跳转同步通知页面路径 需http://或者https://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问 商户可以自定义同步跳转地址
    public static final String return_url="http://120.25.91.237:8086/alipay/v1/return_url"  ;
    //--------------------------------------------------------------------------------------------


    public AlipayConfig() {
    }
}
