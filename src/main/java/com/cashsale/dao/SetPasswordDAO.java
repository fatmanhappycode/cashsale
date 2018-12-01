package com.cashsale.dao;

import com.cashsale.conn.Conn;
import com.cashsale.enums.ResultEnum;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * 设置新的密码
 * @author Sylvia
 * 2018年11月24日
 */
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
            new Conn().closeConn(null,pstmt,conn);
            return ResultEnum.PASSWORD_SUCCESS.getCode();
        }catch (Exception e){
            e.printStackTrace();
            return  ResultEnum.ERROR.getCode();
        }
    }

    /**
     * 判断用户名和邮箱是否已经注册且正确
     * @param username
     * @param email
     * @return
     */
    public boolean isRight(String username, String email){
        Connection conn = new Conn().getCon();
        PreparedStatement pstmt = null;
        ResultSet result = null;
        String sql = "";
        try{
            sql = "SELECT * FROM user_data WHERE user_name = ? AND email =?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1,username);
            pstmt.setString(2,email);
            result = pstmt.executeQuery();
            if(result.next()){
                new Conn().closeConn(null,pstmt,conn);
                return true;
            }else{
                new Conn().closeConn(null,pstmt,conn);
                return false;
            }
        }catch (Exception e){
            e.printStackTrace();
            new Conn().closeConn(null,pstmt,conn);
            return false;
        }
    }


}
