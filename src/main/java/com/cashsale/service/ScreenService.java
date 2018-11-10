package com.cashsale.service;

import java.util.Map;

import com.cashsale.bean.ResultDTO;
import com.cashsale.dao.ScreenDAO;

public class ScreenService {
	
	public ResultDTO<Object> screen(String queryInfo, String strPage){
		Map<String, Object> map = new ScreenDAO().search(queryInfo, strPage);
		int code = (int) map.get("code");
		if( code == 115 )
		{
			return new ResultDTO<Object>(115, null, "查询失败！");
		} else if (code == 200) {
			String queryResult = (String) map.get("queryResult");
			return new ResultDTO<Object>(200, queryResult, "查询成功");
		} else {
			return new ResultDTO<Object>(116, null, "没有更多数据了……");
		}
	}
}
