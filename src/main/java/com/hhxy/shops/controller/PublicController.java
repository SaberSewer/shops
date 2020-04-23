package com.hhxy.shops.controller;

import com.hhxy.shops.dao.*;
import com.hhxy.shops.po.*;
import com.hhxy.shops.setting.SystemSetting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class PublicController {
    @Autowired
    private CategoryDao categoryDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private CommodityDao commodityDao;
    @Autowired
    private AddressDao addressDao;
    @Autowired
    private OrdersDao ordersDao;
    @Autowired
    private OrdersSubDao ordersSubDao;
    @Autowired
    private RepDao repDao;
    @Autowired
    private CartDao cartDao;
    @Autowired
    private ViewDao viewDao;
    @Autowired
    private BannerDao bannerDao;
    @Autowired
    private PlacardDao placardDao;
    @Autowired
    private DiscountDao discountDao;
    @RequestMapping(value = "/")
    public String index(Model model, HttpSession session) {
        model.addAttribute("categoryList", categoryDao.selectIndexList());
        model.addAttribute("bannerList", bannerDao.selectIndexBannerList());
        model.addAttribute("placardList", placardDao.selectIndexList());
        return "index";
    }

    @RequestMapping(value = "/login")
    public String login(User user, Model model, HttpSession session) {
        user.setLevel(2L);
        User u = userDao.selectByloginNameOrEmail(user);
        if (u != null) {
            session.setAttribute("user", u);
            return index(model, session);
        }
        return "login";
    }


    @RequestMapping(value = "/register")
    public String register(User user, Model model, HttpSession session) {
        user.setLevel(2L);
        user.setRegistedate(new Date());
        userDao.insertSelective(user);
        return index(model, session);
    }

    @RequestMapping(value = "/toRegister")
    public String register() {
        return "register";
    }

    @RequestMapping(value = "/home")
    public String home(Model model, HttpSession session) {
        if(session.getAttribute("user") == null){
            return login(new User(), model, session);
        }
        return "home";
    }

    @RequestMapping(value = "/order")
    public String order(Model model, HttpSession session) {
        return "order";
    }

    @RequestMapping(value = "search")
    public String search(Commodity commodity, Model model, HttpSession session, @RequestParam(required = false) Long page) {
        commodity.setStatus(1L);//买家查询只查询上架的东西
        if (page == null) {
            page = 1L;
        }
        List<Commodity> list = commodityDao.selectCommodityList(commodity, (page - 1) * SystemSetting.PAGE_SIZE, SystemSetting.PAGE_SIZE);
        Long count = commodityDao.selectCommodityCount(commodity);
        Long size = count % SystemSetting.PAGE_SIZE == 0 ? count / SystemSetting.PAGE_SIZE : (count / SystemSetting.PAGE_SIZE + 1);
        model.addAttribute("commodityList", list);
        model.addAttribute("now", page);
        model.addAttribute("size", size);
        return "search";
    }

    @RequestMapping(value = "/item")
    public String item(Long id, Model model, HttpSession session) {
        Commodity c = commodityDao.selectByPrimaryKey(id);
        model.addAttribute("commodity", c);
        Commodity commodity = new Commodity();
        commodity.setSid(commodity.getSid());
        commodity.setStatus(1L);
        model.addAttribute("commodityList", commodityDao.selectCommodityList(commodity, 0L, 5L));
        model.addAttribute("repList", repDao.selectByCid(id));
        model.addAttribute("discount", discountDao.selectDiscountByCid(id));
        return "item";
    }

    @RequestMapping(value = "buy")
    public String buy(OrdersSub ordersSub, Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null || user.getLevel() != 2) {
            return login(new User(), model, session);
        }
        Commodity c = commodityDao.selectByPrimaryKey(ordersSub.getCid());
        model.addAttribute("addressList", addressDao.getAllAddressByUserId(user.getId()));
        model.addAttribute("commodity", c);
        model.addAttribute("orders", ordersSub);
        model.addAttribute("isDiscount", 1);
        return "order";
    }

    @RequestMapping(value = "/submitOrder")
    public String Orders(OrdersSub ordersSub, String description, String name, String phone, Long aid, Model model, HttpSession session, Long isDiscount) {
        User user = (User) session.getAttribute("user");
        Orders orders = new Orders();
        Address address = addressDao.selectByPrimaryKey(aid);
        orders.setAddress(address.getName());
        orders.setPhone(address.getPhone());
        orders.setStatus(2L);
        Discount discount = discountDao.selectDiscountByCid(ordersSub.getCid());
        if(isDiscount == 2){
            orders.setPrices(discount.getPrice().multiply(BigDecimal.valueOf(ordersSub.getNum())));
            orders.setIsDiscount(2L);
        }else {
            orders.setPrices(ordersSub.getPrices().multiply(BigDecimal.valueOf(ordersSub.getNum())));
            orders.setIsDiscount(2L);
        }
        orders.setDescription(description);
        orders.setCreatetime(new Date());
        orders.setUid(user.getId());
        orders.setName(name);
        orders.setPhone(phone);
        ordersDao.insertSelective(orders);
        ordersSub.setOid(orders.getId());
        ordersSubDao.insertSelective(ordersSub);
        model.addAttribute("orders", orders);

        return "paysuccess";
    }

    @RequestMapping(value = "myAddress")
    public String myAddress(HttpSession session, Model model) {
        return "addressList";
    }

    @RequestMapping(value = "/saveAddress")
    public String saveAddress(Address address, Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (address.getId() == null) {
            address.setUid(user.getId());
            addressDao.insertSelective(address);
        } else {
            addressDao.updateByPrimaryKeySelective(address);
        }
        return "addressList";
    }

    @RequestMapping(value = "addMyAddress")
    public String addMyAddress() {
        return "address-add";
    }

    @RequestMapping(value = "delMyAddress")
    public String delMyAddress(Long id, Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        addressDao.deleteByPrimaryKey(id);
        return "addressList";
    }

    @RequestMapping(value = "logOut")
    public String logOut(HttpSession session, Model model) {
        session.invalidate();
        return index(model, session);
    }

    @RequestMapping(value = "saveOrder")
    public String saveOrder(Orders orders, Model model, HttpSession session) {
        ordersDao.updateByPrimaryKeySelective(orders);
        return "home";
    }

    @RequestMapping(value = "orders")
    public String order(Orders orders, Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        model.addAttribute("order", ordersDao.selectByPrimaryKey(orders.getId()));
        return "order-info";
    }

    @RequestMapping(value = "rep/{action}")
    public String rep(Rep rep, @PathVariable("action") String action, Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        switch (action) {
            case "save": {
                if (rep.getId() != null) {
                    rep.setCreatetimme(new Date());
                    repDao.updateByPrimaryKeySelective(rep);
                } else {
                    rep.setUid(user.getId());
                    repDao.insertSelective(rep);
                }
                return "home";
            }
            case "toAdd": {
                model.addAttribute("commodity", commodityDao.selectByPrimaryKey(rep.getCid()));
                return "order-rep";
            }
            default:
                return "home";
        }
    }

    @RequestMapping(value = "cart/{action}")
    public String cart(Cart cart, @PathVariable("action") String action, Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "login";
        }
        switch (action) {
            case "save": {
                cart.setUid(user.getId());
                Cart c = cartDao.selectByUidAndCid(cart);
                if (cart.getNum() == null) {
                    cart.setNum(1L);
                }
                if (c == null) {
                    cartDao.insertSelective(cart);
                } else {
                    c.setNum(c.getNum() + cart.getNum());
                    cartDao.updateByPrimaryKeySelective(c);
                }
                return "success-cart";
            }
            case "myCart": {
                model.addAttribute("cartList", viewDao.selectCartViewByUid(user.getId()));
                return "cart";
            }
            case "del": {
                cartDao.deleteByPrimaryKey(cart.getId());
                model.addAttribute("cartList", viewDao.selectCartViewByUid(user.getId()));
                return "cart";
            }
            default:
                return "cart";
        }
    }

    @RequestMapping(value = "cartBuy")
    public String cartBuy(Long[] id, Long[] num, HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null || user.getLevel() != 2) {
            return login(new User(), model, session);
        }
        List<Commodity> c = commodityDao.selectByCartId(id);
        List<Cart> cartList = new ArrayList<>();
        for (int i = 0; i < id.length; i++) {
            cartList.add(cartDao.selectByPrimaryKey(id[i]));
        }
        model.addAttribute("addressList", addressDao.getAllAddressByUserId(user.getId()));
        model.addAttribute("commodityList", c);
        model.addAttribute("orders", num);
        final BigDecimal[] sunPrices = {BigDecimal.valueOf(0L)};
        cartList.stream().forEach(cart -> {
            Commodity commodity = commodityDao.selectByPrimaryKey(cart.getCid());
            sunPrices[0] = sunPrices[0].add(commodity.getPrice().multiply(BigDecimal.valueOf(cart.getNum())));
        });
        model.addAttribute("sumPrice", sunPrices[0].doubleValue());
        return "order-cart";
    }

    @RequestMapping(value = "submitCartOrder")
    @Transactional
    public String submitCartOrder(Long[] cid,Long num[], String description, String name, String phone, Long aid, Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        Orders orders = new Orders();
        Address address = addressDao.selectByPrimaryKey(aid);
        orders.setAddress(address.getName());
        orders.setPhone(address.getPhone());
        orders.setStatus(2L);
        BigDecimal sum = new BigDecimal(0L);
        for (int i = 0; i<cid.length;i++){
        }
        List<OrdersSub> ordersSubList = new ArrayList<>();
        for (int i = 0; i < cid.length; i++){
            Cart cart = new Cart();
            cart.setUid(user.getId());
            cart.setCid(cid[i]);
            Cart c = cartDao.selectByUidAndCid(cart);
            Commodity commodity = commodityDao.selectByPrimaryKey(c.getCid());
            sum = sum.add(commodity.getPrice().multiply(BigDecimal.valueOf(num[i])));
            OrdersSub sub = new OrdersSub();
            sub.setCid(c.getCid());
            sub.setNum(c.getNum());
            sub.setPrices(commodity.getPrice());
            ordersSubList.add(sub);
            cartDao.deleteByPrimaryKey(c.getId());
        }
        orders.setPrices(sum);
        orders.setUid(user.getId());
        orders.setCreatetime(new Date());
        ordersDao.insertSelective(orders);
        ordersSubList.forEach(ordersSub -> {
            ordersSub.setOid(orders.getId());
            ordersSubDao.insertSelective(ordersSub);
        });
        model.addAttribute("orders", orders);
        return "paysuccess";
    }

    @RequestMapping(value = "pwd/{action}")
    public String pwd(User user, HttpSession session, Model model, @PathVariable("action")String action){
        User u = (User) session.getAttribute("user");
        switch (action){
            case "modify":{
                return "modify";
            }
            case "save":{
                user.setId(u.getId());
                userDao.updateByPrimaryKeySelective(user);
            }
        }
        return logOut(session, model);
    }

    @RequestMapping(value = "welcome")
    public String welcome(Model model){
        model.addAttribute("version", System.getProperty("java.version"));
        model.addAttribute("os", System.getProperty("os.name"));
        return "welcome";
    }

    @RequestMapping(value = "placard")
    public String welcome(Long id, Model model){
        model.addAttribute("placard", placardDao.selectByPrimaryKey(id));
        return "placard";
    }

    @RequestMapping(value = "groupBuy")
    public String groupBuy(Long cid, Long num, Model model, HttpSession session){
        User user = (User) session.getAttribute("user");
        if (user == null || user.getLevel() != 2) {
            return login(new User(), model, session);
        }
        Commodity c = commodityDao.selectByPrimaryKey(cid);
        c.setPrice(discountDao.selectDiscountByCid(cid).getPrice());
        model.addAttribute("addressList", addressDao.getAllAddressByUserId(user.getId()));
        model.addAttribute("commodity", c);
        OrdersSub ordersSub = new OrdersSub();
        ordersSub.setNum(num);
        ordersSub.setCid(cid);
        model.addAttribute("orders", ordersSub);
        model.addAttribute("isDiscount", 2);
        return "order";
    }

    @RequestMapping(value = "gBuyList")
    public String gBuyList(Model model, HttpSession session, @RequestParam(required = false) Long page){
        if (page == null) {
            page = 1L;
        }
        List<Commodity> list = commodityDao.selectCommodityIsGroupList((page - 1) * SystemSetting.PAGE_SIZE, SystemSetting.PAGE_SIZE);
        Long count = commodityDao.selectCommodityIsGroupCount();
        Long size = count % SystemSetting.PAGE_SIZE == 0 ? count / SystemSetting.PAGE_SIZE : (count / SystemSetting.PAGE_SIZE + 1);
        model.addAttribute("commodityList", list);
        model.addAttribute("now", page);
        model.addAttribute("size", size);
        return "search";
    }
}
