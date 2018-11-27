package com.cashsale.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.cashsale.conn.Conn;
import com.cashsale.enums.ResultEnum;
import com.cashsale.util.TimeUtil;

/**
 * 更新每个用户对商品的评分 and 用户信用
 * @author Sylvia
 * 2018年11月3日
 */
public class UpdateScoreDAO {

    /** 浏览商品  A */
    private static final String SCAN_CODE = "A";
    /** 询问商家 B */
    private static final String INQUIRE_SELLER_CODE = "B";
    /** 收藏商品 / 加入购物车 C */
    private static final String COLLECTION_CODE = "C";
    /** 分享商品 D */
    private static final String SHARE_CODE = "D";
    /** 点赞 E */
    private static final String LOVE_CODE = "E";
    /** 评论 F */
    private static final String COMMENT_CODE = "F";

    /**
     * 更新某用户对商品的评分 and 信用
     * @param username
     * 			用户名
     * @param strCode
     * 			判断用户是浏览/收藏/分享……了该商品
     */
    public int updateScore(String username, int productId, String strCode, String comments) {

        if(strCode.equals(SCAN_CODE)) {
            return changeScore(username, productId, 1, "is_scan");
        }
        else if(strCode.equals(INQUIRE_SELLER_CODE)) {
            return changeScore(username, productId, 2, "is_inquire");
        }
        else if(strCode.equals(COLLECTION_CODE)) {
            System.out.println(2);
            return changeScore(username, productId, 3, "is_collect");
        }
        else if(strCode.equals(SHARE_CODE)) {
            return changeScore(username, productId, 4, "is_share");
        }
        else if(strCode.equals(LOVE_CODE)){
            System.out.println("E");
            return changeScore(username, productId, 1, "is_like");
        }else if(strCode.equals(COMMENT_CODE)){
            return commentProduct(username, productId, comments);
        }
        return ResultEnum.ERROR.getCode();
    }

    /**
     * 更改用户对商品的评分的具体方法
     * @param username
     * 			用户名
     * @param productId
     * 			商品id
     * @param score
     * 			增加的分数
     * @param code
     * 			判断用户是浏览/收藏/分享……了该商品
     */
    private int changeScore(String username, int productId, int score, String code) {
        Connection conn = new com.cashsale.conn.Conn().getCon();
        PreparedStatement pstmt = null;
        PreparedStatement pstmt2 = null;
        ResultSet resutl2 = null;
        ResultSet result = null;
        String sql = "";
        String sql2 = "";
        try {
            sql = "SELECT * FROM commodity_score WHERE product_id = ? AND user_name=?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, productId);
            pstmt.setString(2,username);
            result = pstmt.executeQuery();
            if(result.next()) {
                boolean temp = result.getBoolean(code);
                int origin = result.getInt("score");
                score += origin;
                //判断用户是否对该商品执行过code的操作
                if(temp == false){
                    //更新该用户对该商品的评分
                    sql = "UPDATE commodity_score SET score="+score+" WHERE product_id = ? AND user_name=?";
                    pstmt = conn.prepareStatement(sql);
                    pstmt.setInt(1,productId);
                    pstmt.setString(2,username);
                    pstmt.execute();
                    //更新该用户对该商品的操作
                    sql2 = "UPDATE commodity_score SET "+code+"=true WHERE product_id =? AND user_name=?";
                    pstmt2 = conn.prepareStatement(sql2);
                    pstmt2.setInt(1,productId);
                    pstmt2.setString(2,username);
                    pstmt2.execute();
                    if(code.equals("is_like") || code.equals("is_share")){
                        interactProduct(username, productId, code);
                    }
                }
            }else{
                insertUser(username,productId,score,code);
            }
            new Conn().closeConn(result, pstmt, conn);
            new Conn().closeConn(resutl2, pstmt2, conn);
            return ResultEnum.SCORE_SUCCESS.getCode();
        }catch(Exception e) {
            e.printStackTrace();
            System.err.println("更改评分失败！");
            new Conn().closeConn(result, pstmt, conn);
            new Conn().closeConn(resutl2, pstmt2, conn);
            return ResultEnum.ERROR.getCode();
        }
    }

