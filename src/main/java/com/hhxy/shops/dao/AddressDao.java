package com.hhxy.shops.dao;

import com.hhxy.shops.po.Address;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AddressDao {
    int deleteByPrimaryKey(Long id);

    int insert(Address record);

    int insertSelective(Address record);

    List<Address> getAllAddressByUserId(Long id);

    List<Address> selectAllAddressByUserId(@Param("id") Long id, @Param("page") Long page, @Param("limit") Long limit);

    Long selectAllAddressCountByUserId(Long id);

    Address selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Address record);

    int updateByPrimaryKey(Address record);
}