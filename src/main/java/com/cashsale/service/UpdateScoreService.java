package com.cashsale.service;

import com.cashsale.bean.ResultDTO;
import com.cashsale.dao.IsInTrolleyDAO;
import com.cashsale.dao.UpdateScoreDAO;
import com.cashsale.enums.ResultEnum;

/**
 * @author Sylvia
 * 2018年11月3日
 */
public class UpdateScoreService {

	public ResultDTO<String> updateScore(String username, int productId, String strCode){
		int code = new UpdateScoreDAO().updateScore(username, productId, strCode);
		if( code == ResultEnum.ERROR.getCode())
		{
			return new ResultDTO<String>(ResultEnum.ERROR.getCode(), null, ResultEnum.ERROR.getMsg());
		} else if (code == ResultEnum.SCORE_SUCCESS.getCode()) {
			return new ResultDTO<String>(ResultEnum.SCORE_SUCCESS.getCode(), null, ResultEnum.SCORE_SUCCESS.getMsg());
		}
		return null;
	}

	public ResultDTO<String> isShoppingTrolley(String username, String productId) {
		if (new IsInTrolleyDAO().isInTrolley(username,productId)) {
			return new ResultDTO<String>(ResultEnum.ALREADY_IN_TROLLEY.getCode(),"1",ResultEnum.ALREADY_IN_TROLLEY.getMsg());
		} else {
			return new ResultDTO<String>(ResultEnum.NOT_IN_TROLLEY.getCode(),"0",ResultEnum.NOT_IN_TROLLEY.getMsg());
		}
	}
}
