package com.cashsale.dao;

import com.cashsale.bean.DemandDO;
import com.cashsale.conn.Conn;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @Description: 获取需求
 * @Author: 8-0416
 * @Date: 2018/12/8
 */
public class SearchDemandDAO {

    public DemandDO getDemand(){
        DemandDO demandDO = new DemandDO();
        Connection conn = new com.cashsale.conn.Conn().getCon();
        PreparedStatement pstmt = null;
        ResultSet result = null;
        try{
            String sql = "SELECT * FROM demand_info ORDER BY publish_time DESC LIMIT 0,1";
            pstmt = conn.prepareStatement(sql);
            result = pstmt.executeQuery();
            if(result.next()){
                demandDO.setContent(result.getString("content"));
                demandDO.setImgUrl(result.getString("img_url"));
                demandDO.setTitle(result.getString("title"));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        new Conn().closeConn(result, pstmt, conn);
        return demandDO;
    }
}
