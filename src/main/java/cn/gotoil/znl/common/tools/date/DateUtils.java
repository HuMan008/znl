package cn.gotoil.znl.common.tools.date;


import java.text.SimpleDateFormat;

/**
 * Created by Suyj <suyajiang@gotoil.cn> on 2017/4/19.15:09
 */
public class DateUtils extends cn.gotoil.bill.tools.date.DateUtils {

    private static final String DATETIMENOCONNECTOR="yyyyMMddHHmmss";   //无连接符的日期时间格式化

    private static ThreadLocal<SimpleDateFormat> threadLocalDateTimeNoConnectorFormatter = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat(DATETIMENOCONNECTOR);
        }
    };
    public final static SimpleDateFormat SimpleDatetimeNoConnectorFormat(){
        SimpleDateFormat simpleDateFormat =  threadLocalDateTimeNoConnectorFormatter.get();
        return simpleDateFormat;
    }
}
