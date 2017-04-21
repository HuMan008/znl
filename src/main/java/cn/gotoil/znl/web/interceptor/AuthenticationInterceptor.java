package cn.gotoil.znl.web.interceptor;

import cn.gotoil.bill.web.interceptor.authentication.hashcompare.HashcompareAuthenticationInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Suyj <suyajiang@gotoil.cn> on 2017/4/19.10:22
 */
@Component
public class AuthenticationInterceptor  extends HashcompareAuthenticationInterceptor {

    @Value(value = "${enableAuth}")
    private boolean enableAuth;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if(!enableAuth)    {
            return true;
        }
       // TODO: 2017/4/20
        String uri = request.getRequestURI();
        if(uri.startsWith("/web")){
            return true;
        }
        super.preHandle(request,response,handler);
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
/*        AuthenticationInterceptorSignatureVerifier authenticationInterceptorSignatureVerifier = new AuthenticationInterceptorSignatureVerifier(request);
        authenticationInterceptorSignatureVerifier.verify();*/

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
