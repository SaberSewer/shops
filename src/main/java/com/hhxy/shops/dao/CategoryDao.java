package com.hhxy.shops.dao;

import com.hhxy.shops.po.Category;

import java.util.List;

public interface CategoryDao {
    int deleteByPrimaryKey(Long id);

    int insert(Category record);

    int insertSelective(Category record);

    Category selectByPrimaryKey(Long id);

    List<Category> selectAllList(Category category);

    List<Category> selectIndexList();

    int updateByPrimaryKeySelective(Category record);

    int updateByPrimaryKey(Category record);
}