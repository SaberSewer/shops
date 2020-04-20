package com.hhxy.shops.po;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * user
 * @author 
 */
@Data
public class User implements Serializable {
    private Long id;

    /**
     * 昵称
     */
    private String username;

    /**
     * 登录名
     */
    private String loginname;

    /**
     * 密码
     */
    private String password;

    /**
     * 注册日期
     */
    private Date registedate;

    /**
     * 管理员1 买家2 卖家3
     */
    private Long level;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 1启用 2禁用
     */
    private Long status;

    /**
     * 用户头像URL
     */
    private String img;

    private static final long serialVersionUID = 1L;
}