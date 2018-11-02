package com.cashsale.bean;

/**
 * @author 肥宅快乐码
 * @date 2018/10/10 - 22:39
 * 存放用户账号密码
 */
public class CustomerDO {
    /**
     * 用户名
     */
    private String username;
    /**
     * 密码
     */
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
