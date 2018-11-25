package com.cashsale.service;

import com.cashsale.bean.ResultDTO;
import com.cashsale.dao.TransactDAO;
import com.cashsale.enums.ResultEnum;

/**
 * @Description:交易
 * @Author: 8-0416
 * @Date: 2018/11/25
 */
public class TransactService {

    public static ResultDTO transact(int productId){
        int code = new TransactDAO().transact(productId);
        if(code == ResultEnum.TRANSACT_SUCCESS.getCode()){
            return new ResultDTO(ResultEnum.TRANSACT_SUCCESS.getCode(),null,ResultEnum.TRANSACT_SUCCESS.getMsg());
        }else{
            return new ResultDTO(ResultEnum.ERROR.getCode(),null,ResultEnum.ERROR.getMsg());
        }
    }
}
