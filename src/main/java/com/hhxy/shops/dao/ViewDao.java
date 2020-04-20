package com.hhxy.shops.dao;

import java.util.HashMap;
import java.util.List;

public interface ViewDao {
    List<HashMap> selectCartViewByUid(Long id);

    List<HashMap> selectDisCountByUserIdList(Long id, Long page, Long limit);

    //销量
    List<HashMap> selectSelfCount(Long userId);
}
