package com.hhxy.shops.po;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * store
 * @author 
 */
@Data
public class Store implements Serializable {
    private Long id;

    /**
     * 所属的卖家ID
     */
    private Long userid;

    /**
     * 店铺名
     */
    private String name;

    /**
     * 1个体 2公司集团
     */
    private Long type;

    /**
     * 统一社会信用代码
     */
    private String carid;

    /**
     * 法定代表人
     */
    private String person;

    /**
     * 证件有效期开始时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date start;

    /**
     * 证件有效期结束时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date end;

    /**
     * 店铺注册时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date register;

    /**
     * 经营范围
     */
    private String range;

    /**
     * 核审状态 -1 未核审 0拒绝 1通过
     */
    private Long auditstatus;

    /**
     * 所在地
     */
    private String address;

    private static final long serialVersionUID = 1L;
}