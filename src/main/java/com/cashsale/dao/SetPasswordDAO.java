package com.cashsale.dao;

import com.cashsale.conn.Conn;
import com.cashsale.enums.ResultEnum;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class SetPasswordDAO {

    public int setPassword(String username, String password){
        Connection conn = new Conn().getCon();
        PreparedStatement pstmt = null;
        String sql = "";
        try{
            sql = "UPDATE all_user SET pass_word=? WHERE user_name = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1,password);
            pstmt.setString(2,username);
            pstmt.execute();
            return ResultEnum.PASSWORD_SUCCESS.getCode();
        }catch (Exception e){
            e.printStackTrace();
            return  ResultEnum.ERROR.getCode();
        }
    }


}
