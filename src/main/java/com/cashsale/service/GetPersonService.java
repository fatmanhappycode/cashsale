package com.cashsale.service;

import com.cashsale.bean.CustomerInfoDO;
import com.cashsale.dao.GetPersonDAO;

/**
 * @author Sylvia
 * @date 2018/11/17 - 4:44
 */
public class GetPersonService {
    public CustomerInfoDO getPersonInfo(String username){
        return new GetPersonDAO().getPersonInfo(username);
    }
}
