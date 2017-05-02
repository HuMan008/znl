package cn.gotoil.znl.classes;


import cn.gotoil.bill.tools.encoder.Hash;
import cn.gotoil.znl.common.tools.SerialNumberUtil;
import cn.gotoil.znl.common.tools.date.DateUtils;
import cn.gotoil.znl.web.helper.ServletRequestHelper;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * Created by Suyj <suyajiang@gotoil.cn> on 2017/4/18.11:34
 */
@Component
public class OrderHelper {

    @Autowired
    private SerialNumberUtil serialNumberUtil;

    public  String createOrder(HttpServletRequest request) {
        String startStr = ServletRequestHelper.appId();
        String orderNoDate = DateUtils.SimpleDatetimeNoConnectorFormat().format(new Date());
        String wasteNo =serialNumberUtil.generateSerialNumber(SerialNumberUtil.NumberTypeEnum.Order,5);
             if(StringUtils.isEmpty(startStr))    {
                 return orderNoDate+wasteNo ;
             }
            return startStr+ "_"+orderNoDate+wasteNo  ;

    }

    //生成一个唯一ID
    public static String createUid(HttpServletRequest request) {
//        String startStr = request.getParameter("APPKEY")==null?"":request.getParameter("APPKEY").toString();
        String hash = Hash.md5(request.getRemoteHost()+ RandomStringUtils.randomAlphabetic(16)+new Date().getTime());
        return hash;
    }
}
