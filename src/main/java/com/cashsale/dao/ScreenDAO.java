package com.cashsale.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * @author Sylvia
 * 2018年11月3日
 */
public class ScreenDAO {
	
	/** 每页显示的数据数目 */
	static int DATA_NUMBER = 8;
	/** 查询成功的code */
	private static final int SCREEN_SUCCESSED = 200;
	/** 没有更多数据了 */
	private static final int NO_MORE_DATA = 116;
	/** 查询失败 */
	private static final int SCREEN_FAILED = 115;
	
	/**
	 * 查询
	 * @param queryInfo
	 * 			查询语句
	 * @param strPage
	 * 			要查询的页码
	 * @return
	 */
	public Map<String, Object> search(String queryInfo, String strPage)
	{
		Map<String, Object> map = new HashMap<String, Object>();
		PreparedStatement pstmt = null;
		ResultSet result = null;
		int page = 1 ;
		map.put("code", NO_MORE_DATA);
		Connection conn = new com.cashsale.conn.Conn().getCon();
		if( strPage != null && !strPage.equals("") )
		{
			page = Integer.parseInt( strPage );
		}
		
		String query = "SELECT * FROM product_info LIMIT "+((page-1)*DATA_NUMBER)+","+DATA_NUMBER;
		if(queryInfo != null && !queryInfo.equals(""))
		{
			query = "SELECT * FROM product_info WHERE "+queryInfo+"LIMIT "+((page-1)*DATA_NUMBER)+","+DATA_NUMBER;
		}
		
		//System.out.println(query);
		
		try {
			pstmt = conn.prepareStatement(query);
			result = pstmt.executeQuery();
			ResultSetMetaData metaData = result.getMetaData();
			int columnCount = metaData.getColumnCount();
			JSONArray array = new JSONArray();
			if(!result.next()){
				map.put("code",NO_MORE_DATA);
			}
			else {
			    result.previous();
				// 遍历每一行数据
				while (result.next()) {
					JSONObject jsonObj = new JSONObject();

					// 遍历每一列
					for (int j = 1; j <= columnCount; j++) {
						String columnName = metaData.getColumnLabel(j);
						String value = result.getString(columnName);
						jsonObj.put(columnName, value);
					}
					array.add(jsonObj);
				}
				map.put("queryResult", array.toJSONString());
				map.put("code", SCREEN_SUCCESSED);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			//查询失败
			map.put("code", SCREEN_FAILED);
		}
		new com.cashsale.conn.Conn().closeConn(result, pstmt, conn);
		return map;
	}
}
