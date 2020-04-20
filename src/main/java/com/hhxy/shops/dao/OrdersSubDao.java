package com.hhxy.shops.dao;

import com.hhxy.shops.po.OrdersSub;

public interface OrdersSubDao {
    int deleteByPrimaryKey(Long id);

    int insert(OrdersSub record);

    int insertSelective(OrdersSub record);

    OrdersSub selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(OrdersSub record);

    int updateByPrimaryKey(OrdersSub record);
}