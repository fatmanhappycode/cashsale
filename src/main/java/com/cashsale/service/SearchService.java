package com.cashsale.service;

import com.cashsale.bean.PagerDTO;
import com.cashsale.bean.ProductDO;
import com.cashsale.bean.ResultDTO;
import com.cashsale.dao.ListProductDAO;

import java.util.List;

/**
 * @author 肥宅快乐码
 * @date 2018/11/2 - 11:27
 */
public class SearchService {
    public ResultDTO<PagerDTO> searchByTitle(String title, String currentPage) {
        // 添加模糊查询需要的%
        title = "%" + title + "%";
        int page;
        if (currentPage == "") {
            page = 1;
        } else {
            page = Integer.valueOf(currentPage);
        }
        // 根据页数计算出从第几条开始查询的offset
        int offset = (page - 1) * 8;
        List<ProductDO> products = new ListProductDAO().listProduct(title, offset);
        PagerDTO<ProductDO> product = new PagerDTO<>(page+1,products);
        return  new ResultDTO<PagerDTO>(107, product,"查询成功");
    }
}
