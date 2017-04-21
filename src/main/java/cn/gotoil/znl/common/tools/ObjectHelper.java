package cn.gotoil.znl.common.tools;

import org.apache.commons.lang3.StringUtils;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Suyj <suyajiang@gotoil.cn> on 2017/4/19.15:15
 */
public class ObjectHelper extends cn.gotoil.bill.tools.ObjectHelper {
    public static Map<String, String> introspectStringValueMapValueNotEmpty(Object obj) throws Exception {
        Map<String, String> result = new HashMap<>();
        BeanInfo info = Introspector.getBeanInfo(obj.getClass());
        for (PropertyDescriptor pd : info.getPropertyDescriptors()) {
            Method reader = pd.getReadMethod();
            if (reader != null && !"class".equals(pd.getName())) {
                if(reader.invoke(obj)!=null && StringUtils.isNotEmpty(reader.invoke(obj).toString()))                          {
                    result.put(pd.getName(), reader.invoke(obj).toString());
                }

            }
        }
        return result;
    }


    public static Object mapToObject(Map<String, Object> map, Class<?> beanClass) throws Exception {
        if (map == null)
            return null;

        Object obj = beanClass.newInstance();

        BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
        PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
        for (PropertyDescriptor property : propertyDescriptors) {
            Method setter = property.getWriteMethod();
            if (setter != null) {
                System.out.println(property.getName());
                setter.invoke(obj, map.get(property.getName()));
            }
        }

        return obj;
    }

    public static Object mapToObject(String str, Class<?> beanClass) throws Exception {
        Map<String,Object> map =    introspect(str);
        return mapToObject(map,beanClass);
    }

    /**
     * 通联J黑烦，返回的字符串就是这种类似request请求字符串
     * 如：payDatetime=20170411164636&userName=&credentialsType=&pan=&txOrgId=&ext1=%3CUSER%3E170410792118546%3C%2FUSER%3E&payAmount=1&returnDatetime
     * =20170420185415&credentialsNo=&issuerId=&signMsg=45BAEDDB16EB2665D69F0EB9FC69544F&payType=0&language=1&errorCode=&merchantId=008500189990304
     *  &orderDatetime=20170411164619&version=v1.0&orderNo=order_001&ext2=&signType=0&orderAmount=1&extTL=&paymentOrderId=201704111646298209&payResult=1&
     * @param str
     * @return
     */
//    payDatetime=20170411164636&userName=&credentialsType=&pan=&txOrgId=&ext1=%3
    public static Map<String,String> stringToMap(String str){
        Map<String,String> map = new HashMap<>();
        String[] arry= str.split("&");
        for(String a  :arry){
            String aa[] = a.split("=") ;
            if(aa.length==1){
                map.put(aa[0],"");
            }else if(aa.length==2){
                map.put(aa[0],aa[1]);
            }
        }

        return map;
    }


}
