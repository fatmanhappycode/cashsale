package com.cashsale.service;

import com.cashsale.bean.ResultDTO;
import com.cashsale.dao.SetPasswordDAO;
import com.cashsale.enums.ResultEnum;

public class SetPasswordService {

    public ResultDTO setPassword(String username, String password){
        int code = new SetPasswordDAO().setPassword(username, password);
        if(code == ResultEnum.PASSWORD_SUCCESS.getCode()){
            return new ResultDTO(ResultEnum.PASSWORD_SUCCESS.getCode(),null,ResultEnum.PASSWORD_SUCCESS.getMsg());
        }else{
            return new ResultDTO(ResultEnum.ERROR.getCode(),null, ResultEnum.ERROR.getMsg());
        }
    }
}
