package com.cashsale.service;

import com.cashsale.bean.Result;
import com.cashsale.dao.RegisterDAO;

public class RegisterService {
	public Result<String> UserRegister(String username, String email, String encodedPass, String encodedCode, String nickname,
			String vertifyCode, String password) {
        int code = new RegisterDAO().register(username, email, encodedPass, encodedCode, nickname, vertifyCode, password);
        if (code == 102) {
            return new Result<String>(102, null, "注册失败！");
        } else if(code == 103){
            return new Result<String>(103, null, "该手机号已被注册！");
        }else if(code == 109){
            return new Result<String>(109, null, "请到邮箱进行账号激活！");
        }else if(code == 110){
            return new Result<String>(110, null, "该邮箱已被注册！");
        }
        return null;
    }
}
