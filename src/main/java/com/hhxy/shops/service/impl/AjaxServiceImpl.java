package com.hhxy.shops.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.hhxy.shops.dao.*;
import com.hhxy.shops.po.*;
import com.hhxy.shops.service.AjaxService;
import com.hhxy.shops.service.PublicService;
import com.hhxy.shops.utils.PoJoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service("ajaxService")
public class AjaxServiceImpl implements AjaxService {
    @Autowired
    private CategoryDao categoryDao;
    @Autowired
    private StoreDao storeDao;
    @Autowired
    private CommodityDao commodityDao;
    @Autowired
    private OrdersDao ordersDao;
    @Autowired
    private AddressDao addressDao;
    @Autowired
    private BannerDao bannerDao;
    @Autowired
    private PublicService publicService;

    @Override
    public JSONObject getCategoryList(Category category) {
        JSONObject json = new JSONObject();
        json.put("cateList", categoryDao.selectAllList(category));
        return json;
    }

    @Override
    public JSONObject getStoreList(Store store, Long page, Long limit) {
        List list = storeDao.selectList(store, page, limit);
        HashMap<String, Object> hash = PoJoUtil.getTableMap(list);
        hash.put("count", storeDao.selectCount(store));
        JSONObject json = new JSONObject();
        json.putAll(hash);
        return json;
    }

    @Override
    public JSONObject getCommodityList(Commodity commodity, Long page, Long limit) {
        List list = publicService.getCommodityList(commodity, page, limit);
        HashMap<String, Object> hash = PoJoUtil.getTableMap(list);
        hash.put("count", commodityDao.selectCommodityCount(commodity));
        JSONObject json = new JSONObject();
        json.putAll(hash);
        return json;
    }

    @Override
    public JSONObject getOrderList(Orders orders, Long page, Long limit) {
        List list = ordersDao.selectOrdersList(orders, (page-1)*limit, limit);
        HashMap<String, Object> hash = PoJoUtil.getTableMap(list);
        hash.put("count", ordersDao.selectOrderCount(orders));
        JSONObject json = new JSONObject();
        json.putAll(hash);
        return json;
    }

    @Override
    public JSONObject getAddressList(Long id, Long page, Long limit) {
        List list = addressDao.selectAllAddressByUserId(id, (page-1)*limit, limit);
        HashMap<String, Object> hash = PoJoUtil.getTableMap(list);
        hash.put("count", addressDao.selectAllAddressCountByUserId(id));
        JSONObject json = new JSONObject();
        json.putAll(hash);
        return json;
    }

    @Override
    public JSONObject getBannerList(Long page, Long limit) {
        List list = bannerDao.selectAllBannerList( (page - 1) * limit, limit);
        HashMap<String, Object> hash = PoJoUtil.getTableMap(list);
        hash.put("count", bannerDao.selectAllBannerCount());
        JSONObject json = new JSONObject();
        json.putAll(hash);
        return json;
    }
}
