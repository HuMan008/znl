package cn.gotoil.znl.web.interceptor;

import cn.gotoil.znl.exception.AuthenticationException;
import cn.gotoil.znl.exception.ValidatorEnum;
import cn.gotoil.znl.web.helper.UserAgentAnalyzer;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.time.Instant;

/**
 * Created by Suyj <suyajiang@gotoil.cn> on 2017/4/19.10:34
 */
public class AuthenticationInterceptorSignatureVerifier {


    private static Logger logger = LoggerFactory.getLogger(AuthenticationInterceptorSignatureVerifier.class);

    private final static ObjectMapper objectMapper = new ObjectMapper();


    private HttpServletRequest request;
    private int current;

    private int XT;

    private String AppId;
    private String Sign;

    public AuthenticationInterceptorSignatureVerifier(HttpServletRequest request) {
        this.request = request;
        current = (int) Instant.now().getEpochSecond();
    }

    public void verify() {
        //参数校验
        if(!checker())    throw new AuthenticationException(ValidatorEnum.Entegrity);
        //签名校验
        // TODO: 2017/4/19
        //根据APPID 找到 APP信息
        //找到密码 与SIGN校验
    }

    private boolean checker()   {
        if (!userAgentChecker()) {
            logger.error("UA Error");
            return false;
        }
        AppId= request.getHeader("AppId");
        if(AppId.length()>8){
            return false;
        }
        if(StringUtils.isNotEmpty(AppId)&& !(Character.isDigit(AppId.charAt(0)) || Character.isLetter(AppId.charAt(0))) ){
            return false;
        }

        Sign =request.getHeader("Sign");
        XT=request.getIntHeader("XT");
        if(StringUtils.isEmpty(AppId)||StringUtils.isEmpty(Sign)   ){
            return false;
        }
        return XT > 1477375755;
    }


    private boolean userAgentChecker() {
        String userAgent = request.getHeader("User-Agent");

        boolean uaValid = new UserAgentAnalyzer(userAgent).isValidated();
        if (!uaValid) {

        }
        return uaValid;
    }
}
