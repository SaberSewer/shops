package com.hhxy.shops.service.impl;

import com.hhxy.shops.dao.CommodityDao;
import com.hhxy.shops.po.Commodity;
import com.hhxy.shops.service.PublicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("publicService")
public class PublicServiceImpl implements PublicService {
    @Autowired
    private CommodityDao commodityDao;

    @Override
    public List<Commodity> getCommodityList(Commodity commodity, Long page, Long limit) {
        return commodityDao.selectCommodityList(commodity, (page - 1) * limit, limit);
    }
}
