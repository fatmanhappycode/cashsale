package com.cashsale.dao;

import com.cashsale.bean.ProductDO;
import com.cashsale.conn.Conn;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * 获得推荐商品
 * @author Sylvia
 * 2018年11月4日
 */
public class ListRecommendDAO {

	private static boolean open = true;

	/**
	 * 获得用户和商品评分的矩阵
	 * @param username
	 * 			用户名
	 * @return
	 * 			用户和商品评分的矩阵
	 */
	public Map<String, Map<String, Integer>> getUserScore(String username){
		Connection conn = new com.cashsale.conn.Conn().getCon();
		PreparedStatement pstmt = null;
		ResultSet result = null;
		Map<String, Map<String, Integer>> userPerfMap = new HashMap<String, Map<String, Integer>>();
		try {
			//找出用户名为username的用户所有浏览、分享……过（即对商品评分不为0）的商品id以及对其的评分
			String sql = "SELECT product_id,score FROM commodity_score WHERE user_name=? AND score != 0";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1,username);
			result = pstmt.executeQuery();
			Map<String, Integer> pref = new HashMap<String, Integer>();

			while(result.next()){
				String productId = result.getString("product_id");
				int score = result.getInt("score");
				pref.put(productId, score);

			}
			//获得其它用户，并把其它用户对 要推荐的用户所浏览/购买/分享……过的商品 的评分加入userPerMap中
			Map<String, Map<String, Integer>> userPerfMap2 = getOther(username);
			userPerfMap.putAll(userPerfMap2);
			//把要推荐的用户和他/她对其它商品的评分加入userPerMap
			userPerfMap.put(username, pref);
		}catch(Exception e) {
			e.printStackTrace();
		}
		new com.cashsale.conn.Conn().closeConn(result, pstmt, conn);
		return userPerfMap;
	}

	/**
	 * 获得其它用户
	 * @param username
	 * 			被推荐用户
	 * @return
	 */
	public Map<String, Map<String, Integer>> getOther(String username){
		Connection conn = new com.cashsale.conn.Conn().getCon();
		PreparedStatement pstmt = null;
		PreparedStatement pstmt2 = null;
		ResultSet result2 = null;
		ResultSet result = null;
		Map<String, Map<String, Integer>> userPerfMap = new HashMap<String, Map<String, Integer>>();
		try {

			//查找出所有与被推荐用户有共同浏览的商品的用户名
			String sql2 = "SELECT DISTINCT user_name FROM commodity_score WHERE product_id IN (" +
					"SELECT product_id FROM commodity_score WHERE user_name=? AND score != 0) AND user_name != ?";
			pstmt2 = conn.prepareStatement(sql2);
			pstmt2.setString(1,username);
			pstmt2.setString(2,username);
			result2 = pstmt2.executeQuery();

			//分别获得每一个用户对被推荐用户所浏览过的商品的id和评分
			while(result2.next()) {
				Map<String, Integer> pref = new HashMap<String, Integer>();
				String sql = "SELECT * FROM commodity_score WHERE product_id IN (" +
						"SELECT product_id FROM commodity_score WHERE user_name=? AND score != 0) AND user_name = ?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, username);
				pstmt.setString(2, result2.getString("user_name"));
				result = pstmt.executeQuery();

				while (result.next()) {
					String productId = result.getString("product_id");
					int score = result.getInt("score");
					pref.put(productId,score);
				}

				userPerfMap.put(result2.getString("user_name"), pref);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.err.println("获得其它用户失败！");
		}
		new com.cashsale.conn.Conn().closeConn(result, pstmt, conn);
		new com.cashsale.conn.Conn().closeConn(result2, pstmt2, conn);
		return userPerfMap;
	}


	/**
	 * 获得相似用户和他浏览/分享……过的商品<br>
	 * (被推荐用户没有浏览/分享……过的商品)
	 * @param username
	 * 			被推荐用户名
	 * @param enList
	 * 			相似用户列表
	 * @return
	 */
	public Map<String, Map<String, Integer>> getSimUserObjMap(String username, List<Entry<String, Double>> enList){
		Map<String, Map<String, Integer>> simUserObjMap = new HashMap<String, Map<String, Integer>>();
		Connection conn = new com.cashsale.conn.Conn().getCon();
		PreparedStatement pstmt = null;
		ResultSet result = null;
		int j = 0;
		try {
			for(int i = enList.size() - 1; i >= 0 ; i--) {
				Map<String, Integer> map = new HashMap<String, Integer>();
				String sql = "SELECT product_id,score FROM commodity_score WHERE"
						+ " product_id NOT IN (SELECT product_id FROM commodity_score WHERE user_name=? AND score != 0) AND user_name = ?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1,username);
				pstmt.setString(2,enList.get(i).getKey());
				result = pstmt.executeQuery();
				while(result.next()) {
					map.put(result.getString("product_id"), result.getInt("score"));
				}
				simUserObjMap.put(enList.get(i).getKey(), map);
				j++;
			}
		}catch(Exception e) {
			e.printStackTrace();
			System.err.println();
		}
		// 关闭连接
		return simUserObjMap;
	}

	/**
	 * 获取随机推荐商品的id
	 * @param username
	 *      被推荐用户
	 * @return
	 */
	/*public ArrayList<String> getRandom(String username){
		Connection conn = new Conn().getCon();
		ArrayList<String> array = new ArrayList<String>();
		PreparedStatement pstmt = null;
		ResultSet result = null;
		try{
			String sql = "SELECT product_id FROM commodity_score WHERE product_id NOT IN ( SELECT product_id FROM commodity_score WHERE user_name=?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1,username);
			result = pstmt.executeQuery();
			while(result.next()){
				array.add(result.getString("product_id"));
			}
		}catch(Exception e){
			e.printStackTrace();
			System.err.println("获取随机推荐商品失败！");
		}
		new com.cashsale.conn.Conn().closeConn(result, pstmt, conn);
		return array;
	}*/

	/**
	 * 获得商品的详细信息
	 * @param array
	 *         被推荐商品id列表
	 * @return
	 *         被推荐商品的详细信息列表
	 */
	public List<ProductDO> getProductData(ArrayList<String> array){
		Connection conn = new com.cashsale.conn.Conn().getCon();
		// 用于存放每一件商品的信息
		List<Map<String, Object>> list = new ArrayList<>();
		//所有商品信息的列表
		List<ProductDO> listPro = new ArrayList<>();
		PreparedStatement pstmt = null;
		ResultSet result = null;
		String sql = "";
		try{
			for(int i = 0; i < array.size(); i++){
				sql = "SELECT * FROM product_info WHERE product_id =?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1,array.get(i));
				result = pstmt.executeQuery();
				ResultSetMetaData metaData = result.getMetaData();
				int columnCount = metaData.getColumnCount();

				// 遍历每一行数据
				while (result.next()) {
					Map<String, Object> map = new HashMap<String, Object>();

					// 遍历每一列
					for (int j = 1; j <= columnCount; j++) {
						String columnName = metaData.getColumnLabel(j);
						String value = result.getString(columnName);
						map.put(columnName, value);
					}
					list.add(map);
				}
			}
			if (list != null) {
				for (Map<String, Object> map : list) {
					ProductDO p = new ProductDO(map);
					listPro.add(p);
				}
			}
			new com.cashsale.conn.Conn().closeConn(result, pstmt, conn);
			return listPro;
		}catch (Exception e){
			e.printStackTrace();
		}
		new com.cashsale.conn.Conn().closeConn(result, pstmt, conn);
		return null;
	}
}
