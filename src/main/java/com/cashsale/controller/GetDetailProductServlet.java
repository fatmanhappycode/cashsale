package com.cashsale.controller;

import com.alibaba.fastjson.JSONObject;
import com.cashsale.bean.ProductDO;
import com.cashsale.bean.ResultDTO;
import com.cashsale.dao.DetailProductInfoDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author 肥宅快乐码
 * @date 2018/11/10 - 17:01
 */
@WebServlet("/GetDetailProduct")
public class GetDetailProductServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 设置响应编码
        resp.setContentType("application/json;charset=UTF-8");
        resp.setCharacterEncoding("UTF-8");

        // 获取参数
        String productId = req.getParameter("productId");
        ResultDTO<ProductDO> result = new DetailProductInfoDAO().showDetailProduct(productId);

        PrintWriter writer = resp.getWriter();
        writer.print(JSONObject.toJSON(result));
    }
}
