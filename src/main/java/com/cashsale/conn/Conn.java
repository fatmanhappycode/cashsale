package com.cashsale.conn;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * 用于与数据库建立连接
 * @author 肥宅快乐码
 * @date 2018/10/11 - 22:47
 */
public class Conn {
    public Connection getCon() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String url="jdbc:mysql://localhost/cashsale?useUnicode=true&characterEncoding=utf-8";
            String user="root";
            String password="";
            Connection conn = DriverManager.getConnection(url,user,password);
            return conn;
        } catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
