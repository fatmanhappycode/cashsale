package com.cashsale.controller.confirm;

import com.alibaba.fastjson.JSONObject;
import com.cashsale.bean.CustomerInfoDO;
import com.cashsale.bean.MobileCodeDTO;
import com.cashsale.bean.ResultDTO;
import com.cashsale.dao.ConfirmDAO;
import com.cashsale.util.CommonUtils;
import com.cashsale.util.mobileCode.IndustrySMS;
import com.google.gson.Gson;
import io.jsonwebtoken.Claims;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @Description: 发送验证码进行手机验证
 * @Author: 8-0416
 * @Date: 2018/12/2
 */
@WebServlet("/mobileConfirm")
public class MobileConfirmServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // 设置响应编码
        response.setContentType("application/json;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        //设置请求编码
        request.setCharacterEncoding("utf-8");

        PrintWriter writer = response.getWriter();

        // 读取前端传过来的json串
        BufferedReader br = request.getReader();
        String str,user = "";
        while((str = br.readLine()) != null){
            user += str;
        }
        CustomerInfoDO c = new Gson().fromJson(user, CustomerInfoDO.class);
        //获取用户名
        String username = c.getUsername();

        //获取请求头token
        /*Cookie[] cookies = request.getCookies();
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

        //发送手机验证码
        String statu = IndustrySMS.execute(username);
        MobileCodeDTO mobileCodeDTO = new Gson().fromJson(statu, MobileCodeDTO.class);
        String statuCode = mobileCodeDTO.getRespCode();
        String desc = mobileCodeDTO.getRespDesc();
        ResultDTO result = null;
        if(statuCode.equals("00000")){
            result = new ConfirmDAO().MobileConfirm(username);
        }else{
            result = new ResultDTO(Integer.parseInt(statuCode), null, desc);
        }
        writer.print(JSONObject.toJSON(result));
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

}
