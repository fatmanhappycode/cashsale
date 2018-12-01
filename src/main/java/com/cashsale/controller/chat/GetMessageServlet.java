package com.cashsale.controller.chat;

import com.alibaba.fastjson.JSONObject;
import com.cashsale.bean.MessageDTO;
import com.cashsale.bean.ResultDTO;
import com.cashsale.dao.GetMessageDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * @author 肥宅快乐码
 * @date 2018/11/16 - 21:17
 */
@WebServlet("/getMessage")
public class GetMessageServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 设置响应编码
        resp.setContentType("application/json;charset=UTF-8");
        resp.setCharacterEncoding("UTF-8");

        String username = req.getParameter("username");
        ResultDTO<List< MessageDTO >> result = new GetMessageDAO().getMessage(username);

        PrintWriter writer = resp.getWriter();
        writer.print(JSONObject.toJSON(result));
    }
}
