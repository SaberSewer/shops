package com.hhxy.shops.po;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * rep
 * @author 
 */
@Data
public class Rep implements Serializable {
    private Long id;

    /**
     * 用户ID
     */
    private Long uid;

    /**
     * 商品ID
     */
    private Long cid;

    /**
     * 评价内容
     */
    private String content;

    /**
     * 好评度
     */
    private Long level;

    /**
     * 评价时间
     */
    private Date createtimme;

    private String uname;

    private static final long serialVersionUID = 1L;
}