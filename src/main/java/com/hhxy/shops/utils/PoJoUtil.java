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
     * @param name  图表题
     * @param lines 线名称
     * @param list  线数据
     * @return
     */
    public static HashMap getEcharMap(String name, List<String> lines, List<HashMap> list) {
        //主Map
        HashMap root = new HashMap();
        HashMap title = new HashMap(1), tooltip = new HashMap(1), legend = new HashMap(1), grid = new HashMap(),
                xAxis = new HashMap(), yAxis = new HashMap();
        title.put("text", name);
        root.put("title", title);
        tooltip.put("trigger", "axis");
        //设置横坐标
        Date now = new Date();
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(now);
        xAxis.put("type", "category");
        xAxis.put("boundaryGap", false);
        String[] row = new String[7];
        row[6] = DataFormat.formatDate(calendar.getTime(), DataFormat.FMT_YYYY_MM_DD);
        for (int i = 5; i >= 0; i--) {
            calendar.add(Calendar.DATE, -1);
            row[i] = DataFormat.formatDate(calendar.getTime(), DataFormat.FMT_YYYY_MM_DD);
        }
        xAxis.put("date", row);
        root.put("xAxis", xAxis);
        //设置线名称
        legend.put("data", lines.toArray());
        root.put("legend", legend);
        grid.put("left", "3%");
        grid.put("right", "4%");
        grid.put("bottom", "3%");
        grid.put("containLabel", true);
        root.put("grid", grid);
        //设置纵轴
        yAxis.put("type", "value");
        root.put("yAxis", yAxis);
        //设置数据
        root.put("series", list.toArray());
        /**
         *     yAxis: {
         *         type: 'value'
         *     },
         *     series: [
         *         {
         *             name: '邮件营销',
         *             type: 'line',
         *             stack: '总量',
         *             data: [120, 132, 101, 134, 90, 230, 210]
         *         },
         *         {
         *             name: '联盟广告',
         *             type: 'line',
         *             stack: '总量',
         *             data: [220, 182, 191, 234, 290, 330, 310]
         *         },
         *         {
         *             name: '视频广告',
         *             type: 'line',
         *             stack: '总量',
         *             data: [150, 232, 201, 154, 190, 330, 410]
         *         },
         *         {
         *             name: '直接访问',
         *             type: 'line',
         *             stack: '总量',
         *             data: [320, 332, 301, 334, 390, 330, 320]
         *         },
         *         {
         *             name: '搜索引擎',
         *             type: 'line',
         *             stack: '总量',
         *             data: [820, 932, 901, 934, 1290, 1330, 1320]
         *         }
         *     ]
         * }
         */
        return root;
    }
}
