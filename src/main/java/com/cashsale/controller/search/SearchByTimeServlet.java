package com.cashsale.controller.search;

import com.alibaba.fastjson.JSONObject;
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
 * @author 肥宅快乐码
 * @date 2018/11/5 - 0:16
 */
@WebServlet("/searchByTime")
public class SearchByTimeServlet  extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 设置响应编码
        resp.setContentType("application/json;charset=UTF-8");
        resp.setCharacterEncoding("UTF-8");

        // 获取参数
        String time = req.getParameter("time");
        String currentPage = req.getParameter("currentPage");
        ResultDTO<PagerDTO> result = new SearchService().searchByTime(time, currentPage);
        System.out.println(result.getData().getData().size());

        PrintWriter writer = resp.getWriter();
        writer.print(JSONObject.toJSON(result));
    }
}
