package com.cashsale.controller;

import com.alibaba.fastjson.JSONObject;
import com.cashsale.bean.CustomerInfoDO;
import com.cashsale.bean.ResultDTO;
import com.cashsale.service.RegisterService;
import com.cashsale.util.MailUtil;
import com.cashsale.util.RSAUtil;
import com.cashsale.util.TimeUtil;
import com.cashsale.util.UUIDUtil;
import com.google.gson.Gson;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

@WebServlet("/forgetPassword")
public class ForgetPasswordServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

        // 设置响应编码
        response.setContentType("application/json;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        //设置请求编码
        request.setCharacterEncoding("utf-8");

        // 读取前端传过来的json串
        BufferedReader br = request.getReader();
        String str,user = "";
        while((str = br.readLine()) != null){
            user += str;
        }
        CustomerInfoDO c = new Gson().fromJson(user, CustomerInfoDO.class);
        String username = c.getUsername();
        String email = c.getEmail();

        //生成一个64位的随机验证码
        String code = UUIDUtil.getUUID() + UUIDUtil.getUUID();

        PrintWriter writer = response.getWriter();
        String encodedCode = "";
        String encodedUsername = username;
        //创建密钥对
        Map<String, String> keyMap = RSAUtil.createKeys(1024);
        //获取公钥
        String  publicKey = keyMap.get("publicKey");
        //获取密钥
        String  privateKey = keyMap.get("privateKey");
        //保存密钥
        this.getServletContext().setAttribute(username, privateKey);
        //公钥加密
        try {
            encodedCode = RSAUtil.publicEncrypt(code, RSAUtil.getPublicKey(publicKey));
            /*encodedUsername = RSAUtil.publicEncrypt(username, RSAUtil.getPublicKey(publicKey));*/
        } catch (Exception e) {
            e.printStackTrace();
        }

        //获取当前时间
        String currentTime = TimeUtil.getTime();
        System.out.println("encodeCode="+encodedCode);
        System.out.println("currentTime="+currentTime);

        /* 发送邮件 */
        try {
            MailUtil.sendMail(email, encodedCode, encodedUsername, currentTime);
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("重置密码邮件发送失败！");
        }

        /*ResultDTO<String> result = new RegisterService().UserRegister(username, email, encodedUsername, encodedCode, nickname,
                code, username);

        writer.print(JSONObject.toJSON(result));*/
    }
}

