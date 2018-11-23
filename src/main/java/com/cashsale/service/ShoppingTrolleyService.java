/*
package com.cashsale.service;

import com.cashsale.bean.ResultDTO;
import com.cashsale.dao.AddShoppingTrolleyDAO;
import com.cashsale.dao.IsInTrolleyDAO;
import com.cashsale.dao.UpdateScoreDAO;
import com.cashsale.enums.ResultEnum;

*/
/**
 * @author 肥宅快乐码
 * @date 2018/11/15 - 15:48
 *//*

public class ShoppingTrolleyService {
    public ResultDTO<String> addShoppingTrolley(String username, String productId) {
        int pro = Integer.parseInt(productId);
        new UpdateScoreDAO().updateScore(username, pro, "C");
        if (new AddShoppingTrolleyDAO().add(username,productId)) {
            return new ResultDTO<String>(ResultEnum.ADD_TROLLEY_SUCCESS.getCode(),"1",ResultEnum.ADD_TROLLEY_SUCCESS.getMsg());
        } else {
            return new ResultDTO<String>(ResultEnum.ERROR.getCode(),"0",ResultEnum.ERROR.getMsg());
        }
    }
    public ResultDTO<String> isShoppingTrolley(String username, String productId) {
        if (new IsInTrolleyDAO().isInTrolley(username,productId)) {
            return new ResultDTO<String>(ResultEnum.ALREADY_IN_TROLLEY.getCode(),"1",ResultEnum.ALREADY_IN_TROLLEY.getMsg());
        } else {
            return new ResultDTO<String>(ResultEnum.NOT_IN_TROLLEY.getCode(),"0",ResultEnum.NOT_IN_TROLLEY.getMsg());
        }
    }
}
*/
