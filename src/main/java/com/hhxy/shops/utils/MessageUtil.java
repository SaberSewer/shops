package com.hhxy.shops.utils;

import org.springframework.ui.Model;

public class MessageUtil {
    /**
     * 向前端发送消息配合Message.jsp使用
     */
    public static String message(String message, String url, Model model){
        model.addAttribute("message", message);
        model.addAttribute("url", url);
        return "message";
    }
}
