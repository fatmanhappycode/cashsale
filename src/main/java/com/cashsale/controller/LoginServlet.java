package com.cashsale.controller;

import com.alibaba.fastjson.JSONObject;
import com.cashsale.bean.CustomerDO;
import com.cashsale.bean.ResultDTO;
import com.cashsale.service.UserService;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

/**
 *
 * @author 肥宅快乐码
 * @date 2018/10/11 - 22:50
 */
@WebServlet(urlPatterns = {"/login"})
public class LoginServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

	@Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 设置响应编码
        resp.setContentType("application/json;charset=UTF-8");
        resp.setCharacterEncoding("UTF-8");

        BufferedReader br = req.getReader();
        String str,user = "";
        while((str = br.readLine()) != null){
            user += str;
        }

        CustomerDO c = new Gson().fromJson(user, CustomerDO.class);

        String userName = c.getUsername();
        String password = c.getPassword();
        String keystoreUrl = req.getServletContext().getRealPath("keytool")+"\\cashsale.keystore";

        PrintWriter writer = resp.getWriter();

        ResultDTO<String> result = new UserService().userLogin(userName,password, keystoreUrl);
        writer.print(JSONObject.toJSON(result));
    }
}
