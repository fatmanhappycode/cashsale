package com.cashsale.service;

import com.alibaba.fastjson.JSONObject;
import com.cashsale.bean.Pager;
import com.cashsale.bean.Product;
import com.cashsale.bean.Result;
import com.cashsale.dao.ListProductDAO;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.List;

/**
 * @author 肥宅快乐码
 * @date 2018/11/2 - 11:27
 */
public class SearchService {
    public Result<Pager> searchByTitle(String searchByTitle) {
        // 构建jsonObject对象后解析参数
        JsonObject jsonObject = (JsonObject) new JsonParser().parse(searchByTitle);
        String title = jsonObject.get("title").getAsString();
        int currentPage = jsonObject.get("currentPage").getAsInt();
        // 添加模糊查询需要的%
        title = "%" + title + "%";
        // 根据页数计算出从第几条开始查询的offset
        int offset = (currentPage - 1) * 8;
        List<Product> products = new ListProductDAO().listProduct(title, offset);
        Pager<Product> product = new Pager<>(currentPage+1,products);
        return  new Result<>(107, product,"查询成功");
    }
}
