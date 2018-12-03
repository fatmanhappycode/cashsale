package com.cashsale.dao;

import com.cashsale.enums.ResultEnum;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @Description: 更新用户的信用
 * @Author: 8-0416
 * @Date: 2018/12/2
 */
public class UpdateCreditDAO {

    public int updateCredit(String username){
        Connection conn = new com.cashsale.conn.Conn().getCon();
        PreparedStatement pstmt = null;
        PreparedStatement pstmt2 = null;
        ResultSet result = null;
        String sql2 = "";
        String sql = "";
        int code = ResultEnum.ERROR.getCode();
        try {
            sql = "SELECT credit FROM user_data WHERE user_name=?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, username);
            result = pstmt.executeQuery();
            if (result.next()) {
                int score = result.getInt("credit");
                score += 5;
                System.out.println("credit="+score);
                sql2 = "UPDATE user_data SET credit=? WHERE user_name=?";
                pstmt2 = conn.prepareStatement(sql2);
                pstmt2.setInt(1,score);
                pstmt2.setString(2,username);
                pstmt2.executeUpdate();
                code = ResultEnum.SCORE_SUCCESS.getCode();
            }else{
                code = ResultEnum.LOGIN_USERNAME_ERROR.getCode();
            }
        }catch(Exception e){
            e.printStackTrace();
            code = ResultEnum.ERROR.getCode();
        }
        new com.cashsale.conn.Conn().closeConn(result, pstmt, conn);
        new com.cashsale.conn.Conn().closeConn(null, pstmt2, conn);
        return code;
    }
}
