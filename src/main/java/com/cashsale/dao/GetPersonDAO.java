package com.cashsale.dao;

import com.cashsale.bean.CustomerDO;
import com.cashsale.bean.CustomerInfoDO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

/**
 * 获取个人的详细信息
 * @author Sylvia
 * @date 2018/11/17 - 4:25
 */
public class GetPersonDAO {

    private Connection conn = new com.cashsale.conn.Conn().getCon();
    private PreparedStatement pstmt = null;
    private ResultSet result = null;

    public CustomerInfoDO getPersonInfo(String username){
        try{
            String sql = "SELECT * FROM user_data WHERE user_name =?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1,username);
            result = pstmt.executeQuery();
            if(result.next()){
                CustomerInfoDO c = new CustomerInfoDO(username);
                c.setCredit(result.getInt("credit"));
                c.setSno(result.getString("sno"));
                c.setRealname(result.getString("real_name"));
                c.setNickname(result.getString("nick_name"));
                c.setCertificate(result.getBoolean("is_certificate"));
                c.setEmail(result.getString("email"));

                new com.cashsale.conn.Conn().closeConn(result, pstmt, conn);
                return c;
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        new com.cashsale.conn.Conn().closeConn(result, pstmt, conn);
        return null;
    }
}
