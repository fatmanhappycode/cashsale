package com.cashsale.dao;

import com.cashsale.conn.EsConn;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.MatchPhrasePrefixQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;

import java.util.ArrayList;
import java.util.Map;

/**
 * @author 肥宅快乐码
 * @date 2018/12/2 - 22:31
 */
public class ListTitleDAO {
    public ArrayList<String> titleHint(String title) {
        MatchPhrasePrefixQueryBuilder builder = QueryBuilders.matchPhrasePrefixQuery("title",title);

        SearchResponse response = EsConn.client.prepareSearch("cashsale3")
                .setQuery(builder)
                .get();

        SearchHits hits = response.getHits();

        ArrayList<String> list = new ArrayList<>();
        for (SearchHit hit:hits
        ) {
            Map<String,Object> map = hit.getSourceAsMap();
            list.add(map.get("title").toString());
        }
        return list;
    }
}
