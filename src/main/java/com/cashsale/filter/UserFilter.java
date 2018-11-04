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
import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSONObject;
import com.cashsale.bean.ResultDTO;

/**
 * @author 肥宅快乐码
 * @date 2018/10/11 - 22:29
 */
@WebFilter(urlPatterns = {"/public"})
public class UserFilter implements Filter {

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
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        /*
         * 设置响应的编码
         */
        response.setContentType("text/json;charset=UTF-8");
        response.setCharacterEncoding("utf-8");
        //获取请求头token
        String token = ((HttpServletRequest) request).getHeader("token");
        try {
            // 解析JWTtoken，错误则抛出异常
            CommonUtils.parseJWT(token);
        } catch (Exception e) {
            PrintWriter writer=response.getWriter();
            // 返回状态信息
            writer.println(JSONObject.toJSON(new ResultDTO<Object>(1001, null, "请先登录")));
            writer.flush();
            writer.close();
        }
        //允许请求通过
        chain.doFilter(request, response);
    }

    /**
     * 过滤器摧毁（该方法在Filter接口中为default，所以也可以不实现）
     */
    @Override
    public void destroy() {
        System.out.println("destroy...");
    }
}