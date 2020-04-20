package com.hhxy.shops.dao;

import java.util.HashMap;
import java.util.List;

public interface ViewDao {
    List<HashMap> selectCartViewByUid(Long id);
}
