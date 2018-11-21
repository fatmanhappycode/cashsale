package com.cashsale.controller;

import com.alibaba.fastjson.JSONObject;
import com.cashsale.bean.CustomerDO;
import com.cashsale.bean.ResultDTO;
import com.cashsale.enums.ResultEnum;
import com.cashsale.util.CommonUtils;
import com.google.gson.Gson;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import java.io.IOException;
import java.sql.Connection;

/**
 * 用于初始化
 * @author 肥宅快乐码
 * @date 2018/10/20 - 11:03
 */
@WebServlet(urlPatterns = {"/init"},loadOnStartup = 1)
public class InitServlet extends HttpServlet {
    @Override
    public void init() {
        // 将可能用到的包都在Tomcat运行时加载运行一次
        Connection conn = new com.cashsale.conn.Conn().getCon();
        CustomerDO c = new Gson().fromJson("{ \"username\":\"\",\"password\":\"\"}", CustomerDO.class);
        try {
            CommonUtils.createJWT("",1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        JSONObject.toJSON(new ResultDTO<String>(ResultEnum.SUCCESS.getCode(), null, ResultEnum.SUCCESS.getMsg()));
        CloseableHttpClient client = null;
        HttpClients.createDefault();
        HttpPost post = new HttpPost();
    }
}
