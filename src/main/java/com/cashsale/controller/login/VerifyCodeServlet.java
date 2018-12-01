package com.cashsale.controller.login;

import com.alibaba.fastjson.JSONObject;
import com.cashsale.enums.ResultEnum;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @Description:判断验证码是否正确
 * @Author: 8-0416
 * @Date: 2018/11/30
 */
@WebServlet("/verifyCode")
public class VerifyCodeServlet extends HttpServlet{

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("text/html;charaset=utf-8");
        response.setHeader("pragma", "no-cache");
        response.setHeader("cache-control", "no-cache");
        PrintWriter writer = response.getWriter();
        try {
            //响应数据
            int resultData;
            //获取传过来的验证码
            String verifyCode = request.getParameter("verifyCode");
            System.out.println("verifyCode----"+verifyCode);
            if(verifyCode=="") {
                resultData = ResultEnum.VERIFYCODE_ERROR.getCode();
            }else {
                //获取kaptcha生成存放在session中的验证码
                String kaptchaValue = (String) request.getSession().getAttribute(com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY);
                //比较输入的验证码和实际生成的验证码是否相同
                System.out.println("kaptchaValue="+kaptchaValue);
                System.out.println("verifyCode = "+verifyCode);
                System.out.println("verifyCode.equalsIgnoreCase(kaptchaValue)="+verifyCode.equalsIgnoreCase(kaptchaValue));
                if (kaptchaValue == null || kaptchaValue == "" || !verifyCode.equalsIgnoreCase(kaptchaValue)) {
                    resultData = ResultEnum.VERIFYCODE_ERROR.getCode();
                } else {
                    resultData = ResultEnum.VERIFYCODE_RIGHT.getCode();
                }
            }
            System.out.println("resultData = "+resultData);
            writer.print(JSONObject.toJSON(resultData));
            writer.flush();
        }catch(Exception e) {
            e.printStackTrace();
        }finally {
            if(writer != null) {
                writer.close();
            }
        }
    }
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
