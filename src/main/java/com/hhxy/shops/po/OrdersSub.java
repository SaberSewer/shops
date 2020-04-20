package com.hhxy.shops.po;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.Data;

/**
 * orders_sub
 * @author 
 */
@Data
public class OrdersSub implements Serializable {
    private Long id;

    /**
     * 订单表主表
     */
    private Long oid;

    /**
     * 商品ID
     */
    private Long cid;

    /**
     * 价格
     */
    private BigDecimal prices;

    /**
     * 数量
     */
    private Long num;

    private static final long serialVersionUID = 1L;
}