package com.cashsale.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;
import com.cashsale.bean.CustomerInfoDO;
import com.cashsale.bean.ResultDTO;
import com.cashsale.service.RegisterService;
import com.cashsale.util.KeytoolUtil;
import com.cashsale.util.RSAUtil;
import com.cashsale.util.UUIDUtil;
import com.google.gson.Gson;

/**
 * 用户注册方法
 * @author Sylvia
 * 2018年10月14日
 */
@WebServlet("/register")
public class RegisterServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	@Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}
	
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

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
        String password = c.getPassword();
        String nickname = c.getNickname();
        String email = c.getEmail();
        
        //生成一个64位的随机验证码
        String code = UUIDUtil.getUUID() + UUIDUtil.getUUID();

        PrintWriter writer = response.getWriter();
        
        String encodedCode = "";
        String encodedPass = "";
    	//创建密钥对
    	Map<String, String> keyMap = RSAUtil.createKeys(1024);
    	//获取公钥
        String  publicKey = keyMap.get("publicKey");
        //获取密钥
        String  privateKey = keyMap.get("privateKey");
        try {
            //保存密钥
            this.getServletContext().setAttribute(username, privateKey);
            new KeytoolUtil().addNew(request.getServletContext().getRealPath("keytool")+"\\cashsale.keystore",username,RSAUtil.getPrivateKey(privateKey));
            //公钥加密
			encodedCode = RSAUtil.publicEncrypt(code, RSAUtil.getPublicKey(publicKey)); 
	        encodedPass = RSAUtil.publicEncrypt(password, RSAUtil.getPublicKey(publicKey));
		} catch (Exception e) {
			e.printStackTrace();
		}
        
        ResultDTO<String> result = new RegisterService().UserRegister(username, email, encodedPass, encodedCode, nickname,
    		 code);
        
        writer.print(JSONObject.toJSON(result));
    }
}