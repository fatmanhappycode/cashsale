package com.cashsale.service;

import com.cashsale.bean.Result;
import com.cashsale.dao.UserLoginDAO;
import com.cashsale.util.SendPostUtil;
import org.apache.http.NameValuePair;

import java.util.List;

/**
 * @author 肥宅快乐码
 * @date 2018/10/28 - 16:15
 */
public class UserService {
    /**
     * 判断登录成功与否，成功返回带token的Result
     * @param userName
     * @param password
     * @return Result<String>
     */
    public Result<String> userLogin(String userName, String password) {
        String token = new UserLoginDAO().isLogin(userName,password);
        if (!token.equals("")) {
            return new Result<String>(105, token, "登录成功");
        } else {
            return new Result<String>(106, null, "登录失败,用户名或密码错误");
        }
    }

    public Result<String> userComfirm(String encoded) {
        // 教务系统登录请求链接
        String url = "http://jwxt.gduf.edu.cn/jsxsd/xk/LoginToXk";
        Object[] params = new Object[]{"encoded"};
        Object[] values = new Object[]{encoded};
        List<NameValuePair> paramsList = SendPostUtil.getParams(params, values);
        int a = 0;
        try {
            a = SendPostUtil.sendPost(url,paramsList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (a == 302) {
            return new Result<String>(121, null,"认证成功");
        }
        else if (a == 200) {
            return new Result<>(122, null,"认证失败");
        }
        else {
            return new Result<>(123, null,"认证失败，系统异常");
        }
    }
}
