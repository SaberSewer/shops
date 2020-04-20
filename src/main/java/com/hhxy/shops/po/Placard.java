package com.hhxy.shops.po;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * placard
 * @author 
 */
@Data
public class Placard implements Serializable {
    private Long id;

    private String title;

    private String content;

    private Date creattime;

    private static final long serialVersionUID = 1L;
}