package com.cashsale.controller.person;

import com.alibaba.fastjson.JSONObject;
import com.cashsale.bean.PagerDTO;
import com.cashsale.bean.ProductDO;
import com.cashsale.bean.ResultDTO;
import com.cashsale.bean.UserInteractDTO;
import com.cashsale.dao.GetMyProductDAO;
import com.cashsale.service.GetMyConcernService;
import com.cashsale.util.CommonUtils;
import io.jsonwebtoken.Claims;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * @Description: 获取我关注的人以及发布商品的数目
 * @Author: 8-0416
 * @Date: 2018/12/4
 */
@WebServlet("/getMyConcern")
public class GetMyConcernServlet extends HttpServlet{
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // 设置响应编码
        response.setContentType("application/json;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");

        String username = request.getParameter("username");

        PrintWriter writer = response.getWriter();
        UserInteractDTO userInteractDTO = new GetMyConcernService().getMyConcern(username);
        ResultDTO<UserInteractDTO> result = new ResultDTO<>(0, userInteractDTO, null);
        writer.println(JSONObject.toJSON(result));
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

}
