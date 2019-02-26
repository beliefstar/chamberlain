package com.zhenxin.chamberlain.common.utils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author xzhen
 * @created 14:55 26/02/2019
 * @description TODO
 */
public class BeanUtils extends org.springframework.beans.BeanUtils {

    public static void initBean(Object object) {
        BeanUtils.setProperty(object, "delFlg", false);
    }

    public static void setProperty(Object object, String property, Object value) {
        try {
            Method method = object.getClass().getMethod(getSetMethodName(property), value.getClass());
            method.invoke(object, value);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public static String getSetMethodName(String property) {
        return "set" + property.substring(0, 1).toUpperCase() + property.substring(1);
    }

    public static String getGetMethodName(String property) {
        return "get" + property.substring(0, 1).toUpperCase() + property.substring(1);
    }
}
