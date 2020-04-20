package com.hhxy.shops.dao;

import com.hhxy.shops.po.Commodity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CommodityDao {
    int deleteByPrimaryKey(Long id);

    int insert(Commodity record);

    int insertSelective(Commodity record);

    Commodity selectByPrimaryKey(Long id);

    List<Commodity> selectCommodityList(@Param("commodity")Commodity commodity, @Param("page") Long page, @Param("limit") Long limit);

    List<Commodity> selectByUserId(Long id);

    Long selectCommodityCount(Commodity commodity);

    int updateByPrimaryKeySelective(Commodity record);

    int updateByPrimaryKey(Commodity record);

    List<Commodity> selectByCartId(Long[] id);
}