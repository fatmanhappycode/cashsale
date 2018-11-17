package com.cashsale.dao;

import com.cashsale.bean.CustomerInfoDO;
import com.cashsale.bean.ProductDO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class GetMyProductDAO {

    /**
     * 获取我发布的商品的详情
     * @param username
     * @return
     */
    public List<ProductDO> getMyProduct(String username){
        ArrayList<String> array = new ArrayList<>();
        Connection conn = new com.cashsale.conn.Conn().getCon();
        PreparedStatement pstmt = null;
        ResultSet result = null;
        try{
            String sql = "SELECT product_id FROM product_info WHERE user_name =?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1,username);
            result = pstmt.executeQuery();
            while(result.next()){
                array.add(result.getString("product_id"));
            }
            new com.cashsale.conn.Conn().closeConn(result, pstmt, conn);
            return new ListRecommendDAO().getProductData(array);
        }catch(Exception e){
            e.printStackTrace();
        }
        new com.cashsale.conn.Conn().closeConn(result, pstmt, conn);
        return null;
    }

    /**
     * 获得username发布的商品个数
     * @param username
     * @return
     */
    public int getProductNum(String username){
        Connection conn = new com.cashsale.conn.Conn().getCon();
        PreparedStatement pstmt = null;
        ResultSet result = null;
        int number = 0;
        try{
            String sql = "SELECT COUNT(*) FROM product_info WHERE user_name = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1,username);
            result = pstmt.executeQuery();
            if(result.next()){
                number = result.getInt(1);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        new com.cashsale.conn.Conn().closeConn(result, pstmt, conn);
        return number;
    }
}
