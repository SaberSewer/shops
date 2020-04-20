package com.hhxy.shops.dao;

import com.hhxy.shops.po.Placard;

import java.util.List;

public interface PlacardDao {
    int deleteByPrimaryKey(Long id);

    int insert(Placard record);

    int insertSelective(Placard record);

    Placard selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Placard record);

    int updateByPrimaryKey(Placard record);

    List<Placard> selectAllList(Long page, Long limit);

    List<Placard> selectIndexList();

    Long selectAllCount();
}