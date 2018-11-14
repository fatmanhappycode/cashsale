package com.cashsale.dao;

import com.cashsale.util.CommonUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author 肥宅快乐码
 * @date 2018/11/13 - 22:18
 */
public class IsConfirmDAO {
    Connection conn = new com.cashsale.conn.Conn().getCon();
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    public boolean isConfirm(String username) {
        try {
            pstmt = conn.prepareStatement("SELECT is_certificate FROM user_data WHERE user_name=?");
            pstmt.setString(1,username);
            rs = pstmt.executeQuery();
            int is_certificate;
            if (rs.next()) {
                is_certificate = rs.getInt(1);
            } else {
                return false;
            }
            if (is_certificate == 1) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            // 关闭连接
            try {
                rs.close();
                pstmt.close();
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
