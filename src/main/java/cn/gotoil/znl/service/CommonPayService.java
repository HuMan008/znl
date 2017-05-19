package cn.gotoil.znl.service;

import cn.gotoil.znl.web.message.request.PayRequest;
import cn.gotoil.znl.web.message.request.QueryRequest;
import com.alipay.api.AlipayApiException;
import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;

/**
 * Created by wh on 2017/5/11.
 */
public interface CommonPayService {

    String orderStatusQuery(QueryRequest queryRequest) throws AlipayApiException ;

//    String  wapPay(PayRequest request);
    ModelAndView wapPay(PayRequest request, Model model, RedirectAttributes attributes, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse);

    String  sdkPay(PayRequest request) throws AlipayApiException, UnsupportedEncodingException;

}
