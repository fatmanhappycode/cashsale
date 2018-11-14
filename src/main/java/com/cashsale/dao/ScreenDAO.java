package com.cashsale.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.cashsale.bean.ProductDO;

/**
 * @author Sylvia
 * 2018年11月3日
 */
public class ScreenDAO {

	/** 每页显示的数据数目 */
	static int DATA_NUMBER = 9;
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
	 * @param page
	 * 			要查询的页码
	 * @return
	 * 		map(code：状态码；page：下一页;queryResult:查询结果）
	 */
	public List<ProductDO> search(String queryInfo, int page)
	{
		// 用于存放每一件商品的信息
		List<Map<String, Object>> list = new ArrayList<>();
		List<ProductDO> rs = new ArrayList<>();
		PreparedStatement pstmt = null;
		ResultSet result = null;
		Connection conn = new com.cashsale.conn.Conn().getCon();

		String query = "SELECT * FROM product_info LIMIT "+((page-1)*DATA_NUMBER)+","+DATA_NUMBER;
		if(queryInfo != null && !queryInfo.equals(""))
		{
			query = "SELECT * FROM product_info WHERE "+queryInfo+" LIMIT "+((page-1)*DATA_NUMBER)+","+DATA_NUMBER;
		}

		System.out.println(query);

		try {
			pstmt = conn.prepareStatement(query);
			result = pstmt.executeQuery();
			ResultSetMetaData metaData = result.getMetaData();
			int columnCount = metaData.getColumnCount();
			List<ProductDO> array = null;

			// 遍历每一行数据
			while (result.next()) {
				Map<String, Object> map = new HashMap<String, Object>();

				// 遍历每一列
				for (int j = 1; j <= columnCount; j++) {
					String columnName = metaData.getColumnLabel(j);
					String value = result.getString(columnName);
					map.put(columnName, value);
					System.out.println("map.columnName="+columnName+"  map.value="+value);
				}
				list.add(map);
			}
			if (list != null) {
				for (Map<String, Object> map : list) {
					ProductDO p = new ProductDO(map);
					rs.add(p);
				}
			}
			new com.cashsale.conn.Conn().closeConn(result, pstmt, conn);
			return rs;
		} catch (Exception e) {
			e.printStackTrace();
		}
		new com.cashsale.conn.Conn().closeConn(result, pstmt, conn);
		return null;
	}
}
