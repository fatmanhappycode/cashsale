package com.cashsale.controller;

import com.alibaba.fastjson.JSONObject;
import com.cashsale.bean.CustomerDO;
import com.cashsale.bean.CustomerInfoDO;
import com.cashsale.bean.ResultDTO;
import com.cashsale.service.SetPasswordService;
import com.cashsale.util.CommonUtils;
import com.cashsale.util.KeytoolUtil;
import com.cashsale.util.RSAUtil;
import com.google.gson.Gson;
import io.jsonwebtoken.Claims;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

/**
 * 设置新的密码
 * @author Sylvia
 * 2018年11月24日
 */
@WebServlet("/setPassword")
public class SetPasswordServlet extends HttpServlet {

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
        CustomerDO c = new Gson().fromJson(user, CustomerDO.class);
        String password = c.getPassword();

        //获取请求头token
        Cookie[] cookies = request.getCookies();
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
        String username = claims.getSubject();
        String encodedPass = "";
        try{
            //创建密钥对
            Map<String, String> keyMap = RSAUtil.createKeys(1024);
            //获取公钥
            String  publicKey = keyMap.get("publicKey");
            //获取密钥
            String  privateKey = keyMap.get("privateKey");
            //删除原来的密钥
            new KeytoolUtil().DeleteAlias(request.getServletContext().getRealPath("keytool")+"\\cashsale.keystore",username);
            //保存新的密钥
            new KeytoolUtil().addNew(request.getServletContext().getRealPath("keytool")+"\\cashsale.keystore",username,
                    RSAUtil.getPrivateKey(privateKey));
            //公钥加密
            encodedPass = RSAUtil.publicEncrypt(password, RSAUtil.getPublicKey(publicKey));
        }catch (Exception e){
            e.printStackTrace();
        }

        PrintWriter writer = response.getWriter();
        ResultDTO result = new SetPasswordService().setPassword(username, encodedPass);
        writer.print(JSONObject.toJSON(result));
    }
}
