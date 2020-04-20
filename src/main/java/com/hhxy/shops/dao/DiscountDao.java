package com.hhxy.shops.dao;

import com.hhxy.shops.po.Discount;

import java.util.List;

public interface DiscountDao {
    int deleteByPrimaryKey(Long id);

    int insert(Discount record);

    int insertSelective(Discount record);

    Discount selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Discount record);

    int updateByPrimaryKey(Discount record);

    Long selectDisCountByUserIdCount(Long id);

    Long selectByCid(Discount discount);

    Discount selectDiscountByCid(Long cid);
}