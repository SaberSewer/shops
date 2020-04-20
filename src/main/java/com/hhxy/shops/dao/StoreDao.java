package com.hhxy.shops.dao;

import com.hhxy.shops.po.Store;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface StoreDao {
    int deleteByPrimaryKey(Long id);

    int insert(Store record);

    int insertSelective(Store record);

    Store selectByPrimaryKey(Long id);


    List<Store> selectList(@Param("store")Store store,@Param("page") Long page, @Param("limit")Long limit);

    Long selectCount(Store store);

    int updateByPrimaryKeySelective(Store record);

    int updateByPrimaryKey(Store record);

    Store selectByUserId(Long id);
}