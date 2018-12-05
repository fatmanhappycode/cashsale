package com.cashsale.dao;

import com.cashsale.bean.DemandDO;
import com.cashsale.bean.PagerDTO;
import com.cashsale.bean.ResultDTO;
import com.cashsale.conn.Conn;
import com.cashsale.enums.ResultEnum;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @Description: 发布捐赠需求
 * @Author: 8-0416
 * @Date: 2018/12/4
 */
public class PublishDemandDAO {

    public ResultDTO publishDemand(DemandDO demandDO, String username){
        Connection conn = new Conn().getCon();
        PreparedStatement pstmt = null;
        String sql = "";

        //获取发布信息
        String title = demandDO.getTitle();
        String imgUrl = demandDO.getImgUrl();
        String content = demandDO.getContent();
        String tweetLink = demandDO.getTweetLink();
        String organization = getOrganization(username);

        try{

            sql = "INSERT INTO demand_info(user_name, title, content, organization, tweet_link, img_url) VALUE(?,?,?,?,?,?)";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1,username);
            pstmt.setString(2, title);
            pstmt.setString(3, content);
            pstmt.setString(4, organization);
            pstmt.setString(5, tweetLink);
            pstmt.setString(6, imgUrl);
            pstmt.executeUpdate();
            new Conn().closeConn(null, pstmt, conn);
            return new ResultDTO(ResultEnum.PUBLISH_SUCCESS.getCode(), null, ResultEnum.PUBLISH_SUCCESS.getMsg());
        }catch (Exception e){
            e.printStackTrace();
            new Conn().closeConn(null, pstmt, conn);
            return new ResultDTO(ResultEnum.ERROR.getCode(), null, ResultEnum.ERROR.getMsg());
        }
    }

    /**
     * 获取用户所在组织，没有返回null
     * @param username
     * @return
     */
    public String getOrganization(String username){
        Connection conn = new Conn().getCon();
        PreparedStatement pstmt = null;
        ResultSet result = null;
        String sql = "";
        String organization = null;
        try {
            sql = "SELECT organization FROM user_data WHERE user_name = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, username);
            result = pstmt.executeQuery();
            if (result.next()) {
                organization = result.getString("organization");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        new Conn().closeConn(result, pstmt, conn);
        return organization;
    }
}
