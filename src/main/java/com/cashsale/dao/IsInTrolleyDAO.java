package com.cashsale.dao;

import javax.xml.transform.Result;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author 肥宅快乐码
 * @date 2018/11/15 - 22:27
 */
public class IsInTrolleyDAO {
    Connection conn = new com.cashsale.conn.Conn().getCon();
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    public boolean isInTrolley(String username, String productId) {
        try {
            pstmt = conn.prepareStatement("SELECT * FROM commodity_score WHERE user_name=? AND product_id=? AND is_collect=1");
            pstmt.setString(1,username);
            pstmt.setString(2, productId);
            rs = pstmt.executeQuery();
            if (rs.next()) {
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
                pstmt.close();
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
