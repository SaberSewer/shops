package com.hhxy.shops.service;

import com.hhxy.shops.po.Commodity;

import java.util.List;

public interface PublicService {
    List<Commodity> getCommodityList(Commodity commodity, Long page, Long limit);
}
