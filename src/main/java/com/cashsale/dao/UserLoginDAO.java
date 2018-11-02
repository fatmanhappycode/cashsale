package com.cashsale.dao;

import com.cashsale.util.CommonUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author 肥宅快乐码
 * @date 2018/10/28 - 16:23
 */
public class UserLoginDAO {

    Connection conn = new com.cashsale.conn.Conn().getCon();
    PreparedStatement pstmt = null;
    ResultSet rs = null;

    public String isLogin(String userName, String password) {
        try {
            // 查询是否存在账号密码在all_user
            pstmt = conn.prepareStatement("SELECT * FROM all_user WHERE user_name=? AND pass_word=?");
            pstmt.setString(1, userName);
            pstmt.setString(2, password);
            rs = pstmt.executeQuery();
            // 有则创建token
            if (rs.next()) {
                String token = CommonUtils.createJWT(userName, 30 * 60 * 1000);
                return token;
            } else {
                return "";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "";
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
