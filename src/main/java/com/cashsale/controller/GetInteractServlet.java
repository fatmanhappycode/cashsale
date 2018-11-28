package com.cashsale.controller;

import com.alibaba.fastjson.JSONObject;
import com.cashsale.bean.InteractDTO;
import com.cashsale.bean.PagerDTO;
import com.cashsale.dao.GetInteractDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @Description:获取评论、点赞、分享
 * @Author: 8-0416
 * @Date: 2018/11/26
 */
@WebServlet("/getInteract")
public class GetInteractServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // 设置响应编码
        response.setContentType("application/json;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");

        String strProductId = request.getParameter("productId");
        String strPage = request.getParameter("page");
        int productId = Integer.parseInt(strProductId);
        int page = 0;
        if(strPage != null && !strPage.equals("")){
            page = Integer.parseInt(strPage);
        }
        PrintWriter writer = response.getWriter();
        PagerDTO<InteractDTO> result = new GetInteractDAO().getInteract(page, productId);
        writer.print(JSONObject.toJSON(result));
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

}
