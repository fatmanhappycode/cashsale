package com.cashsale.dao;

import com.cashsale.conn.Conn;
import com.cashsale.enums.ResultEnum;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @Description: 商品交易
 * @Author: 8-0416
 * @Date: 2018/11/25
 */
public class TransactDAO {

    public static int transact(int productId){
        Connection conn = new Conn().getCon();
        PreparedStatement pstmt = null;
        PreparedStatement pstmt2 = null;
        ResultSet result = null;
        String sql2 = "";
        String sql = "";
        try{
            sql = "SELECT * FROM product_info WHERE product_id=?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1,productId);
            result = pstmt.executeQuery();
            if(result.next()){
                //把交易成功的商品加入transaction_history中
                sql2 = "INSERT INTO transaction_history(product_id,title,label,price,trade_method,is_bargain,product_description,image_url,user_name,publish_time,trade_place)" +
                        " VALUES(?,?,?,?,?,?,?,?,?,?,?)";
                pstmt2 = conn.prepareStatement(sql2);
                pstmt2.setInt(1,productId);
                pstmt2.setString(2,result.getString("title"));
                pstmt2.setString(3,result.getString("label"));
                pstmt2.setDouble(4,result.getDouble("price"));
                pstmt2.setString(5,result.getString("trade_method"));
                pstmt2.setString(6,result.getString("is_bargain"));
                pstmt2.setString(7,result.getString("product_description"));
                pstmt2.setString(8,result.getString("image_url"));
                pstmt2.setString(9,result.getString("user_name"));
                pstmt2.setTimestamp(10,result.getTimestamp("publish_time"));
                pstmt2.setString(11,result.getString("trade_place"));

                //在product_info中删除该商品
                sql = "DELETE FROM product_info WHERE product_id = ?";
                pstmt = conn.prepareStatement(sql);
                pstmt.setInt(1,productId);

                pstmt2.execute();
                pstmt.execute();
            }else{
                new Conn().closeConn(result,pstmt,conn);
                new Conn().closeConn(null,pstmt2,conn);
                return ResultEnum.NO_FOUND.getCode();
            }
            new Conn().closeConn(result,pstmt,conn);
            new Conn().closeConn(null,pstmt2,conn);
            return ResultEnum.TRANSACT_SUCCESS.getCode();
        }catch (Exception e){
            new Conn().closeConn(result,pstmt,conn);
            new Conn().closeConn(null,pstmt2,conn);
            e.printStackTrace();
            return ResultEnum.ERROR.getCode();
        }
    }
}
