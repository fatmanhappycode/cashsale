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
import com.cashsale.bean.CustomerData;
import com.cashsale.bean.Result;
import com.cashsale.service.RegisterService;
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
        
        CustomerData c = new Gson().fromJson(user,CustomerData.class);
        String username = c.getUsername();
        String password = c.getPassword();
        String nickname = c.getNickname();
        String email = c.getEmail();
        
        //生成一个64位的随机验证码
        String code = UUIDUtil.getUUID() + UUIDUtil.getUUID();

        PrintWriter writer = response.getWriter();
        
        String encodedCode = "";
        String encodedPass = "";
        //System.out.println(code);
    	//创建密钥对
    	Map<String, String> keyMap = RSAUtil.createKeys(1024);
    	//获取公钥
        String  publicKey = keyMap.get("publicKey");
        //获取密钥
        String  privateKey = keyMap.get("privateKey");
        //保存密钥
        this.getServletContext().setAttribute(username, privateKey);
        System.out.println(privateKey);
        //公钥加密
        try {
			encodedCode = RSAUtil.publicEncrypt(code, RSAUtil.getPublicKey(publicKey)); 
	        //System.out.println(encodedCode);
	        encodedPass = RSAUtil.publicEncrypt(password, RSAUtil.getPublicKey(publicKey));
	        //System.out.println(encodedPass);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("加密失败！");
		}
        
        Result<String> result = new RegisterService().UserRegister(username, email, encodedPass, encodedCode, nickname,
    		 code, password);
        
        writer.print(JSONObject.toJSON(result));
        
        /*Connection conn = new com.cashsale.conn.Conn().getCon();
        
        try {
        	//判断该用户名和邮箱是否已被注册
            PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM user_data WHERE user_name=?");
            pstmt.setString(1,username);
            ResultSet rs = pstmt.executeQuery();
            PreparedStatement pstmt2 = conn.prepareStatement("SELECT * FROM all_user WHERE user_name=?");
            pstmt2.setString(1, username);
            ResultSet rs2 = pstmt2.executeQuery();
            PreparedStatement pstmt3 = conn.prepareStatement("SELECT * FROM user_data WHERE email = ?");
            pstmt3.setString(1, email);
            ResultSet rs3 = pstmt3.executeQuery();
            
            if (rs.next() || rs2.next()) {
                writer.print(JSONObject.toJSON(new Result<String>(103, null, "该手机号已被注册！")));
            } 
            else if(rs3.next()){
            	writer.print(JSONObject.toJSON(new Result<String>(110, null, "该邮箱已被注册！")));
            }
            else{
            	//将用户信息存进数据库中
            	pstmt = conn.prepareStatement("INSERT INTO user_data(user_name, real_name,email,code) VALUES(?,?,?,?)");
            	pstmt.setString(1, username);
            	pstmt.setString(2, nickname);
            	pstmt.setString(3, email);
            	pstmt.setString(4, code);
            	
            	pstmt2 = conn.prepareStatement("INSERT INTO all_user VALUES(?,?)");
            	pstmt2.setString(1, username);
            	pstmt2.setString(2, password);
            	
            	//获取当前时间
            	String currentTime = TimeUtil.getTime();
            	
            	
            	//System.out.println(code);
            	//创建密钥对
            	Map<String, String> keyMap = RSAUtil.createKeys(1024);
            	//获取公钥
                String  publicKey = keyMap.get("publicKey");
                //获取密钥
                String  privateKey = keyMap.get("privateKey");
                //保存密钥
                this.getServletContext().setAttribute(username, privateKey);
                //公钥加密
                String encodedCode = RSAUtil.publicEncrypt(code, RSAUtil.getPublicKey(publicKey));
                //System.out.println(encodedCode);
                String encodedPass = RSAUtil.publicEncrypt(password, RSAUtil.getPublicKey(publicKey));
                //System.out.println(encodedPass);

            	//发送邮件
            	MailUtil.sendMail(email, encodedCode, username, encodedPass, nickname, currentTime);
            	
            	pstmt.execute();
            	pstmt2.execute();
            	writer.print(JSONObject.toJSON(new Result<String>(109, null, "请到邮箱进行账号激活！")));
            }
        } 
        catch (Exception e) {
        	e.printStackTrace();
            writer.print(JSONObject.toJSON(new Result<String>(102,null,"注册失败！")));
        }*/
    }
}