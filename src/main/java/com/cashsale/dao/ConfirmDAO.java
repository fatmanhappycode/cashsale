package com.cashsale.dao;

import com.cashsale.util.CommonUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author 肥宅快乐码
 * @date 2018/11/10 - 10:47
 */
public class ConfirmDAO {
    Connection conn = new com.cashsale.conn.Conn().getCon();
    PreparedStatement pstmt = null;

    public boolean Comfirm(String username) {
        try {
            pstmt = conn.prepareStatement("UPDATE user_data SET is_certificate = 1 WHERE user_name=?");
            pstmt.setString(1, username);
            pstmt.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            // 关闭连接
            try {
                pstmt.close();
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
