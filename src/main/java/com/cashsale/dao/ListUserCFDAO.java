package com.cashsale.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * @author Sylvia
 * 2018年11月4日
 */
public class ListUserCFDAO {
	
	private static boolean open = true;
	
	/**
	 * 获得用户和商品评分的矩阵
	 * @param username
	 * 			用户名
	 * @return
	 * 			用户和商品评分的矩阵
	 */
	public Map<String, Map<String, Integer>> getUserScore(String username){
		ResultSet result = null;
		ResultSet result2 = null;
		username = "a" + username;
		PreparedStatement pstmt = null;
		PreparedStatement pstmt2 = null;
		Connection conn = new com.cashsale.conn.Conn().getCon();
		Map<String, Map<String, Integer>> userPerfMap = new HashMap<String, Map<String, Integer>>();
		try {
			String sql = "SELECT product_id," + username + " FROM commodity_score WHERE " + username + " LIKE '%A%'";
			pstmt = conn.prepareStatement(sql);
			result = pstmt.executeQuery();
			Map<String, Integer> pref = new HashMap<String, Integer>();
			
			while(result.next()){
				String productId = result.getString("product_id");
				int score = getScore(result.getString(username));
				pref.put(productId, score);
				
				String sql2 = "SELECT * FROM commodity WHERE product_id = ?";
				pstmt2 = conn.prepareStatement(sql2);
				pstmt2.setString(1, productId);
				result2 = pstmt2.executeQuery();
				
				ResultSetMetaData data = result2.getMetaData();
				//获得所有列的数目
				int columnCount = data.getColumnCount();
				if(open) {
					//获得其它用户，并把其它用户对 要推荐的用户所浏览/购买/分享……过的商品 的评分加入userPerMap中
					Map<String, Map<String, Integer>> userPerfMap2 = getOther(data, columnCount, username);
					userPerfMap.putAll(userPerfMap2);
					open = false;
				}

			}
			//把要推荐的用户和他/她对其它商品的评分加入userPerMap
			userPerfMap.put("1", pref);
		}catch(Exception e) {
			e.printStackTrace();
		}
		new com.cashsale.conn.Conn().closeConn(result, pstmt, conn);
		new com.cashsale.conn.Conn().closeConn(result2, pstmt2, conn);
		return userPerfMap;
	}
	
	/**
	 * 获得其它用户
	 * @param data
	 * @param columnCount
	 * @param username
	 * @return
	 */
	public Map<String, Map<String, Integer>> getOther(ResultSetMetaData data, int columnCount, String username){
		Connection conn = new com.cashsale.conn.Conn().getCon();
		ResultSet result = null;
		PreparedStatement pstmt = null;
		Map<String, Map<String, Integer>> userPerfMap = new HashMap<String, Map<String, Integer>>();
		try {
			for (int i = 2; i <= columnCount; i++) {
				// 获取指定列的列名
				String columnName = data.getColumnName(i);
				Map<String, Integer> pref = new HashMap<String, Integer>();
				// 获得的用户不为需要推荐的用户
				if (!columnName.equals(username)) {
					String sql = "SELECT product_id," + columnName + " FROM commodity_score WHERE product_id IN "
							+ "(SELECT product_id FROM commodity_score WHERE " + username + " LIKE '%A%' )";
					pstmt = conn.prepareStatement(sql);
					result = pstmt.executeQuery();
					while (result.next()) {
						String productId = result.getString("product_id");
						int score = getScore(result.getString(columnName));
						pref.put(productId, score);
					}
					userPerfMap.put(i+"", pref);
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.err.println("获得其它用户失败！");
		}
		new com.cashsale.conn.Conn().closeConn(result, pstmt, conn);
		return userPerfMap;
	}
	
	/**
	 * 获得该商品的评分
	 * @param username
	 * 			评分的用户
	 * @return
	 */
	public int getScore(String username) {
		String[] temp = username.split(";");
		return Integer.parseInt(temp[0]);
	}
	
	/**
	 * 获得相似用户和他浏览/分享……过的商品<br>
	 * (被推荐用户没有浏览/分享……过的商品)
	 * @param username
	 * @param enList
	 * @return
	 */
	public Map<String, Map<String, Integer>> getSimUserObjMap(String username, List<Entry<String, Double>> enList){
		Map<String, Map<String, Integer>> simUserObjMap = new HashMap<String, Map<String, Integer>>();
		Connection conn = new com.cashsale.conn.Conn().getCon();
		PreparedStatement pstmt = null;
		ResultSet result = null;
		int j = 0;
		try {
			for(int i = enList.size() - 1; i >= 0 && j < 5; i++) {
				Map<String, Integer> map = new HashMap<String, Integer>();
				String sql = "SELECT product_id," + enList.get(i).getKey() + " FROM commodity_score WHERE"
					+ " product_id NOT IN (SELECT product_id FROM commodity_score WHERE " + username + 
					" LIKE '%A%' )";
				pstmt = conn.prepareStatement(sql);
				result = pstmt.executeQuery();
				while(result.next()) {
					map.put(result.getString(1), getScore(result.getString(2)));
				}
				simUserObjMap.put(enList.get(i).getKey(), map);
				j++; 
			}
		}catch(Exception e) {
			e.printStackTrace();
			System.err.println();
		}
		new com.cashsale.conn.Conn().closeConn(result, pstmt, conn);
		return simUserObjMap;
	}
}
