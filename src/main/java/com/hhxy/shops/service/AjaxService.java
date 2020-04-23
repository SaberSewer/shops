package com.hhxy.shops.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hhxy.shops.po.*;

public interface AjaxService {
    JSONObject getCategoryList(Category category);

    JSONObject getStoreList(Store store, Long page, Long limit);

    JSONObject getCommodityList(Commodity commodity, Long page, Long limit);

    JSONObject getOrderList(Orders orders, Long page, Long limit);

    JSONObject getAddressList(Long id, Long page, Long limit);

    JSONObject getBannerList(Long page, Long limit);

    JSONObject getPlacardList(Long start, Long limit);

    JSONObject getUserList(User user, Long page, Long limit);

    JSONObject getGroupList(Long id, Long start, Long limit);

    JSONObject getSelfCount(Long id);

    JSONObject getHotSelfCount(Long id);

}
