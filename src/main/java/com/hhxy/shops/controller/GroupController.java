package com.hhxy.shops.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping(value = "group/")
public class GroupController {
    @RequestMapping(value = "{action}")
    public String toView(@PathVariable("action")String action) throws Exception {
        switch (action){
            case "gList":{

            }
            default: throw new Exception("非法请求");
        }
    }
}
