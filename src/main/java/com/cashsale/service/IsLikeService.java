package com.cashsale.service;

import com.cashsale.bean.ResultDTO;
import com.cashsale.dao.GetInteractDAO;
import com.cashsale.enums.ResultEnum;

/**
 * @Description: 判断用户是否已点赞该商品
 * @Author: 8-0416
 * @Date: 2018/11/28
 */
public class IsLikeService {

    public ResultDTO isLike(String username, int productId){
        int code = new GetInteractDAO().isLike(username, productId);
        if(code == ResultEnum.ALREADY_LIKE.getCode()){
            return new ResultDTO(ResultEnum.ALREADY_LIKE.getCode(), null, ResultEnum.ALREADY_LIKE.getMsg());
        }else if(code == ResultEnum.NOT_LIKE.getCode()){
            return new ResultDTO(ResultEnum.NOT_LIKE.getCode(), null, ResultEnum.NOT_LIKE.getMsg());
        }else{
            return null;
        }
    }
}
