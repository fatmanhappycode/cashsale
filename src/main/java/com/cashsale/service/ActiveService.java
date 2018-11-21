package com.cashsale.service;

import com.cashsale.bean.ResultDTO;
import com.cashsale.dao.ActiveDAO;
import com.cashsale.enums.ResultEnum;

public class ActiveService {
	
	public ResultDTO<String> UserActive(String vertifyCode, String currentTime, String username, String password) {
       int code = new ActiveDAO().active(vertifyCode, currentTime, username, password);
		if(code == ResultEnum.ACTIVE_TIME_ERROR.getCode())
		{
			return new ResultDTO<String>(ResultEnum.ACTIVE_TIME_ERROR.getCode(), null, ResultEnum.ACTIVE_TIME_ERROR.getMsg());
		}
		else if(code == ResultEnum.ACTIVE_SUCCESS.getCode()) {
			return new ResultDTO<String>(ResultEnum.ACTIVE_SUCCESS.getCode(), null, ResultEnum.ACTIVE_SUCCESS.getMsg());
		}
		else if(code == ResultEnum.ACTIVE_ERROR.getCode()) {
			return new ResultDTO<String>(ResultEnum.ACTIVE_ERROR.getCode(), null, ResultEnum.ACTIVE_ERROR.getMsg());
		}
		else if(code == ResultEnum.ERROR.getCode()) {
			return new ResultDTO<String>(ResultEnum.ERROR.getCode(), null, ResultEnum.ERROR.getMsg());
		}
		return null;
    }
}
