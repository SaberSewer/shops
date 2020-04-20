package com.hhxy.shops.dao;

import com.hhxy.shops.po.Cart;
import com.hhxy.shops.po.Commodity;

import java.util.List;

public interface CartDao {
    int deleteByPrimaryKey(Long id);

    int insert(Cart record);

    int insertSelective(Cart record);

    Cart selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Cart record);

    int updateByPrimaryKey(Cart record);

    Cart selectByUidAndCid(Cart cart);
}