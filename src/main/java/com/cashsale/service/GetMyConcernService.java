package com.cashsale.service;

import com.cashsale.bean.ProductDO;
import com.cashsale.bean.UserInteractDTO;
import com.cashsale.dao.GetMyProductDAO;
import com.cashsale.dao.GetPersonDAO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description:获取我关注的人以及发布商品的数目
 * @Author: 8-0416
 * @Date: 2018/12/4
 */
public class GetMyConcernService {

    public UserInteractDTO getMyConcern(String username){
        GetPersonDAO getPersonDAO = new GetPersonDAO();
        List<String> myConcern = getPersonDAO.getConcern(username);
        int fans = getPersonDAO.getMyFans(username);
        UserInteractDTO userInteractDTO = new UserInteractDTO();
        Map<String, Integer> map = new HashMap<>();
        for(String user : myConcern){
            List<ProductDO> product = new GetMyProductDAO().getMyProduct(user);
            map.put(user, product.size());
        }
        userInteractDTO.setConcernsProductNumber(map);
        userInteractDTO.setConcernNumber(myConcern.size());
        userInteractDTO.setFanNumber(fans);
        return userInteractDTO;
    }
}
