package com.cashsale.dao;

import com.cashsale.conn.Conn;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author 肥宅快乐码
 * @date 2018/11/15 - 15:50
 */
public class AddShoppingTrolleyDAO {

    private Connection conn = new com.cashsale.conn.Conn().getCon();
    private PreparedStatement pstmt = null;
    private ResultSet rs = null;

    public boolean add(String username, String productId) {
        try {
            pstmt = conn.prepareStatement("INSERT INTO shopping_trolley(username,product_id) VALUES (?,?)");
            pstmt.setString(1,username);
            pstmt.setString(2,productId);
            pstmt.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            // 关闭连接
            if (pstmt != null && rs != null) {
                new com.cashsale.conn.Conn().closeConn(rs, pstmt, conn);
            } else {
                try {
                    conn.close();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }
}
