package com.cashsale.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.cashsale.conn.Conn;
import com.cashsale.enums.ResultEnum;

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
    /** 收藏商品 C */
    private static final String COLLECTION_CODE = "C";
    /** 分享商品 D */
    private static final String SHARE_CODE = "D";
    /** 购买商品 E */
    private static final String PURCHASE_CODE = "E";
    /** 评价商品 F */
    private static final String EVALUATE_CODE = "F";
    /** 好评 G */
    private static final String GOOD_EVALUATE_CODE = "G";
    /** 差评 H */
    private static final String BAD_EVALUATE_CODE = "H";
    /** 中评 I */
    private static final String MIDDLE_EVALUATE_CODE = "I";
    /** 分割符 */
    private static final String SEPARATOR= ";";

    /**
     * 更新某用户对商品的评分 and 信用
     * @param username
     * 			用户名
     * @param strCode
     * 			判断用户是浏览/收藏/分享……了该商品
     */
    public int updateScore(String username, int productId, String strCode) {
        String second = "";

        System.out.println(1);
        if( strCode != null && !strCode.equals("") )
        {
            if(strCode.indexOf(SEPARATOR) != -1) {
                String[] str = strCode.split(SEPARATOR);
                strCode = str[0];
                second = str[1];
            }
        }
        if(strCode.equals(SCAN_CODE)) {
            return changeScore(username, productId, 1, "is_scan");
        }
        else if(strCode.equals(INQUIRE_SELLER_CODE)) {
            return changeScore(username, productId, 2, "is_inquire");
        }
        else if(strCode.equals(COLLECTION_CODE)) {
            return changeScore(username, productId, 3, "is_collect");
        }
        else if(strCode.equals(SHARE_CODE)) {
            return changeScore(username, productId, 4, "is_share");
        }
        else if(strCode.equals(PURCHASE_CODE)) {
            return changeScore(username, productId, 5, "is_purchase");
        }
        else if(strCode.equals(EVALUATE_CODE)) {

            if(second.equals(GOOD_EVALUATE_CODE)) {
                return changeScore(username, productId, 3, "evaluate","good");
            }
            else if(second.equals(BAD_EVALUATE_CODE)) {
                return changeScore(username, productId, -3, "evaluate","bad");
            }
            else if(second.equals(MIDDLE_EVALUATE_CODE)) {
                return changeScore(username, productId, 0, "evaluate","middle");
            }
        }
        return ResultEnum.ERROR.getCode();
    }

    /**
     * 更改用户对商品的评分的具体方法（除评价外）
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
        System.out.println(3);
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
                    pstmt2 = conn.prepareStatement(sql);
                    pstmt2.setInt(1,productId);
                    pstmt2.setString(2,username);
                    pstmt2.execute();
                    // 若用户对商品执行的操作为：购买，则增加用户信用,5分
                    if(code.equals("is_purchase")){
                        changeScore(username,5);
                    }
                }
            }else{
                insertUser(username,productId);
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
     * 更改用户对商品的评分（仅限评价操作）
     * @param username
     * 			用户名
     * @param productId
     * 			商品id
     * @param score
     * 			修改的分数
     * @param code
     * 			执行的操作
     * @param code2
     * 			好评/中评/差评
     * @return
     *          更新数据库结果代码
     */
    private int changeScore(String username, int productId, int score, String code, String code2) {
        Connection conn = new com.cashsale.conn.Conn().getCon();
        PreparedStatement pstmt = null;
        ResultSet result = null;
        String sql = "";
        try {
            sql = "SELECT evaluate,score FROM commodity_score WHERE product_id = ? AND user_name=?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, productId);
            pstmt.setString(2,username);
            result = pstmt.executeQuery();
            if(result.next()){
                int origin = result.getInt("score");
                score += origin;
                sql = "UPDATE commodity_score SET evaluate=?, score=? WHERE product_id=? AND user_name=?";
                pstmt = conn.prepareStatement(sql);
                pstmt.setString(1,code2);
                pstmt.setInt(2,score);
                pstmt.setInt(3,productId);
                pstmt.setString(4,username);
                pstmt.execute();
            }
            new Conn().closeConn(result, pstmt, conn);
            return ResultEnum.SCORE_SUCCESS.getCode();
        }catch(Exception e) {
            e.printStackTrace();
            System.err.println("更改评分失败！");
            new Conn().closeConn(result, pstmt, conn);
            return ResultEnum.ERROR.getCode();
        }
    }

    /**
     * 更新用户信用的具体方法
     * @param score
     *          修改的信用
     */
    private int changeScore(String username, int score) {
        Connection conn = new com.cashsale.conn.Conn().getCon();
        PreparedStatement pstmt = null;
        ResultSet result = null;
        String sql = "";
        try {
            sql = "SELECT credit FROM user_data WHERE user_name=?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1,username);
            result = pstmt.executeQuery();
            if(result.next()){
                int origin = result.getInt("credit");
                score += origin;
                sql = "UPDATE user_data SET credit=? WHERE user_name=?";
                pstmt = conn.prepareStatement(sql);
                pstmt.setInt(1,score);
                pstmt.setString(2,username);
                pstmt.execute();
            }
            new Conn().closeConn(result, pstmt, conn);
            return ResultEnum.SCORE_SUCCESS.getCode();
        }catch(Exception e) {
            e.printStackTrace();
            System.err.println("更改评分失败！");
            new Conn().closeConn(result, pstmt, conn);
            return ResultEnum.ERROR.getCode();
        }
    }

    /**
     * 用户第一次该商品，插入记录
     * @param newUsername
     * 			用户名
     * @param productId
     * 			商品id
     */
    private void insertUser(String newUsername, int productId){
        Connection conn = new com.cashsale.conn.Conn().getCon();
        PreparedStatement pstmt = null;
        ResultSet result = null;
        String sql = "";
        try{
            sql = "INSERT INTO commodity_score(user_name,product_id,score,is_scan) VALUE(?,?,?,?)";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1,newUsername);
            pstmt.setInt(2,productId);
            pstmt.setInt(3,1);
            pstmt.setBoolean(4,true);
            result = pstmt.executeQuery();
        }catch(Exception e){
            e.printStackTrace();
            System.err.println("用户浏览商品失败！");
        }
        new com.cashsale.conn.Conn().closeConn(result, pstmt, conn);
    }
}
