package com.cashsale.service;

import com.cashsale.bean.HightLightDTO;
import com.cashsale.bean.PagerDTO;
import com.cashsale.bean.ProductDO;
import com.cashsale.bean.ResultDTO;
import com.cashsale.dao.ListProductDAO;
import com.cashsale.dao.ListTitleDAO;
import com.cashsale.enums.ResultEnum;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 肥宅快乐码
 * @date 2018/11/2 - 11:27
 */
public class SearchService {
    /**
     * 每页有多少条信息
     */
    public static final int NUMBER_OF_PAGE = 9;
    public ResultDTO<PagerDTO> searchByTitle(String title, String currentPage) {
        int page;
        if (currentPage == "") {
            page = 1;
        } else {
            page = Integer.valueOf(currentPage);
        }
        // 根据页数计算出从第几条开始查询的offset
        int offset = (page - 1) * NUMBER_OF_PAGE;
        List<HightLightDTO> h = new ListProductDAO().listProductByTitle(title, offset);
        PagerDTO<HightLightDTO> product = new PagerDTO<>(page+1,h);
        return  new ResultDTO<PagerDTO>(ResultEnum.SEARCH_SUCCESS.getCode(), product,ResultEnum.SEARCH_SUCCESS.getMsg());
    }

    public ResultDTO<PagerDTO> searchByTime(String time, String currentPage) {
        int page;
        if (currentPage == "") {
            page = 1;
        } else {
            page = Integer.valueOf(currentPage);
        }
        // 根据页数计算出从第几条开始查询的offset
        int offset = (page - 1) * NUMBER_OF_PAGE;
        List<ProductDO> products = new ListProductDAO().listProductByTime(time, offset);
        PagerDTO<ProductDO> product = new PagerDTO<>(page+1,products);
        return  new ResultDTO<PagerDTO>(ResultEnum.SEARCH_SUCCESS.getCode(), product,ResultEnum.SEARCH_SUCCESS.getMsg());
    }

    public ResultDTO<List<String>> searchHint(String title) {
        List<String> list = new ListTitleDAO().titleHint(title);
        return new ResultDTO<List<String>>(ResultEnum.SEARCH_SUCCESS.getCode(), list,ResultEnum.SEARCH_SUCCESS.getMsg());
    }
}
