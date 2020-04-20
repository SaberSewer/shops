package com.hhxy.shops.dao;

import com.hhxy.shops.po.Rep;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RepDao {
    int deleteByPrimaryKey(Long id);

    int insert(Rep record);

    int insertSelective(Rep record);

    Rep selectByPrimaryKey(Long id);

    List<Rep> selectByCid(Long cid);

    List<Rep> selectByUid(@Param("uid") Long uid, @Param("page")Long page, @Param("limit") Long limit);

    Long selectCount(Long uid);

    int updateByPrimaryKeySelective(Rep record);

    int updateByPrimaryKey(Rep record);
}