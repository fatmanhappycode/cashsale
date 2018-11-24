package com.cashsale.service;

import com.cashsale.bean.ResultDTO;
import com.cashsale.dao.RegisterDAO;
import com.cashsale.enums.ResultEnum;

public class RegisterService {
	public ResultDTO<String> UserRegister(String username, String email, String encodedPass, String encodedCode, String nickname, String vertifyCode) {
        int code = new RegisterDAO().register(username, email, encodedPass, encodedCode, nickname, vertifyCode);
        if (code == ResultEnum.ERROR.getCode()) {
            return new ResultDTO<String>(ResultEnum.ERROR.getCode(), null, ResultEnum.ERROR.getMsg());
        } else if(code == ResultEnum.REGISTER_NUMBER_ERROR.getCode()){
            return new ResultDTO<String>(ResultEnum.REGISTER_NUMBER_ERROR.getCode(), null, ResultEnum.REGISTER_NUMBER_ERROR.getMsg());
        }else if(code == ResultEnum.REGISTER_TO_MAIL.getCode()){
            return new ResultDTO<String>(ResultEnum.REGISTER_TO_MAIL.getCode(), null, ResultEnum.REGISTER_TO_MAIL.getMsg());
        }else if(code == ResultEnum.REGISTER_MAIL_ERROR.getCode()){
            return new ResultDTO<String>(ResultEnum.REGISTER_MAIL_ERROR.getCode(), null, ResultEnum.REGISTER_MAIL_ERROR.getMsg());
        }
        return null;
    }
}
