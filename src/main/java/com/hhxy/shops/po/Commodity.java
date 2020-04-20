package com.hhxy.shops.po;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;

/**
 * commodity
 * @author 
 */
@Data
public class Commodity implements Serializable {
    private Long id;

    /**
     * 店铺ID
     */
    private Long sid;

    /**
     * 商品名
     */
    private String name;

    /**
     * 上架时间
     */
    private Date registerdate;

    /**
     * 商品描述
     */
    private String description;

    /**
     * 库存
     */
    private Long stock;

    /**
     * 状态 1上架 2下架
     */
    private Long status;

    /**
     * 价格
     */
    private BigDecimal price;

    /**
     * 是否包邮 1是 2否
     */
    private String isfree;

    /**
     * 所属类目 子类
     */
    private Long cid;

    /**
     * 商品预览图
     */
    private String img;

    private static final long serialVersionUID = 1L;
}