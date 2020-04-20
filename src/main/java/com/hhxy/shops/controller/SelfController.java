package com.hhxy.shops.controller;

import com.hhxy.shops.dao.CommodityDao;
import com.hhxy.shops.dao.DiscountDao;
import com.hhxy.shops.dao.StoreDao;
import com.hhxy.shops.dao.UserDao;
import com.hhxy.shops.po.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.Date;

@Controller
@RequestMapping(value = "/self/")
public class SelfController {
    @Autowired
    private UserDao userDao;
    @Autowired
    private StoreDao storeDao;
    @Autowired
    private CommodityDao commodityDao;
    @Autowired
    private DiscountDao discountDao;

    @RequestMapping(value = "{action}")
    public String toView(@PathVariable("action") String action, Model model, HttpSession session) throws Exception {
        User user = (User) session.getAttribute("user");
        switch (action) {
            case "store": {
                model.addAttribute("store", storeDao.selectByUserId(user.getId()));
                return "self/store-info";
            }
            case "order": {
                return "self/order-list";
            }
            case "commodity": {
                return "self/commodity-list";
            }
            case "addCommodity": {
                Store store = storeDao.selectByUserId(user.getId());
                if (store == null || store.getAuditstatus() != 1) {
                    model.addAttribute("message", "未通过核审，无法上新");
                    return "message";
                }
                return "self/commodity-add";
            }
            case "register": {
                return "self/register";
            }
            case "group": {
                return "self/group-list";
            }
            case "welcome":{
                model.addAttribute("version", System.getProperty("java.version"));
                model.addAttribute("os", System.getProperty("os.name"));
                return "self/welcome";
            }
            default:
                throw new Exception("非法请求");
        }
    }

    @RequestMapping(value = "store/{action}")
    public String toView(Store store, @PathVariable("action") String action, Model model, HttpSession session) throws Exception {
        User user = (User) session.getAttribute("user");
        switch (action) {
            case "save": {
                if (store.getId() == null) {
                    store.setUserid(user.getId());
                    store.setAuditstatus(-1L);
                    store.setRegister(new Date());
                    storeDao.insertSelective(store);
                } else {
                    store.setAuditstatus(-1L);
                    storeDao.updateByPrimaryKeySelective(store);
                }
                return toView("store", model, session);
            }
            default:
                throw new Exception("非法请求");
        }
    }

    @RequestMapping(value = "commodity/{action}")
    public String store(Commodity commodity, @PathVariable("action") String action, Model model, HttpSession session) throws Exception {
        User user = (User) session.getAttribute("user");
        switch (action) {
            case "save": {
                if (commodity.getId() == null) {
                    commodity.setSid(storeDao.selectByUserId(user.getId()).getId());
                    commodity.setRegisterdate(new Date());
                    commodityDao.insertSelective(commodity);
                } else {
                    commodityDao.updateByPrimaryKeySelective(commodity);
                }
                return "self/commodity-list";
            }
            default:
                throw new Exception("非法请求");
        }
    }

    @RequestMapping(value = "login")
    public String login(User user, Model model, HttpSession session) {
        user.setLevel(3L);
        User u = userDao.selectByloginNameOrEmail(user);
        if (u != null) {
            session.setAttribute("user", u);
            return "self/index";
        }
        return "self/login";
    }

    @RequestMapping(value = "user/save")
    public String register(User user, Model model, HttpSession session) {
        if (user.getId() == null) {
            user.setLevel(3L);
            userDao.insertSelective(user);
        }
        return "self/login";
    }

    @RequestMapping(value = "group/{action}")
    public String banner(Discount discount, Model model, HttpSession session, @PathVariable("action") String action) throws Exception {
        User user = (User) session.getAttribute("user");
        switch (action) {
            case "save": {
                if (discountDao.selectByCid(discount) > 0) {
                    model.addAttribute("message", "同一时间段一个商品只能设置一个优惠活动");
                    return "message";
                }
                discount.setUid(user.getId());
                discountDao.insertSelective(discount);
                return "/self/group-list";
            }
            case "add": {
                model.addAttribute("commodityList", commodityDao.selectByUserId(user.getId()));
                return "/self/group-add";
            }
            case "del": {
                discountDao.deleteByPrimaryKey(discount.getId());
                return "/self/group-list";
            }
            default:
                throw new Exception("非法请求");
        }
    }


}
