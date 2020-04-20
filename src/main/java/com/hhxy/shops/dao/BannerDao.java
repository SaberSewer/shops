package com.hhxy.shops.dao;


import com.hhxy.shops.po.Banner;

import java.util.List;

public interface BannerDao {
    int deleteByPrimaryKey(Long id);

    int insert(Banner record);

    int insertSelective(Banner record);

    Banner selectByPrimaryKey(Long id);

    List<Banner> selectAllBannerList(Long page, Long limit);

    Long selectAllBannerCount();

    List<Banner> selectIndexBannerList();

    int updateByPrimaryKeySelective(Banner record);

    int updateByPrimaryKey(Banner record);
}