package com.hhxy.shops.utils;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

public class PoJoUtil {
    public static <T> T mapToPo(Map<String, Object> map, T t) {
        Class clazz = t.getClass();
        List<Field> fields = Arrays.stream(clazz.getDeclaredFields()).filter(field -> map.get(field.getName()) != null).collect(Collectors.toList());
        fields.forEach(field -> {
            try {
                PropertyDescriptor descriptor = new PropertyDescriptor(field.getName(), clazz);
                Method method = descriptor.getWriteMethod();
                switch (field.getType().toString()) {
                    case "class java.lang.String":
                        method.invoke(t, map.get(field.getName()));
                        break;
                    case "class java.lang.Long":
                        if (map.get(field.getName()) != null && !"".equals(map.get(field.getName()))) {
                            method.invoke(t, Long.parseLong(String.valueOf(map.get(field.getName()))));
                        }
                        break;
                    case "class java.lang.Float":
                        if (map.get(field.getName()) != null && !"".equals(map.get(field.getName()))) {
                            method.invoke(t, Float.parseFloat((String) map.get(field.getName())));
                        }
                        break;
                    case "class java.math.BigDecimal":
                        if (map.get(field.getName()) != null && !"".equals(map.get(field.getName()))) {
                            BigDecimal decimal = BigDecimal.valueOf(Double.parseDouble((String) map.get(field.getName())));
                            method.invoke(t, decimal);
                        }
                        break;
                    case "class java.util.Date":
                        if (map.get(field.getName()) != null && !"".equals(map.get(field.getName()))) {
                            Date date = DataFormat.strToDate((String) map.get(field.getName()), DataFormat.FMT_YYYY_MM_DD);
                            method.invoke(t, date);
                        }

                }
            } catch (IntrospectionException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }


        });
        return t;
    }

    public static <T> String beanToURL(T t) {
        StringBuffer sb = new StringBuffer("?");
        Class clazz = t.getClass();
        Field[] fields = clazz.getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            try {
                PropertyDescriptor propertyDescriptor = new PropertyDescriptor(fields[i].getName(), clazz);
                Method method = propertyDescriptor.getReadMethod();
                if (method.invoke(t) == null) {
                    continue;
                }
                sb.append(fields[i].getName());
                sb.append("=");
                sb.append(method.invoke(t));
                if (i < fields.length - 1) {
                    sb.append("&");
                }
            } catch (IntrospectionException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }

    public static HashMap getTableMap(List<HashMap> list) {
        //主节点
        HashMap<String, Object> hash = new HashMap<>(4);
        hash.put("code", 0);
        hash.put("msg", "");
        hash.put("count", list.size());
        hash.put("data", list.toArray());
        /**
         {
         "code": 0,
         "msg": "",
         "count": 1000,
         "data": [{}, {}]
         }
         */
        return hash;
    }


    /**
     * @param xValue 横轴做表明
     * @param yValue  数据值
     * @return
     */
    public static HashMap getEcharMap(List xValue, List yValue) {
        //主Map
        HashMap root = new HashMap();
        HashMap xAxis = new HashMap();
        xAxis.put("type", "category");
        xAxis.put("data", xValue.toArray());
        root.put("xAxis", xAxis);
        HashMap yAxis = new HashMap();
        yAxis.put("type", "value");
        root.put("yAxis", yAxis);
        HashMap series = new HashMap();
        series.put("data", yValue.toArray());
        series.put("type", "line");
        root.put("series", series);
        return root;
    }

    /**
     * @param xValue 横轴做表明
     * @param yValue  数据值
     * @Param type 属性类型
     * @return
     */
    public static HashMap getEcharMap(List xValue, List yValue, String type) {
        //主Map
        HashMap root = new HashMap();
        HashMap xAxis = new HashMap();
        xAxis.put("type", "category");
        xAxis.put("data", xValue.toArray());
        root.put("xAxis", xAxis);
        HashMap yAxis = new HashMap();
        yAxis.put("type", "value");
        root.put("yAxis", yAxis);
        HashMap series = new HashMap();
        series.put("data", yValue.toArray());
        series.put("type", type);
        root.put("series", series);
        return root;
    }
}
