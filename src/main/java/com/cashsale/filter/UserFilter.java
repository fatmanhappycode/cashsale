package com.cashsale.filter;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;
import com.cashsale.bean.ResultDTO;
import com.cashsale.util.CommonUtils;

/**
 * @author 肥宅快乐码
 * @date 2018/10/11 - 22:29
 */
/*@WebFilter(urlPatterns = "/*")*/
    public class UserFilter extends HttpServlet implements Filter {

    /**
     * 过滤器初始化（该方法在Filter接口中为default，所以也可以不实现）
     */
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("init...");
    }

    /**
     * 过滤请求
     */
    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
            throws IOException, ServletException {
        /*
         * 设置响应的编码
         */
        req.setCharacterEncoding("UTF-8");

        //对request进行强制转型以调用其中的方法
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        request.setCharacterEncoding("UTF-8");

        //获取请求头token
        Cookie[] cookies = request.getCookies();
        String token = "";
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                switch (cookie.getName()) {
                    case "token":
                        token = cookie.getValue();
                        break;
                    default:
                        break;
                }
            }
        }else{
            System.out.println("123");
            response.sendRedirect("error.html");
            return;
        }

        try {
            // 解析JWTtoken，错误则抛出异常
            CommonUtils.parseJWT(token);
        } catch (Exception e) {
            System.out.println("234");
            response.sendRedirect("error.html");
        }

        //允许请求通过
        chain.doFilter(req, resp);
    }

    /**
     * 过滤器摧毁（该方法在Filter接口中为default，所以也可以不实现）
     */
    @Override
    public void destroy() {
        System.out.println("destroy...");
    }
}