package com.hhxy.shops.utils;

import java.util.Random;

public class RollUtils {
    //获取随机数
    public static Long getRandomNumber(Long length){
        Random random = new Random();
        return (long) random.nextInt(Integer.parseInt((String.valueOf(length))));
    }
}
