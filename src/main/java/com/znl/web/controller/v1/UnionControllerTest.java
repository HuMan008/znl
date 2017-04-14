package com.znl.web.controller.v1;

import com.znl.config.property.SybConstants;
import org.junit.Test;

import java.net.URLEncoder;

/**
 * Created by Suyj <suyajiang@gotoil.cn> on 2017/4/10.14:51
 */
public class UnionControllerTest {

    @Test
    public void testPay() throws Exception {

    }

    @Test
    public void testUnionRegister() throws Exception {

    }

    @Test
    public void testOrderSubmit() throws Exception {

        System.out.println(URLEncoder.encode("http://fastweb.guotongshiyou.com/v1/getSession"));
        System.out.println(URLEncoder.encode("https%3A%2F%2Fchong.qq.com%2Fphp%2Findex.php%3Fd%3D%26c%3DwxAdapter%26m%3DmobileDeal%26showwxpaytitle%3D1%26vb2ctag%3D4_2030_5_1194_60"));

        int xx = SybConstants.GateWayConsts.URL_ORDERSUBMIT.indexOf("/index.do");
        System.out.println(xx);

        String x = "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.1//EN\" \"http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd\">\n" +
                "<html xmlns=\"http://www.w3.org/1999/xhtml\">\n" +
                "<head>\n" +
                "\t<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />\n" +
                "\t<title>通联支付</title>\n" +
                "</head>\n" +
                "<body>\n" +
                "<!--登录后支付-->\n" +
                "<script>\n" +
                "   window.location=\"secure/recvMemberRequest.action\"+\"?date=\"+new Date().getTime()+\"&RecvRePaymentRequestMac=27CD7431F3A881DE405991F17553090A\";\n" +
                "</script>\n" +
                "</body>\n" +
                "</html>";

       if(x.contains("secure/recvMemberRequest.action")){
           System.out.println(1);
           x.replace("secure/recvMemberRequest.action", SybConstants.GateWayConsts.URL_ORDERSUBMIT.substring(0,xx)) ;
           System.out.println(x);
       }
    }
}