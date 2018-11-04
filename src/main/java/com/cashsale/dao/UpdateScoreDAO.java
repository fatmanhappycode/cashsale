package com.cashsale.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.cashsale.conn.Conn;

/**
 * 更新每个用户对商品的评分 and 用户信用
 * @author Sylvia
 * 2018年11月3日
 */
public class UpdateScoreDAO {
	
	/** 浏览商品  A */
	//private static final int SCAN_SCORE = 1;
	private static final String SCAN_CODE = "A";
	/** 询问商家 B */
	//private static final int INQUIRE_SELLER_SCORE = 2;
	private static final String INQUIRE_SELLER_CODE = "B";
	/** 收藏商品 C */
	//private static final int COLLECTION_SCORE = 3;
	private static final String COLLECTION_CODE = "C";
	/** 分享商品 D */
	//private static final int SHARE_SCORE = 4;
	private static final String SHARE_CODE = "D";
	/** 购买商品 E */
	//private static final int PURCHASE_SCORE = 5;
	private static final String PURCHASE_CODE = "E";
	/** 评价商品 F */
	//private static final int EVALUATE_SCORE = 6;
	private static final String EVALUATE_CODE = "F";
	/** 好评 G */
	//private static final int GOOD_EVALUATE_SCORE = 7;
	private static final String GOOD_EVALUATE_CODE = "G";
	/** 差评 H */
	//private static final int BAD_EVALUATE_SCORE = -3;
	private static final String BAD_EVALUATE_CODE = "H";
	/** 中评 I */
	//private static final int MIDDLE_EVALUATE_SCORE = 0;
	private static final String MIDDLE_EVALUATE_CODE = "I";
	/** 分割符 */
	private static final String SEPARATOR= ";";
	/** 更新错误code */
	private static final int UPDATE_WRONG = 404;
	/** 更新成功的code */
	private static final int UPDATE_SUCCESSED = 200;
	
	/**
	 * 更新某用户对商品的评分 and 信用
	 * @param username
	 * @param productId
	 * @param strCode
	 * 			判断用户是浏览/收藏/分享……了该商品
	 */
	public int updateScore(String username, String strProductId, String strCode) {
		/** 评价商品传值为 6;7 or 6;-3 or 6;0 */
		String second = "";
		username = "a"+username;
		
		if( strCode != null && !strCode.equals("") )
		{
			if(strCode.indexOf(SEPARATOR) != -1) {
				String[] str = strCode.split(SEPARATOR);
				strCode = str[0];
				second = str[1];
			}
		}
		int productId = Integer.parseInt(strProductId);
		if(strCode == SCAN_CODE) {
			return changeScore(username, productId, 1, SCAN_CODE);
		}
		else if(strCode == INQUIRE_SELLER_CODE) {
			return changeScore(username, productId, 2, INQUIRE_SELLER_CODE);
		}
		else if(strCode == COLLECTION_CODE) {
			return changeScore(username, productId, 3, COLLECTION_CODE);
		}
		else if(strCode == SHARE_CODE) {
			return changeScore(username, productId, 4, SHARE_CODE);
		}
		else if(strCode == PURCHASE_CODE) {
			return changeScore(username, productId, 5, PURCHASE_CODE);
		}
		else if(strCode == EVALUATE_CODE) {

			if(second == GOOD_EVALUATE_CODE) {
				return changeScore(username, productId, 3, GOOD_EVALUATE_CODE);
			}
			else if(second == BAD_EVALUATE_CODE) {
				return changeScore(username, productId, -3, BAD_EVALUATE_CODE);
			}
			else if(second == MIDDLE_EVALUATE_CODE) {
				return changeScore(username, productId, 0, MIDDLE_EVALUATE_CODE);
			}
		}
		return UPDATE_WRONG;
	}
	
	/**
	 * 更改用户对商品的评分的具体方法
	 * @param username
	 * @param productId
	 * @param score
	 * 			增加的分数
	 * @param code
	 * 			判断用户是浏览/收藏/分享……了该商品
	 */
	public int changeScore(String username, int productId, int score, String code) {
		Connection conn = new com.cashsale.conn.Conn().getCon();
		PreparedStatement pstmt = null;
		ResultSet result = null;
		try {
			String sql = "SELECT " + username + " FROM commodity_score WHERE product_id = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, productId);
			result = pstmt.executeQuery();
			if(result.next()) {
				String temp = result.getString(username);
				//判断用户是否已浏览/收藏/购买……过该商品
				if(temp.indexOf(code) == -1) {
					//增加用户信用
					changeScore(score, code);
					
					String[] strScore = temp.split(SEPARATOR,2);
					int newScore = Integer.parseInt(strScore[0]);
					score += newScore;
					if(strScore.length > 1) {
						temp = score + strScore[1];
					}else {
						temp = String.valueOf(score);
					}
					System.out.println(temp);
				}
			}
			sql = "UPDATE commodity_score SET " + username + " WHERE product_id = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, productId);
			pstmt.execute();
			new Conn().closeConn(result, pstmt, conn);
			return UPDATE_SUCCESSED;
		}catch(Exception e) {
			e.printStackTrace();
			System.err.println("更改评分失败！");
			new Conn().closeConn(result, pstmt, conn);
			return UPDATE_WRONG;
		}
	}
	
	/**
	 * 更新用户信用的具体方法
	 * @param score
	 */
	public int changeScore(int score, String code) {
		//若code = 7/-3/0，均加EVALUATE_SCORE，其它就加score;
		return UPDATE_WRONG;
	}
}
