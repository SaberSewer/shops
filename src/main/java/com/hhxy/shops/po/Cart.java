package com.hhxy.shops.po;

import java.io.Serializable;
import lombok.Data;

/**
 * cart
 * @author 
 */
@Data
public class Cart implements Serializable {
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
     * 数量
     */
    private Long num;

    private static final long serialVersionUID = 1L;
}