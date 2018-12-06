package com.cashsale.conn;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * 用于与数据库建立连接
 * @author 肥宅快乐码
 * @date 2018/10/11 - 22:47
 */
public class Conn {
	
	/**
	 * 链接数据库
	 * @return
	 */
    public Connection getCon() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String url="jdbc:mysql://localhost/cashsale?useUnicode=true&characterEncoding=utf-8";
            String user="root";
            String password="3624";

            Connection conn = DriverManager.getConnection(url,user,password);
            return conn;
        } catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    /**
     * 关闭链接
     * @param rs
     * @param pstmt
     * @param conn
     */
    public void closeConn(ResultSet rs, PreparedStatement pstmt, Connection conn) {
    	try {
    		if(rs != null) {
    			rs.close();
    		}
			if(pstmt != null) {
				pstmt.close();
			}
	    	if(!conn.isClosed()) {
	    		conn.close();
	    	}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.err.println("数据库链接关闭失败!");
		}
    }
    
    /**
     * 关闭链接
     * @param rs
     * @param stmt
     * @param conn
     */
    public void closeConn(ResultSet rs, Statement stmt, Connection conn) {
    	try {
    		if(rs != null) {
    			rs.close();
    		}
			if(stmt != null) {
				stmt.close();
			}
	    	if(!conn.isClosed()) {
	    		conn.close();
	    	}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.err.println("数据库链接关闭失败!");
		}
    }
}
