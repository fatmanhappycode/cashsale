package com.cashsale.service;

import java.util.Map;

import com.cashsale.bean.Result;
import com.cashsale.dao.ScreenDAO;

public class ScreenService {
	
	public Result<Object> screen(String queryInfo, String strPage){
		Map<String, Object> map = new ScreenDAO().search(queryInfo, strPage);
		int code = (int) map.get("code");
		if( code == 115 )
		{
			return new Result<Object>(115, null, "查询失败！");
		} else if (code == 200) {
			String queryResult = (String) map.get("queryResult");
			return new Result<Object>(200, null, queryResult);
		} else {
			return new Result<Object>(116, null, "没有更多数据了……");
		}
	}
}
