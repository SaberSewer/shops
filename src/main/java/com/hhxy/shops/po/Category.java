package com.hhxy.shops.po;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

/**
 * category
 *
 * @author
 */
@Data
public class Category implements Serializable {
    private Long id;

    private Long pid;

    /**
     * 名字
     */
    private String name;

    /**
     * 排序
     */
    private Long sort;

    /**
     * 首页用的子查询
     */
    List<Category> children;

    private static final long serialVersionUID = 1L;
}