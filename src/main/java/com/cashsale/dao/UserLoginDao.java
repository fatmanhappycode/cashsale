package com.cashsale.dao;

import com.cashsale.filter.CommonUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author 肥宅快乐码
 * @date 2018/10/28 - 16:23
 */
public class UserLoginDao {
    public String isLogin(String userName, String password) {
        Connection conn = new com.cashsale.conn.Conn().getCon();
        try {
            PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM all_user WHERE user_name=? AND pass_word=?");
            pstmt.setString(1, userName);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                String token = CommonUtils.createJWT(userName, 30 * 60 * 1000);
                return token;
            } else {
                return "";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
}
