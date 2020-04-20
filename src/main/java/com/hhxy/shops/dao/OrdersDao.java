package com.hhxy.shops.dao;

import com.hhxy.shops.po.Orders;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OrdersDao {
    int deleteByPrimaryKey(Long id);

    int insert(Orders record);

    int insertSelective(Orders record);

    Orders selectByPrimaryKey(Long id);

    List<Orders> selectOrdersList(@Param("orders") Orders orders, @Param("page") Long page, @Param("limit") Long limit);

    Long selectOrderCount(Orders orders);

    List<Orders> selectSelfOrdersList(@Param("uid")Long uid, @Param("page") Long page, @Param("limit") Long limit);

    Long selectSelfOrderCount(Long uid);

    int updateByPrimaryKeySelective(Orders record);

    int updateByPrimaryKey(Orders record);
}