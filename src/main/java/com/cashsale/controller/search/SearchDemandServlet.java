package com.cashsale.controller.search;

import com.alibaba.fastjson.JSONObject;
import com.cashsale.bean.DemandDO;
import com.cashsale.bean.PagerDTO;
import com.cashsale.bean.ResultDTO;
import com.cashsale.service.SearchService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @Description: 获取需求
 * @Author: 8-0416
 * @Date: 2018/12/8
 */
@WebServlet("/searchDemand")
public class SearchDemandServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 设置响应编码
        resp.setContentType("application/json;charset=UTF-8");
        resp.setCharacterEncoding("UTF-8");

        ResultDTO<DemandDO> result = new SearchService().searchDemand();

        PrintWriter writer = resp.getWriter();
        writer.print(JSONObject.toJSON(result));
    }

}
