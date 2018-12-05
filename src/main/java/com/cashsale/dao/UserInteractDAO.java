package com.cashsale.dao;

import com.cashsale.bean.PagerDTO;
import com.cashsale.bean.ResultDTO;
import com.cashsale.conn.Conn;
import com.cashsale.enums.ResultEnum;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @Description:用户之间的关注
 * @Author: 8-0416
 * @Date: 2018/12/4
 */
public class UserInteractDAO {

    /**
     * 关注用户
     * @param username
     * @param concern
     * @return
     */
    public ResultDTO concern(String username, String concern){
        Connection conn = new com.cashsale.conn.Conn().getCon();
        PreparedStatement pstmt = null;
        String sql = "";
        try{
            boolean is = isConcern(username, concern);
            if(is){
                return new ResultDTO(ResultEnum.ALREADY_CONCERN.getCode(), null, ResultEnum.ALREADY_CONCERN.getMsg());
            }else {
                sql = "INSERT INTO user_interaction(user_name, concern) VALUE(?,?)";
                pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, username);
                pstmt.setString(2, concern);
                pstmt.executeUpdate();
                new Conn().closeConn(null, pstmt, conn);
                return new ResultDTO(ResultEnum.CONCERN_SUCCESS.getCode(), null, ResultEnum.CONCERN_SUCCESS.getMsg());
            }
        }catch (Exception e){
            new Conn().closeConn(null, pstmt, conn);
            e.printStackTrace();
            return new ResultDTO(ResultEnum.ERROR.getCode(), null, ResultEnum.ERROR.getMsg());
        }
    }

    /**
     * 判断是否已经关注
     * @param username
     * @param concern
     * @return
     */
    public boolean isConcern(String username, String concern){
        Connection conn = new com.cashsale.conn.Conn().getCon();
        PreparedStatement pstmt = null;
        ResultSet result = null;
        String sql = "";
        try {
            sql = "SELECT * FROM user_interaction WHERE user_name = ? AND concern = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, username);
            pstmt.setString(2, concern);
            result = pstmt.executeQuery();
            if (result.next()) {
                new Conn().closeConn(result, pstmt, conn);
                return true;
            }else{
                new Conn().closeConn(result, pstmt, conn);
                return false;
            }
        }catch (Exception e){
            e.printStackTrace();
            new Conn().closeConn(result, pstmt, conn);
            return true;
        }
    }
}
