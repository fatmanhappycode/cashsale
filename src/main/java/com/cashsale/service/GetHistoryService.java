package com.cashsale.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.cashsale.dao.SocketDAO;

public class GetHistoryService {
	
	/**
	 * 返回与name用户相关的聊天记录
	 * @param name
	 * 			用户名
	 * @param strPage
	 * 			聊天记录的页数
	 * @return
	 */
	public Map<String, Object> getHistory(String name, String strPage){
		Map<String, Object> mapSum = new HashMap<String, Object>();
		ArrayList<String> array = new SocketDAO().getTable(name);
		for(int i = 0; i < array.size(); i++) {
			Map<String, Object> map = new SocketDAO().getMessage(array.get(i), strPage);
			mapSum.putAll(map);
		}
		return mapSum;
	}
}
