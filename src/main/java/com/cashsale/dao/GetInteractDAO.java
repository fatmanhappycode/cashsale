package com.cashsale.dao;

import com.cashsale.bean.InteractDTO;
import com.cashsale.bean.InteractInfoDTO;
import com.cashsale.bean.PagerDTO;
import com.cashsale.conn.Conn;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description: 获取评论、点赞、分享
 * @Author: 8-0416
 * @Date: 2018/11/26
 */
public class GetInteractDAO {

    /** 一页显示的评论条数 */
    private static final int DATA_NUMBER = 6;

    public PagerDTO<InteractDTO> getInteract(int page, int productId){
        InteractDTO interact = new InteractDTO();
        Connection conn = new Conn().getCon();
        PreparedStatement pstmt2 = null;
        PreparedStatement pstmt = null;
        ResultSet result2 = null;
        ResultSet result = null;
        String sql2 = "";
        String sql = "";
        try{
            sql = "SELECT COUNT(comments), COUNT(like_time), COUNT(share_time) FROM product_interaction WHERE product_id=?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, productId);
            result = pstmt.executeQuery();
            int commentsNumber = 0;
            int like = 0;
            int share = 0;
            if(result.next()){
                commentsNumber = result.getInt(1);
                like = result.getInt(2);
                share = result.getInt(3);
            }
            interact.setCommentsNumber(commentsNumber);
            interact.setLikeNumber(like);
            interact.setShareNumber(share);

            int temp = (page - 1)*DATA_NUMBER;

            //查询评论
            sql2 = "SELECT * FROM product_interaction WHERE product_id=? ORDER BY comments_time DESC LIMIT ?,"+DATA_NUMBER;
            pstmt2 = conn.prepareStatement(sql2);
            pstmt2.setInt(1,temp);
            result2 = pstmt2.executeQuery();
            List<InteractInfoDTO> list = new ArrayList<>();
            while(result2.next()){
                InteractInfoDTO interactInfo = new InteractInfoDTO();
                interactInfo.setUserName(result2.getString("user_name"));
                interactInfo.setComments(result2.getString("comments"));
                interactInfo.setCommentsTime("comments_time");
                list.add(interactInfo);
            }
            interact.setComments(list);

            //查询点赞
            sql = "SELECT * FROM product_interaction WHERE product_id = ? ORDER BY like_time DESC LIMIT ?,"+DATA_NUMBER;
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1,temp);
            result = pstmt.executeQuery();
            List<InteractInfoDTO> list2 = new ArrayList<>();
            while(result.next()){
                InteractInfoDTO interactInfo = new InteractInfoDTO();
                interactInfo.setUserName(result.getString("user_name"));
                interactInfo.setLikeTime(result.getString("like_time"));
                list2.add(interactInfo);
            }
            interact.setLike(list2);

            //查询分享
            sql = "SELECT * FROM product_interaction WHERE product_id = ? ORDER BY like_time DESC LIMIT ?,"+DATA_NUMBER;
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1,temp);
            result = pstmt.executeQuery();
            List<InteractInfoDTO> list3 = new ArrayList<>();
            while(result.next()){
                InteractInfoDTO interactInfo = new InteractInfoDTO();
                interactInfo.setUserName(result.getString("user_name"));
                interactInfo.setShareTime(result.getString("share_time"));
                list3.add(interactInfo);
            }
            interact.setLike(list3);

            new Conn().closeConn(result,pstmt,conn);
            new Conn().closeConn(result2,pstmt2,conn);
            return new PagerDTO<InteractDTO>(page+1,interact);
        }catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }
}
