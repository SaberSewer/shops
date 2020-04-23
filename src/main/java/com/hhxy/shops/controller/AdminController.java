package com.hhxy.shops.controller;

import com.hhxy.shops.dao.*;
import com.hhxy.shops.po.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.Date;

@Controller
@RequestMapping(value = "admin/")
public class AdminController {
    @Autowired
    private UserDao userDao;
    @Autowired
    private StoreDao storeDao;
    @Autowired
    private CategoryDao categoryDao;
    @Autowired
    private CommodityDao commodityDao;
    @Autowired
    private BannerDao bannerDao;
    @Autowired
    private PlacardDao placardDao;
    @Autowired
    private OrdersDao ordersDao;

    @RequestMapping("{action}")
    public String toView(@PathVariable("action") String action, Model model, HttpSession session) throws Exception {
        switch (action) {
            case "store": {
                return "admin/store-list";
            }
            case "category": {
                return "admin/category-list";
            }
            case "commodity": {
                return "admin/commodity-list";
            }
            case "banner": {
                return "admin/banner-list";
            }
            case "placard": {
                return "admin/placard-list";
            }
            case "employee": {
                return "admin/employee-list";
            }
            case "welcome":{
                model.addAttribute("version", System.getProperty("java.version"));
                model.addAttribute("os", System.getProperty("os.name"));
                return "admin/welcome";
            }
            default:
                throw new Exception("非法请求");
        }
    }

    @RequestMapping(value = "store/{action}")
    public String store(Store store, @PathVariable("action") String action, Model model, HttpSession session) throws Exception {
        switch (action) {
            case "save": {
                if (store.getId() == null) {
                    storeDao.insertSelective(store);
                } else {
                    storeDao.updateByPrimaryKeySelective(store);
                }
                return "admin/store-list";
            }
            default:
                throw new Exception("非法请求");
        }
    }

    @RequestMapping(value = "commodity/{action}")
    public String commodity(Commodity commodity, @PathVariable("action") String action, Model model, HttpSession session) throws Exception {
        switch (action) {
            case "save": {
                if (commodity.getId() == null) {
                    commodityDao.insertSelective(commodity);
                } else {
                    commodityDao.updateByPrimaryKeySelective(commodity);
                }
                return "admin/store-list";
            }
            default:
                throw new Exception("非法请求");
        }
    }

    @RequestMapping(value = "category/{action}")
    public String category(Category category, @PathVariable("action") String action, Model model, HttpSession session) throws Exception {
        switch (action) {
            case "add": {//添加子集
                model.addAttribute("category", category.getPid());
                return "admin/category-add";
            }
            default:
                throw new Exception("非法请求");
        }
    }

    @RequestMapping(value = "login")
    public String login(User user, Model model, HttpSession session) {
        user.setLevel(1L);
        User u = userDao.selectByloginNameOrEmail(user);
        if (u != null) {
            session.setAttribute("user", u);
            return "admin/index";
        }
        return "admin/login";
    }

    @RequestMapping(value = "banner/{action}")
    public String banner(Banner banner, Model model, HttpSession session, @PathVariable("action") String action) throws Exception {
        switch (action) {
            case "save": {
                bannerDao.insertSelective(banner);
                return "/admin/banner-list";
            }
            case "add": {
                return "/admin/banner-add";
            }
            case "del": {
                bannerDao.deleteByPrimaryKey(banner.getId());
                return "/admin/banner-list";
            }
            default:
                throw new Exception("非法请求");
        }
    }

    @RequestMapping(value = "placard/{action}")
    public String placard(Placard placard, Model model, HttpSession session, @PathVariable("action") String action) throws Exception {
        switch (action) {
            case "save": {
                if (placard.getId() == null) {
                    placard.setCreattime(new Date());
                    placardDao.insertSelective(placard);
                } else {
                    placardDao.updateByPrimaryKey(placard);
                }
                return "/admin/placard-list";
            }
            case "add": {
                return "/admin/placard-add";
            }
            case "modify": {
                model.addAttribute("placard", placardDao.selectByPrimaryKey(placard.getId()));
                return "/admin/placard-add";
            }
            case "del": {
                placardDao.deleteByPrimaryKey(placard.getId());
                return "/admin/placard-list";
            }
            default:
                throw new Exception("非法请求");
        }
    }

    @RequestMapping(value = "employee/{action}")
    public String employee(User user, Model model, HttpSession httpSession, @PathVariable("action") String action) throws Exception {
        switch (action) {
            case "add": {
                return "admin/employee-add";
            }
            case "save": {
                userDao.updateByPrimaryKeySelective(user);
                return "admin/employee-list";

            }
            default:
                throw new Exception("非法请求");
        }
    }

    @RequestMapping(value = "orders/save")
    public String orders(Orders orders, Model model, HttpSession session){
        User user = (User) session.getAttribute("user");
        ordersDao.updateByPrimaryKeySelective(orders);
        if(user.getLevel() == 2){
            return "home";
        }
        return "admin/commodity-list";
    }
}
