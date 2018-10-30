package com.cashsale.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * 查询数据的工具
 * @author Sylvia
 * 2018年10月24日
 */
public class SearchUtil {
	
	static int DATA_NUMBER = 8;
	
	/**
	 * 查询
	 * @param query
	 * 			查询语句
	 * @param strPage
	 * 			要查询的页码
	 * @return
	 */
	public static Map<String, Object> search(String queryInfo, String strPage)
	{
		Map<String, Object> map = new HashMap<String, Object>();
		int page = 1 ;
		map.put("code", 116);
		Connection conn = new com.cashsale.conn.Conn().getCon();
		if( strPage != null && !strPage.equals("") )
		{
			page = Integer.parseInt( strPage );
		}
		
		String query = "SELECT * FROM product_info LIMIT "+((page-1)*8)+","+8;
		if(queryInfo != null && !queryInfo.equals(""))
		{
			query = "SELECT * FROM product_info WHERE "+queryInfo+"LIMIT "+((page-1)*8)+","+8;
		}
		
		//System.out.println(query);
		
		try {
			PreparedStatement pstmt = conn.prepareStatement(query);
			ResultSet result = pstmt.executeQuery();
			ResultSetMetaData metaData = result.getMetaData();
			int columnCount = metaData.getColumnCount();
			JSONArray array = new JSONArray();
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
			
		} catch (Exception e) {
			e.printStackTrace();
			//查询失败
			map.put("code", 115);
		}
		
		return map;
	}
}
