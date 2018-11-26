package com.cashsale.controller;

import com.alibaba.fastjson.JSONObject;
import com.cashsale.bean.PagerDTO;
import com.cashsale.bean.ProductDO;
import com.cashsale.dao.GetMyProductDAO;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * @Description:
 * @Author: 8-0416
 * @Date: 2018/11/26
 */
public class GetInteractNumberServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // 设置响应编码
        response.setContentType("application/json;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");

        String strProductId = request.getParameter("productId");
        int productId = Integer.parseInt(strProductId);

        /*PrintWriter writer = response.getWriter();
        List<ProductDO> product = new GetMyProductDAO().getMyProduct(username);
        int number = new GetMyProductDAO().getProductNum(username);
        PagerDTO<ProductDO> result = new PagerDTO<>(number,product);
        writer.println(JSONObject.toJSON(result));*/
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

}
