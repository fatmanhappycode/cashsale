package com.cashsale.controler;

import com.alibaba.fastjson.JSONObject;
import com.cashsale.bean.Customer;
import com.cashsale.bean.Result;
import com.cashsale.filter.CommonUtils;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import java.sql.Connection;

/**
 * @author 肥宅快乐码
 * @date 2018/10/20 - 11:03
 */
@WebServlet(urlPatterns = {"/init"},loadOnStartup = 1)
public class InitServlet extends HttpServlet {
    @Override
    public void init() {
        Connection conn = new com.cashsale.conn.Conn().getCon();
        Customer c = new Gson().fromJson("{ \"username\":\"\",\"password\":\"\"}",Customer.class);
        try {
            CommonUtils.createJWT("",1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        JSONObject.toJSON(new Result<String>(0, null, ""));
    }
}
