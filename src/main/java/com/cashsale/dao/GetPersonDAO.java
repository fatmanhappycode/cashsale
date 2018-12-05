package com.cashsale.dao;

import com.cashsale.bean.CustomerInfoDO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * 获取个人的详细信息
 * @author Sylvia
 * @date 2018/11/17 - 4:25
 */
public class GetPersonDAO {

    /**
     * 获取个人的基本信息
     * @param username
     * @return
     */
    public CustomerInfoDO getPersonInfo(String username){
        int fan = getMyFans(username);
        int concern = getMyConcern(username);

        Connection conn = new com.cashsale.conn.Conn().getCon();
        PreparedStatement pstmt = null;
        ResultSet result = null;

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
                c.setMobile_certificate(result.getBoolean("mobile_certificate"));
                c.setMyFans(fan);
                c.setMyConcern(concern);

                new com.cashsale.conn.Conn().closeConn(result, pstmt, conn);
                return c;
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        new com.cashsale.conn.Conn().closeConn(result, pstmt, conn);
        return null;
    }

    /**
     * 获取我的粉丝数目
     * @param username
     * @return
     */
    public int getMyFans(String username){
        Connection conn = new com.cashsale.conn.Conn().getCon();
        PreparedStatement pstmt = null;
        ResultSet result = null;

        try{
            String sql = "SELECT COUNT(*) FROM user_interaction WHERE concern = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1,username);
            result = pstmt.executeQuery();
            if(result.next()){
                new com.cashsale.conn.Conn().closeConn(result, pstmt, conn);
                return result.getInt(1);
            }else{
                new com.cashsale.conn.Conn().closeConn(result, pstmt, conn);
                return 0;
            }
        }catch (Exception e){
            e.printStackTrace();
            new com.cashsale.conn.Conn().closeConn(result, pstmt, conn);
            return 0;
        }
    }

    /**
     * 获取我的粉丝列表
     * @param username
     * @return
     */
    public List<String> getFan(String username){
        List<String> list = new ArrayList<>();
        Connection conn = new com.cashsale.conn.Conn().getCon();
        PreparedStatement pstmt = null;
        ResultSet result = null;
        try{
            String sql = "SELECT * FROM user_interaction WHERE concern=?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1,username);
            result = pstmt.executeQuery();
            System.out.println("我的粉丝: ");
            while(result.next()){
                list.add(result.getString("user_name"));
                System.out.println(result.getString("user_name") + "   ");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        new com.cashsale.conn.Conn().closeConn(result, pstmt, conn);
        return list;
    }

    /**
     * 获取我关注的人列表
     * @param username
     * @return
     */
    public List<String> getConcern(String username){
        List<String> list = new ArrayList<>();
        Connection conn = new com.cashsale.conn.Conn().getCon();
        PreparedStatement pstmt = null;
        ResultSet result = null;
        try{
            String sql = "SELECT * FROM user_interaction WHERE user_name=?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1,username);
            result = pstmt.executeQuery();
            System.out.println("我关注的人: ");
            while(result.next()){
                list.add(result.getString("concern"));
                System.out.println(result.getString("concern") + "   ");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        new com.cashsale.conn.Conn().closeConn(result, pstmt, conn);
        return list;
    }

    /**
     * 获取我关注的人数目
     * @param username
     * @return
     */
    public int getMyConcern(String username){

        Connection conn = new com.cashsale.conn.Conn().getCon();
        PreparedStatement pstmt = null;
        ResultSet result = null;

        try{
            String sql = "SELECT COUNT(*) FROM user_interaction WHERE user_name = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1,username);
            result = pstmt.executeQuery();
            if(result.next()){
                new com.cashsale.conn.Conn().closeConn(result, pstmt, conn);
                return result.getInt(1);
            }else{
                new com.cashsale.conn.Conn().closeConn(result, pstmt, conn);
                return 0;
            }
        }catch (Exception e){
            e.printStackTrace();
            new com.cashsale.conn.Conn().closeConn(result, pstmt, conn);
            return 0;
        }
    }
}
