package com.cashsale.controller.person;

import com.alibaba.fastjson.JSONObject;
import com.cashsale.bean.ResultDTO;
import com.cashsale.dao.UserInteractDAO;
import com.cashsale.enums.ResultEnum;
import com.cashsale.service.UpdateScoreService;
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

/**
 * @Description: 是否已经关注
 * @Author: 8-0416
 * @Date: 2018/12/4
 */
@WebServlet("/isConcern")
public class IsConcernServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 设置响应编码
        resp.setContentType("application/json;charset=UTF-8");
        resp.setCharacterEncoding("UTF-8");

        // 获取参数
        String concern = req.getParameter("concern");
        String username = req.getParameter("username");

        //获取请求头token
        /*Cookie[] cookies = req.getCookies();
        String token = "";
        for (Cookie cookie : cookies) {
            switch(cookie.getName()){
                case "token":
                    token = cookie.getValue();
                    break;
                default:
                    break;
            }
        }
        Claims claims = null;
        try {
            claims = CommonUtils.parseJWT(token);
        } catch (Exception e) {
            e.printStackTrace();
        }
        String username = claims.getSubject();*/

        PrintWriter writer = resp.getWriter();
        ResultDTO result = null;
        boolean isConcern = new UserInteractDAO().isConcern(username, concern);
        if(isConcern){
            result = new ResultDTO(ResultEnum.ALREADY_CONCERN.getCode(), null, ResultEnum.ALREADY_CONCERN.getMsg());
        }else{
            result = new ResultDTO(ResultEnum.NOT_CONCERN.getCode(), null, ResultEnum.NOT_CONCERN.getMsg());
        }
        writer.print(JSONObject.toJSON(result));
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
