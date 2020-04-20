package com.hhxy.shops;

import com.hhxy.shops.dao.AddressDao;
import com.hhxy.shops.dao.CategoryDao;
import com.hhxy.shops.dao.CommodityDao;
import com.hhxy.shops.po.Category;
import com.hhxy.shops.po.Commodity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class ShopsApplicationTests {
    @Autowired
    private AddressDao addressDao;
    @Test
    void contextLoads() {
        System.out.println(addressDao.selectAllAddressByUserId(8L, 0L, 10L));
    }

}
