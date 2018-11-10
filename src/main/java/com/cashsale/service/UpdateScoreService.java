package com.cashsale.service;

import com.cashsale.bean.ResultDTO;
import com.cashsale.dao.UpdateScoreDAO;

/**
 * @author Sylvia
 * 2018年11月3日
 */
public class UpdateScoreService {
	public ResultDTO<String> updateScore(String username, String strProductId, String strCode){
		int code = new UpdateScoreDAO().updateScore(username, strProductId, strCode);
		if( code == 404 )
		{
			return new ResultDTO<String>(404, null, "更新错误！");
		} else if (code == 200) {
			return new ResultDTO<String>(200, null, "更新成功！");
		}
		return null;
	}
}
