package com.hhxy.shops.po;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * banner
 * @author 
 */
@Data
public class Banner implements Serializable {
    private Long id;

    /**
     * url
     */
    private String url;

    /**
     * 开始
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date dtstart;

    /**
     * 结束
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date dtend;

    private static final long serialVersionUID = 1L;
}