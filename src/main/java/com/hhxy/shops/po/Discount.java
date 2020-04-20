package com.hhxy.shops.po;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * discount
 * @author 
 */
@Data
public class Discount implements Serializable {
    private Long id;

    /**
     * 团购价格
     */
    private BigDecimal price;

    /**
     * 开始
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dtstart;

    /**
     * 结束
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dtend;

    /**
     * 商品ID
     */
    private Long cid;

    private Long uid;

    private static final long serialVersionUID = 1L;
}