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
}
