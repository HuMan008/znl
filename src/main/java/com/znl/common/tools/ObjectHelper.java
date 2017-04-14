/*
 * Copyright (C) 2016.  Iusworks, Inc - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Dolphin com.iusworks.dolphin.common.tools.ObjectHelper
 *
 * Suyj <yizhishita@126.com>,  November 2016
 *
 * LastModified: 10/12/16 12:06 PM
 *
 */

package com.znl.common.tools;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by cluries on 12/10/2016.
 */
public class ObjectHelper {

    private static ObjectWriter objectWriter;
    private static Logger logger = LoggerFactory.getLogger(ObjectHelper.class);

    static {
        objectWriter = new ObjectMapper().writer();
    }

    public static Map<String, Object> introspect(Object obj) throws Exception {
        Map<String, Object> result = new HashMap<>();
        BeanInfo info = Introspector.getBeanInfo(obj.getClass());
        for (PropertyDescriptor pd : info.getPropertyDescriptors()) {
            Method reader = pd.getReadMethod();
            if (reader != null && !"class".equals(pd.getName())) {
                result.put(pd.getName(), reader.invoke(obj));
            }
        }
        return result;
    }

    public static Map<String, String> introspectStringValueMap(Object obj) throws Exception {
        Map<String, String> result = new HashMap<>();
        BeanInfo info = Introspector.getBeanInfo(obj.getClass());
        for (PropertyDescriptor pd : info.getPropertyDescriptors()) {
            Method reader = pd.getReadMethod();
            if (reader != null && !"class".equals(pd.getName())) {
                result.put(pd.getName(), reader.invoke(obj)==null?"":reader.invoke(obj).toString());
            }
        }
        return result;
    }
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
                setter.invoke(obj, map.get(property.getName()));
            }
        }

        return obj;
    }

    public static Object mapToObject(String str, Class<?> beanClass) throws Exception {
        Map<String,Object> map =    introspect(str);
        return mapToObject(map,beanClass);
    }

    public static String jsonString(Object object) {
        try {
            return objectWriter.writeValueAsString(object);
        } catch (Exception ex) {
            logger.error("{}", ex);
        }

        return "";
    }

    public static boolean allPropertiesIsNull(Object obj) {
        try {
            BeanInfo info = Introspector.getBeanInfo(obj.getClass());
            for (PropertyDescriptor pd : info.getPropertyDescriptors()) {
                Method reader = pd.getReadMethod();
                if (reader != null && !"class".equals(pd.getName())) {
                    if (reader.invoke(obj) != null) {
                        return false;
                    }
                }
            }
            return true;
        } catch (Exception ex) {
            logger.error("{}", ex);
            return true;
        }

    }
}
