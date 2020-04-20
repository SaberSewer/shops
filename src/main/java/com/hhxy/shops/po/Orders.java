package com.hhxy.shops.po;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import lombok.Data;

/**
 * orders
 * @author 
 */
@Data
public class Orders implements Serializable {
    private Long id;

    /**
     * 买家ID
     */
    private Long uid;

    private Long cid;

    /**
     * 送货地址
     */
    private String address;

    /**
     * 价格
     */
    private BigDecimal prices;

    /**
     * 订单时间
     */
    private Date createtime;

    /**
     * 1 已保存 2已付款 3已发货 4已收货 5已退款
     */
    private Long status;

    /**
     * 留言备注
     */
    private String description;
    /**
     * 收货人
     */
    private String name;
    /**
     * 手机号
     */
    private String phone;

    /**
     * 子查询用
     */
    private List<OrdersSub> ordersSubList;

    private static final long serialVersionUID = 1L;
}