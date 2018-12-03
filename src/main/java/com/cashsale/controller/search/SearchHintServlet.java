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
import java.util.ArrayList;

/**
 * @author 肥宅快乐码
 * @date 2018/12/2 - 22:13
 */
@WebServlet("/searchhint")
public class SearchHintServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        // 设置响应编码
        resp.setContentType("application/json;charset=UTF-8");
        resp.setCharacterEncoding("UTF-8");

        // 获取参数
        String title = req.getParameter("title");
        ResultDTO<ArrayList<String>> result = new SearchService().searchHint(title);

        PrintWriter writer = resp.getWriter();
        writer.print(JSONObject.toJSON(result));
    }
}
