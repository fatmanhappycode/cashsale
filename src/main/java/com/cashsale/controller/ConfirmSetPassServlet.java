package com.cashsale.controller;

import com.cashsale.bean.ResultDTO;
import com.cashsale.service.ActiveService;
import com.cashsale.util.RSAUtil;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/comfirmSet")
public class ConfirmSetPassServlet extends HttpServlet{
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("utf-8");

        // 接收激活码
        String code = request.getParameter("code");
        String username = request.getParameter("username");
        String currentTime = request.getParameter("currentTime");

        // 设置响应编码
        response.setContentType("application/json;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");

        PrintWriter writer = response.getWriter();

        //获取密钥
        String privateKey = (String) this.getServletContext().getAttribute(username);
        try
        {
            //解密
            code = RSAUtil.privateDecrypt(code, RSAUtil.getPrivateKey(privateKey));
            /*username = RSAUtil.privateDecrypt(username, RSAUtil.getPrivateKey(privateKey));*/

            String StrCode = (String) this.getServletContext().getAttribute(username);
            if(code.equals(StrCode)){
                //跳转到重置密码的页面
                response.sendRedirect("");
            }else{
                //跳转到验证失败的页面
                response.sendRedirect("");
            }
        }
        catch(Exception e )
        {
            System.err.println("解密失败！");
            e.printStackTrace();
            //跳转到验证失败的页面
            response.sendRedirect("");
        }

        /*ResultDTO<String> result = new ActiveService().UserActive(code, currentTime, username, password);
        if (result.getMsg().equals("激活成功")) {
            response.sendRedirect("/activesuccess.html");
        } else {
            response.sendRedirect("/activesuccess.html");
        }*/
    }
}
