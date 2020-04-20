package com.hhxy.shops.setting;

import javax.websocket.Session;
import java.util.HashMap;

public class SystemSetting {
    public static final String UPLOAD_PATH = "/upload/";

    /**
     * 分页大小默认10页
     */
    public static final Long PAGE_SIZE = 10L;

    /**
     * WEBSocket
     */

    public static HashMap<String, Session> online = new HashMap<>();
}
