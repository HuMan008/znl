package com.znl.web.interceptor;

import com.znl.exception.ValidatorEnum;
import com.znl.exception.WebException;
import com.znl.web.helper.ServletRequestHelper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.ServletRequestHandledEvent;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Suyj <suyajiang@gotoil.cn> on 2017/4/19.10:22
 */
@Component
public class AuthenticationInterceptor implements HandlerInterceptor {

    @Value(value = "${enableAuth}")
    private boolean enableAuth;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

     /*   if(StringUtils.isEmpty(ServletRequestHelper.appId())){
            throw new WebException(ValidatorEnum.RequestNoAppId)  ;
        }
        if(StringUtils.isEmpty(ServletRequestHelper.sign())){
            throw new WebException(ValidatorEnum.RequestNoSign)  ;
        }
        if(StringUtils.isEmpty(ServletRequestHelper.userAgent())){
            throw new WebException(ValidatorEnum.RequestNoUserAgent);
        }
        if(!enableAuth){
            return true;
        }*/
        AuthenticationInterceptorSignatureVerifier authenticationInterceptorSignatureVerifier = new AuthenticationInterceptorSignatureVerifier(request);
        authenticationInterceptorSignatureVerifier.verify();

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
