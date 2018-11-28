package com.cashsale.dao;

import com.cashsale.bean.InteractDTO;
import com.cashsale.bean.InteractInfoDTO;
import com.cashsale.bean.PagerDTO;
import com.cashsale.conn.Conn;
import com.cashsale.enums.ResultEnum;

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
            int commentsNumber = 0;
            int like = 0;
            int share = 0;

            //评论条数
            sql = "SELECT COUNT(comments) FROM product_interaction WHERE product_id=? AND comments_time != ''";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, productId);
            result = pstmt.executeQuery();
            if(result.next()){
                commentsNumber = result.getInt(1);
            }
            interact.setCommentsNumber(commentsNumber);

            //点赞条数
            sql = "SELECT COUNT(like_time) FROM product_interaction WHERE product_id=? AND like_time != ''";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, productId);
            result = pstmt.executeQuery();
            if(result.next()){
                like = result.getInt(1);
            }
            interact.setLikeNumber(like);

            //分享条数
            sql = "SELECT COUNT(share_time) FROM product_interaction WHERE product_id=? AND share_time != ''";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, productId);
            result = pstmt.executeQuery();
            if(result.next()){
                share = result.getInt(1);
            }
            interact.setShareNumber(share);

            int temp = (page - 1)*DATA_NUMBER;

            //查询评论
            sql2 = "SELECT * FROM product_interaction WHERE product_id=? AND comments_time != '' ORDER BY comments_time DESC LIMIT ?,"+DATA_NUMBER;
            pstmt2 = conn.prepareStatement(sql2);
            pstmt2.setInt(1,productId);
            pstmt2.setInt(2,temp);
            result2 = pstmt2.executeQuery();
            List<InteractInfoDTO> list = new ArrayList<>();
            while(result2.next()){
                InteractInfoDTO interactInfo = new InteractInfoDTO();
                interactInfo.setUserName(result2.getString("user_name"));
                interactInfo.setComments(result2.getString("comments"));
                interactInfo.setCommentsTime(result2.getString("comments_time"));
                list.add(interactInfo);
            }
            interact.setComments(list);

            //查询点赞
            sql = "SELECT * FROM product_interaction WHERE product_id = ? AND like_time != '' ORDER BY like_time DESC LIMIT ?,"+DATA_NUMBER;
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1,productId);
            pstmt.setInt(2,temp);
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
            sql = "SELECT * FROM product_interaction WHERE product_id = ? AND share_time != '' ORDER BY share_time DESC LIMIT ?,"+DATA_NUMBER;
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1,productId);
            pstmt.setInt(2,temp);
            result = pstmt.executeQuery();
            List<InteractInfoDTO> list3 = new ArrayList<>();
            while(result.next()){
                InteractInfoDTO interactInfo = new InteractInfoDTO();
                interactInfo.setUserName(result.getString("user_name"));
                interactInfo.setShareTime(result.getString("share_time"));
                list3.add(interactInfo);
            }
            interact.setShare(list3);

            new Conn().closeConn(result,pstmt,conn);
            new Conn().closeConn(result2,pstmt2,conn);
            return new PagerDTO<>(page + 1, interact);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }

    }

    /**
     * 判断用户是否已点赞该商品
     * @param username
     * @param productId
     * @return
     */
    public int isLike(String username, int productId){
        Connection conn = new Conn().getCon();
        PreparedStatement pstmt = null;
        ResultSet result = null;
        String sql = "";
        try{
            sql = "SELECT is_like FROM commodity_score WHERE product_id=? AND user_name=?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1,productId);
            pstmt.setString(2,username);
            result = pstmt.executeQuery();
            if(result.next()){
                if(result.getBoolean("is_like")) {
                    new Conn().closeConn(result, pstmt, conn);
                    return ResultEnum.ALREADY_LIKE.getCode();
                }else{
                    new Conn().closeConn(result,pstmt,conn);
                    return ResultEnum.NOT_LIKE.getCode();
                }
            }else{
                new Conn().closeConn(result,pstmt,conn);
                return ResultEnum.NOT_LIKE.getCode();
            }
        }catch (Exception e){
            e.printStackTrace();
            new Conn().closeConn(result,pstmt,conn);
            return ResultEnum.NOT_LIKE.getCode();
        }
    }

}
