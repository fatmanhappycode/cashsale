package com.cashsale.dao;

import com.cashsale.bean.DemandDO;
import com.cashsale.bean.ResultDTO;
import com.cashsale.conn.Conn;
import com.cashsale.enums.ResultEnum;

import java.sql.Connection;
import java.sql.PreparedStatement;

/**
 * @Description: 发布需求
 * @Author: 8-0416
 * @Date: 2018/12/3
 */
public class PublishDemandDAO {

    public ResultDTO<String> publishDemand(DemandDO demand, String username){
        String title = demand.getTitle();
        String label = demand.getLabel();
        double price = demand.getPrice();
        int tradeMethod = demand.getTradeMethod();
        int isBargain = demand.getIsBargain();
        String demandDescription = demand.getDemandDescription();
        String imageUrl = demand.getImageUrl();

        Connection conn = new com.cashsale.conn.Conn().getCon();
        PreparedStatement pstmt = null;

        try{
            pstmt = conn.prepareStatement("INSERT INTO demand_info(title, label, price, trade_method, is_bargain, demand_description, image_url,user_name) VALUES (?,?,?,?,?,?,?,?)");
            pstmt.setString(1,title);
            pstmt.setString(2,label);
            pstmt.setDouble(3,price);
            pstmt.setInt(4,tradeMethod);
            pstmt.setInt(5,isBargain);
            pstmt.setString(6,demandDescription);
            pstmt.setString(7,imageUrl);
            pstmt.setString(8,username);
            pstmt.executeUpdate();
            new Conn().closeConn(null, pstmt, conn);
            return new ResultDTO<String>(ResultEnum.PUBLISH_SUCCESS.getCode(),null,ResultEnum.PUBLISH_SUCCESS.getMsg());
        } catch (Exception e) {
            e.printStackTrace();
            new Conn().closeConn(null, pstmt, conn);
            return new ResultDTO<String>(ResultEnum.ERROR.getCode(),null,ResultEnum.ERROR.getMsg());
        }
    }
}
