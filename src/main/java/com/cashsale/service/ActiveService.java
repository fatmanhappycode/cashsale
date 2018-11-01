package com.cashsale.service;

import com.cashsale.bean.Result;
import com.cashsale.dao.ActiveDAO;

public class ActiveService {
	
	public Result<String> UserActive(String vertifyCode, String currentTime, String username, String password) {
       int code = new ActiveDAO().active(vertifyCode, currentTime, username, password);
		if(code == 113)
		{
			return new Result<String>(113, null, "验证码已过期，请重新注册！");
		}
		else if(code == 101) {
			return new Result<String>(101, null, "激活成功！");
		}
		else if(code == 111) {
			return new Result<String>(111, null, "验证码有误，请重新激活！");
		}
		else if(code == 112) {
			return new Result<String>(112, null, "验证失败！");
		}
		return null;
    }
}