    /**
     * 评论商品
     * @param username
     *          执行者
     * @param productId
     *          商品id
     * @param comments
     *          评论内容
     * @return
     */
    private int commentProduct(String username, int productId, String comments) {
        String currentTime = new TimeUtil().getCurrentTime();
        Connection conn = new com.cashsale.conn.Conn().getCon();
        PreparedStatement pstmt = null;
        ResultSet result = null;
        String sql = "";
        try {
            sql = "INSERT INTO product_interaction(product_id, user_name, comments, commnets_time) VALUES(?,?,?,?)";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1,productId);
            pstmt.setString(2,username);
            pstmt.setString(3,comments);
            pstmt.setString(4,currentTime);
            pstmt.execute();
            new Conn().closeConn(result, pstmt, conn);
            return ResultEnum.COMMENT_SUCCESS.getCode();
        }catch(Exception e) {
            e.printStackTrace();
            System.err.println("评论商品失败！");
            new Conn().closeConn(result, pstmt, conn);
            return ResultEnum.ERROR.getCode();
        }
    }

    /**
     * 点赞、分享商品
     * @param username
     * @param productId
     * @param code
     */
    private void interactProduct(String username, int productId, String code){
        System.out.println("code="+code);
        String currentTime = new TimeUtil().getCurrentTime();
        Connection conn = new com.cashsale.conn.Conn().getCon();
        PreparedStatement pstmt2 = null;
        PreparedStatement pstmt = null;
        ResultSet result2 = null;
        ResultSet result = null;
        String sql2 = "";
        String sql = "";
        if(code.equals("is_like")){
            code = "like_time";
        }else if(code.equals("is_share")){
            code = "share_time";
        }
        try {
            sql2 = "SELECT * FROM product_interaction WHERE product_id = ? AND user_name = ?";
            pstmt2 = conn.prepareStatement(sql2);
            pstmt2.setInt(1,productId);
            pstmt2.setString(2,username);
            result2 = pstmt2.executeQuery();
            if(result2.next()){
                System.out.println("currentTime="+currentTime);
                sql = "UPDATE product_interaction SET "+code+"='"+currentTime+"' WHERE product_id =? AND user_name=?";
                pstmt = conn.prepareStatement(sql);
                pstmt.setInt(1,productId);
                pstmt.setString(2,username);
                pstmt.execute();
            }else {
                sql = "INSERT INTO product_interaction(product_id, user_name, " + code + ") VALUE(?,?,?)";
                pstmt = conn.prepareStatement(sql);
                pstmt.setInt(1, productId);
                pstmt.setString(2, username);
                pstmt.setString(3, currentTime);
                pstmt.execute();
            }
            new Conn().closeConn(result, pstmt, conn);
            new Conn().closeConn(result2, pstmt2, conn);
        }catch(Exception e) {
            e.printStackTrace();
            System.err.println("点赞或分享商品失败！");
            new Conn().closeConn(result, pstmt, conn);
            new Conn().closeConn(result2, pstmt2, conn);
        }
    }

    /**
     * 用户第一次浏览该商品，插入记录
     * @param username
     * 			用户名
     * @param productId
     * 			商品id
     */
    private void insertUser(String username, int productId, int score, String code){
        Connection conn = new com.cashsale.conn.Conn().getCon();
        PreparedStatement pstmt = null;
        ResultSet result = null;
        String sql = "";
        try{
            sql = "INSERT INTO commodity_score(user_name,product_id,score,is_scan) VALUE(?,?,?,?)";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1,username);
            pstmt.setInt(2,productId);
            pstmt.setInt(3,1);
            pstmt.setBoolean(4,true);
            pstmt.execute();
            changeScore(username,productId,score,code);
        }catch(Exception e){
            e.printStackTrace();
            System.err.println("用户浏览商品失败！");
        }
        new com.cashsale.conn.Conn().closeConn(result, pstmt, conn);
    }
}
