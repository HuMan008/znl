package cn.gotoil.znl.classes;

import cn.gotoil.bill.tools.encoder.Hash;
import cn.gotoil.znl.web.helper.ServletRequestHelper;
import org.apache.commons.lang3.RandomStringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * Created by Suyj <suyajiang@gotoil.cn> on 2017/4/18.11:34
 */
public class OrderHelper {
    public static String createOrder(HttpServletRequest request) {
        String startStr = ServletRequestHelper.appId();

            return startStr+ "_"+RandomStringUtils.randomNumeric(32)  ;

    }

    //生成一个唯一ID
    public static String createUid(HttpServletRequest request) {
//        String startStr = request.getParameter("APPKEY")==null?"":request.getParameter("APPKEY").toString();
        String hash = Hash.md5(request.getRemoteHost()+ RandomStringUtils.randomAlphabetic(16)+new Date().getTime());
        return hash;
    }
}
