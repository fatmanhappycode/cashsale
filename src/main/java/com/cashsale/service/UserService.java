package com.cashsale.service;

import com.cashsale.bean.Result;
import com.cashsale.dao.UserLoginDao;
import com.cashsale.filter.CommonUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author 肥宅快乐码
 * @date 2018/10/28 - 16:15
 */
public class UserService {
    public Result<String> UserLogin(String userName, String password) {
        String token = new UserLoginDao().isLogin(userName,password);
        if (!token.equals("")) {
            return new Result<String>(105, token, "登录成功");
        } else {
            return new Result<String>(106, null, "登录失败,用户名或密码错误");
        }
    }
}
