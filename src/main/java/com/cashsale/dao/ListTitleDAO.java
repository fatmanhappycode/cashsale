package com.cashsale.dao;

import com.cashsale.conn.EsConn;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.MatchPhrasePrefixQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.suggest.Suggest;
import org.elasticsearch.search.suggest.SuggestBuilder;
import org.elasticsearch.search.suggest.SuggestBuilders;
import org.elasticsearch.search.suggest.completion.CompletionSuggestion;
import org.elasticsearch.search.suggest.completion.CompletionSuggestionBuilder;

import java.util.*;

/**
 * @author 肥宅快乐码
 * @date 2018/12/2 - 22:31
 */
public class ListTitleDAO {
    public List<String> titleHint(String title) {
        //field的名字,前缀(输入的text),以及大小size
        CompletionSuggestionBuilder suggestionBuilderDistrict = SuggestBuilders.completionSuggestion("title.suggest")
                .prefix(title);
        SuggestBuilder suggestBuilder = new SuggestBuilder();
        //添加suggest
        suggestBuilder.addSuggestion("my-suggest", suggestionBuilderDistrict);

        //设置查询builder的index,type,以及建议
        SearchRequestBuilder requestBuilder = EsConn.client.prepareSearch("cashsale3").setTypes("doc").suggest(suggestBuilder);

        SearchResponse response = requestBuilder.get();
        //suggest实体
        Suggest suggest = response.getSuggest();
        //set
        Set<String> suggestSet = new HashSet<>();
        int maxSuggest = 0;
        if (suggest != null) {
            //获取suggest,name任意string
            Suggest.Suggestion result = suggest.getSuggestion("my-suggest");
            for (Object term : result.getEntries()) {

                if (term instanceof CompletionSuggestion.Entry) {
                    CompletionSuggestion.Entry item = (CompletionSuggestion.Entry) term;
                    if (!item.getOptions().isEmpty()) {
                        //若item的option不为空,循环遍历
                        for (CompletionSuggestion.Entry.Option option : item.getOptions()) {
                            String tip = option.getText().toString();
                            if (!suggestSet.contains(tip)) {
                                suggestSet.add(tip);
                                ++maxSuggest;
                            }
                        }
                    }
                }
                if (maxSuggest >= 4) {
                    break;
                }
            }
        }
        List<String> suggests = Arrays.asList(suggestSet.toArray(new String[]{}));
        return suggests;
    }
}
