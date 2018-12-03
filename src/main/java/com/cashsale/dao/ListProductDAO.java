package com.cashsale.dao;

import com.cashsale.bean.HightLightDTO;
import com.cashsale.bean.ProductDO;
import com.cashsale.bean.ResultDTO;
import com.cashsale.conn.EsConn;
import com.cashsale.enums.ResultEnum;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Date;

import static org.elasticsearch.index.query.QueryBuilders.matchPhraseQuery;

/**
 * @author 肥宅快乐码
 * @date 2018/11/2 - 11:33
 */
public class ListProductDAO {

    Connection conn = new com.cashsale.conn.Conn().getCon();
    PreparedStatement pstmt = null;
    ResultSet rs = null;

    public List<HightLightDTO> listProductByTitle(String title, int offset) {
        HighlightBuilder highlightBuilder = new HighlightBuilder().field("title");
        highlightBuilder.preTags("<em>");
        highlightBuilder.postTags("</em>");


        SearchRequestBuilder request = EsConn.client.prepareSearch("cashsale3")
                .setTypes("doc")
                .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
                .setFrom(offset)
                .setSize(9)
                .setQuery(QueryBuilders.boolQuery().must(matchPhraseQuery("title", title)))
                .highlighter(highlightBuilder);
        SearchResponse response = request.get();

        //处理结果
        List<HightLightDTO> result = new ArrayList<>();
        SearchHits hits = response.getHits();
        for (SearchHit hit:hits
        ) {
            Map<String,Object> map = hit.getSourceAsMap();
            HightLightDTO h = new HightLightDTO(map);
            h.setHighLight(hit.getHighlightFields().get("title").fragments()[0].toString());
            result.add(h);
        }
        return result;
    }

    public List<ProductDO> listProductByTime(String time, int offset) {
        try {
            if (time.equals("asc")) {
                pstmt = conn.prepareStatement("SELECT * FROM product_info ORDER BY publish_time LIMIT ?,9");
            } else if (time.equals("desc")) {
                pstmt = conn.prepareStatement("SELECT * FROM product_info ORDER BY publish_time DESC LIMIT ?,9");
            }
            pstmt.setInt(1,offset);
            rs = pstmt.executeQuery();

            // 构造获取元数据，用以获取列数，列名，列对应的值等相关信息
            ResultSetMetaData metaData = rs.getMetaData();
            int cols_len = metaData.getColumnCount();

            // 用于存放每一件商品的信息
            List<Map<String, Object>> list = new ArrayList<>();
            List<ProductDO> result = new ArrayList<>();

            while (rs.next()) {
                // 存放列名和对应值
                Map<String, Object> map = new HashMap<String, Object>();
                for (int i = 0; i < cols_len; i++) {
                    String cols_name = metaData.getColumnName(i + 1);
                    Object cols_value = rs.getObject(cols_name);
                    if (cols_value == null) {
                        cols_value = "";
                    }
                    map.put(cols_name, cols_value);
                }
                list.add(map);
            }
            if (list != null) {
                for (Map<String, Object> map : list) {
                    ProductDO p = new ProductDO(map);
                    result.add(p);
                }
            }
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
            // 关闭连接
            try {
                rs.close();
                pstmt.close();
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public ResultDTO<List<String>> listProductByTrolley(String username) {
        try {
            pstmt = conn.prepareStatement("SELECT product_id FROM commodity_score WHERE user_name=?");
            pstmt.setString(1,username);
            rs = pstmt.executeQuery();

            List<String> result = new ArrayList<>();

            while (rs.next()) {
               result.add(rs.getString("product_id"));
            }

            return new ResultDTO<List<String>>(ResultEnum.SEARCH_SUCCESS.getCode(),result,ResultEnum.SEARCH_SUCCESS.getMsg());
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
            // 关闭连接
            try {
                rs.close();
                pstmt.close();
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
