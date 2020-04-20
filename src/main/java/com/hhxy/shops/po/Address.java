package com.hhxy.shops.po;

import java.io.Serializable;
import lombok.Data;

/**
 * address
 * @author 
 */
@Data
public class Address implements Serializable {
    private Long id;

    /**
     * 用户ID
     */
    private Long uid;

    /**
     * 地址
     */
    private String address;

    /**
     * 联系方式
     */
    private String phone;
    /**
     * 收件人
     */
    private String name;

    private static final long serialVersionUID = 1L;
}