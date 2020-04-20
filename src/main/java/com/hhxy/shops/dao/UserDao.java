package com.hhxy.shops.dao;

import com.hhxy.shops.po.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserDao {
    int deleteByPrimaryKey(Long id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Long id);

    User selectByloginNameOrEmail(User user);

    List<User> selectUserList(@Param("user") User user, @Param("page") Long page, @Param("limit") Long limit);

    List<User> selectAllUserList();

    Long  selectUserCount(User user);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);
}