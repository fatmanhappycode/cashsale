package com.cashsale.service;

import com.cashsale.bean.ResultDTO;
import com.cashsale.dao.AddShoppingTrolleyDAO;
import com.cashsale.dao.IsInTrolleyDAO;
import com.cashsale.dao.UpdateScoreDAO;

/**
 * @author 肥宅快乐码
 * @date 2018/11/15 - 15:48
 */
public class ShoppingTrolleyService {
    public ResultDTO<String> addShoppingTrolley(String username, String productId) {
        int pro = Integer.parseInt(productId);
        new UpdateScoreDAO().updateScore(username, pro, "C");
        if (new AddShoppingTrolleyDAO().add(username,productId)) {
            return new ResultDTO<String>(101,"1","成功加入购物车");
        } else {
            return new ResultDTO<String>(102,"0","加入购物车失败");
        }
    }
    public ResultDTO<String> isShoppingTrolley(String username, String productId) {
        if (new IsInTrolleyDAO().isInTrolley(username,productId)) {
            return new ResultDTO<String>(101,"1","已存在");
        } else {
            return new ResultDTO<String>(102,"0","未存在");
        }
    }
}
