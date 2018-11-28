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
        try{
            //评论条数
            int commentsNumber = getCommentNumber(productId);
            interact.setCommentsNumber(commentsNumber);

            //点赞条数
            int like = getLikeNumber(productId);
            interact.setLikeNumber(like);

            //分享条数
            int share = getShareNumber(productId);
            interact.setShareNumber(share);

            int temp = (page - 1)*DATA_NUMBER;

            //查询评论
            List<InteractInfoDTO> list = getComments(productId, temp);
            interact.setComments(list);

            //查询点赞
            list = getLike(productId, temp);
            interact.setLike(list);

            //查询分享
            list = getShare(productId, temp);
            interact.setShare(list);

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

    /**
     * 获取商品的评论数目
     * @param productId
     * @return
     */
    public int getCommentNumber(int productId){
        Connection conn = new Conn().getCon();
        PreparedStatement pstmt = null;
        ResultSet result = null;
        String sql = "";
        int commentsNumber = 0;
        try {
            sql = "SELECT COUNT(comments) FROM product_interaction WHERE product_id=? AND comments_time != ''";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, productId);
            result = pstmt.executeQuery();
            if(result.next()){
                commentsNumber = result.getInt(1);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        new Conn().closeConn(result,pstmt,conn);
        return commentsNumber;
    }

    /**
     * 查询评论的具体内容
     * @param productId
     * @param temp
     * @return
     */
    public List<InteractInfoDTO> getComments(int productId, int temp){
        List<InteractInfoDTO> list = new ArrayList<>();
        Connection conn = new Conn().getCon();
        PreparedStatement pstmt = null;
        ResultSet result = null;
        String sql = "";
        try{
            sql = "SELECT * FROM product_interaction WHERE product_id=? AND comments_time != '' ORDER BY comments_time DESC LIMIT ?,"+DATA_NUMBER;
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1,productId);
            pstmt.setInt(2,temp);
            result = pstmt.executeQuery();
            while(result.next()){
                InteractInfoDTO interactInfo = new InteractInfoDTO();
                interactInfo.setUserName(result.getString("user_name"));
                interactInfo.setComments(result.getString("comments"));
                interactInfo.setCommentsTime(result.getString("comments_time"));
                list.add(interactInfo);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        new Conn().closeConn(result,pstmt,conn);
        return list;
    }

    /**
     * 获取商品的点赞数
     * @param productId
     * @return
     */
    public int getLikeNumber(int productId){
        Connection conn = new Conn().getCon();
        PreparedStatement pstmt = null;
        ResultSet result = null;
        String sql = "";
        int like = 0;
        try {
            sql = "SELECT COUNT(like_time) FROM product_interaction WHERE product_id=? AND like_time != ''";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, productId);
            result = pstmt.executeQuery();
            if(result.next()){
                like = result.getInt(1);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        new Conn().closeConn(result,pstmt,conn);
        return like;
    }

    /**
     * 查询点赞的具体内容
     * @param productId
     * @param temp
     * @return
     */
    public List<InteractInfoDTO> getLike(int productId, int temp){
        List<InteractInfoDTO> list = new ArrayList<>();
        Connection conn = new Conn().getCon();
        PreparedStatement pstmt = null;
        ResultSet result = null;
        String sql = "";
        try{
            sql = "SELECT * FROM product_interaction WHERE product_id = ? AND like_time != '' ORDER BY like_time DESC LIMIT ?,"+DATA_NUMBER;
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1,productId);
            pstmt.setInt(2,temp);
            result = pstmt.executeQuery();
            while(result.next()){
                InteractInfoDTO interactInfo = new InteractInfoDTO();
                interactInfo.setUserName(result.getString("user_name"));
                interactInfo.setLikeTime(result.getString("like_time"));
                list.add(interactInfo);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        new Conn().closeConn(result,pstmt,conn);
        return list;
    }

    /**
     * 获取商品的分享数
     * @param productId
     * @return
     */
    public int getShareNumber(int productId){
        Connection conn = new Conn().getCon();
        PreparedStatement pstmt = null;
        ResultSet result = null;
        String sql = "";
        int share = 0;
        try {
            sql = "SELECT COUNT(share_time) FROM product_interaction WHERE product_id=? AND share_time != ''";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, productId);
            result = pstmt.executeQuery();
            if(result.next()){
                share = result.getInt(1);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        new Conn().closeConn(result,pstmt,conn);
        return share;
    }

    /**
     * 查询分享的具体内容
     * @param productId
     * @param temp
     * @return
     */
    public List<InteractInfoDTO> getShare(int productId, int temp){
        List<InteractInfoDTO> list = new ArrayList<>();
        Connection conn = new Conn().getCon();
        PreparedStatement pstmt = null;
        ResultSet result = null;
        String sql = "";
        try{
            sql = "SELECT * FROM product_interaction WHERE product_id = ? AND share_time != '' ORDER BY share_time DESC LIMIT ?,"+DATA_NUMBER;
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1,productId);
            pstmt.setInt(2,temp);
            result = pstmt.executeQuery();
            while(result.next()){
                InteractInfoDTO interactInfo = new InteractInfoDTO();
                interactInfo.setUserName(result.getString("user_name"));
                interactInfo.setShareTime(result.getString("share_time"));
                list.add(interactInfo);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        new Conn().closeConn(result,pstmt,conn);
        return list;
    }
    
}
