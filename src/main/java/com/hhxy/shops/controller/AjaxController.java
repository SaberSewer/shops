package com.hhxy.shops.controller;

import com.alibaba.fastjson.JSONObject;
import com.hhxy.shops.dao.CategoryDao;
import com.hhxy.shops.dao.StoreDao;
import com.hhxy.shops.po.*;
import com.hhxy.shops.service.AjaxService;
import com.hhxy.shops.utils.PoJoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@RequestMapping(value = "Ajax/", method = {RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.POST, RequestMethod.GET}, produces = "application/json;charset=utf-8")
@Controller
public class AjaxController {
    @Autowired
    private AjaxService ajaxService;
    @Autowired
    private CategoryDao categoryDao;
    @Autowired
    private StoreDao storeDao;

    /**
     * 所有的Ajax请求都通过这个控制
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "{user}/{path}/{action}")
    public String ajaxMapping(@PathVariable("user") String user, @PathVariable("path") String path, @PathVariable("action") String action, @RequestBody(required = false) Map<String, Object> map, HttpSession session) {
        Map<String, Object> param = new HashMap<>();
        //Ajax分页用
        Long start = null;
        Long limit = null;
        Category category = null;
        Commodity commodity = null;
        Store store = null;
        Orders orders = null;
        JSONObject json = null;
        User u = (User) session.getAttribute("user");
        try {
            //模块分支
            switch (path) {
                case "category": {
                    switch (action) {
                        case "list": {
                            if (map != null) {
                                category = PoJoUtil.mapToPo(map, new Category());
                            }
                            return ajaxService.getCategoryList(category).toJSONString();
                        }
                        case "save": {
                            category = PoJoUtil.mapToPo(map, new Category());
                            if (category.getId() == null) {
                                categoryDao.insertSelective(category);
                            } else {
                                categoryDao.updateByPrimaryKeySelective(category);
                            }
                            json = new JSONObject();
                            json.put("node", category);
                            return json.toJSONString();
                        }
                        case "del": {
                            category = PoJoUtil.mapToPo(map, new Category());
                            category.setPid(category.getId());
                            if (categoryDao.selectAllList(category).size() > 0) {
                                throw new Exception("该节点有子节点，请先删除对应子节点");
                            }
                            categoryDao.deleteByPrimaryKey(category.getId());
                            json = new JSONObject();
                            json.put("info", "删除成功");
                            return json.toJSONString();
                        }
                        default:
                            throw new Exception("非法请求");
                    }
                }
                case "commodity": {
                    switch (action) {
                        case "list": {
                            start = Long.parseLong(String.valueOf(map.get("page")));
                            limit = Long.parseLong(String.valueOf(map.get("limit")));
                            if (map != null) {
                                commodity = PoJoUtil.mapToPo(map, new Commodity());
                            }
                            return ajaxService.getCommodityList(commodity, start, limit).toJSONString();
                        }
                        case "myList": {
                            start = Long.parseLong(String.valueOf(map.get("page")));
                            limit = Long.parseLong(String.valueOf(map.get("limit")));
                            commodity = new Commodity();
                            commodity.setSid(storeDao.selectByUserId(u.getId()).getId());
                            return ajaxService.getCommodityList(commodity, start, limit).toJSONString();
                        }
                    }
                }
                case "address": {
                    switch (action) {
                        case "myList": {
                            start = Long.parseLong(String.valueOf(map.get("page")));
                            limit = Long.parseLong(String.valueOf(map.get("limit")));
                            return ajaxService.getAddressList(u.getId(), start, limit).toJSONString();
                        }
                        default:
                            throw new Exception("非法请求");
                    }
                }
                case "orders": {
                    switch (action) {
                        case "list": {
                            start = Long.parseLong(String.valueOf(map.get("page")));
                            limit = Long.parseLong(String.valueOf(map.get("limit")));
                            if (map != null) {
                                orders = PoJoUtil.mapToPo(map, new Orders());
                            }
                            return ajaxService.getOrderList(orders, start, limit).toJSONString();
                        }
                        case "myList": {
                            start = Long.parseLong(String.valueOf(map.get("page")));
                            limit = Long.parseLong(String.valueOf(map.get("limit")));
                            if (map != null) {
                                orders = PoJoUtil.mapToPo(map, new Orders());
                            }
                            orders.setUid(u.getId());
                            return ajaxService.getOrderList(orders, start, limit).toJSONString();
                        }
                        case "selfList": {
                            start = Long.parseLong(String.valueOf(map.get("page")));
                            limit = Long.parseLong(String.valueOf(map.get("limit")));
                            if (map != null) {
                                orders = PoJoUtil.mapToPo(map, new Orders());
                            }
                            orders.setCid(u.getId());
                            return ajaxService.getOrderList(orders, start, limit).toJSONString();
                        }
                    }
                }
                case "store": {
                    switch (action) {
                        case "list": {
                            start = Long.parseLong(String.valueOf(map.get("page")));
                            limit = Long.parseLong(String.valueOf(map.get("limit")));
                            if (map != null) {
                                store = PoJoUtil.mapToPo(map, new Store());
                            }
                            return ajaxService.getStoreList(store, (start - 1) * limit, limit).toJSONString();
                        }
                        default:
                            throw new Exception("非法请求");
                    }
                }
                case "banner": {
                    switch (action) {
                        case "list": {
                            start = Long.parseLong(String.valueOf(map.get("page")));
                            limit = Long.parseLong(String.valueOf(map.get("limit")));
                            return ajaxService.getBannerList(start, limit).toJSONString();
                        }
                    }
                }
                case "placard": {
                    switch (action) {
                        case "list": {
                            start = Long.parseLong(String.valueOf(map.get("page")));
                            limit = Long.parseLong(String.valueOf(map.get("limit")));
                            return ajaxService.getPlacardList(start, limit).toJSONString();
                        }
                    }
                }
                case "employee": {
                    switch (action) {
                        case "list": {
                            start = Long.parseLong(String.valueOf(map.get("page")));
                            limit = Long.parseLong(String.valueOf(map.get("limit")));
                            u = PoJoUtil.mapToPo(map, new User());
                            return ajaxService.getUserList(u, start, limit).toJSONString();
                        }
                        default:
                            throw new Exception("页面跟着小姨子跑了");
                    }
                }
                case "group": {
                    switch (action) {
                        case "list": {
                            start = Long.parseLong(String.valueOf(map.get("page")));
                            limit = Long.parseLong(String.valueOf(map.get("limit")));
                            return ajaxService.getGroupList(u.getId(), start, limit).toJSONString();
                        }
                        default:
                            throw new Exception("页面跟着小姨子跑了");
                    }
                }
                case "welcome": {
                    switch (action) {
                        case "self": {
                            return ajaxService.getSelfCount(null).toJSONString();
                        }
                        case "mySelf": {
                            return ajaxService.getSelfCount(u.getId()).toJSONString();
                        }
                        case "hotSelf": {
                            return ajaxService.getHotSelfCount(null).toJSONString();
                        }
                        case "myHotSelf": {
                            return ajaxService.getHotSelfCount(u.getId()).toJSONString();
                        }
                        default:
                            throw new Exception("页面跟着小姨子跑了");
                    }
                }
                default:
                    throw new Exception("页面跟着小姨子跑了");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return error(e.getMessage());
        }
    }


    /**
     * 没有登陆或权限不足
     */
    private String noLoginOrPurview() {
        JSONObject json = new JSONObject();
        json.put("error", "1");
        json.put("info", "没有登陆或权限不足");
        return json.toJSONString();
    }

    /**
     * 成功
     *
     * @param map
     * @return
     */
    private String success(Map<String, Object> map) {
        JSONObject json = new JSONObject();
        json.put("code", "1");
        json.putAll(map);
        return json.toJSONString();
    }

    /**
     * 异常信息
     *
     * @param message
     * @return
     */
    private String error(String message) {
        JSONObject json = new JSONObject();
        json.put("error", 0);
        json.put("info", message);
        return json.toJSONString();
    }
}
