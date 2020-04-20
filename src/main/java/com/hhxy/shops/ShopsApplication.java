package com.hhxy.shops;

import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.annotation.MapperScans;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(value = "com.hhxy.shops.dao")
public class ShopsApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShopsApplication.class, args);
    }

}
