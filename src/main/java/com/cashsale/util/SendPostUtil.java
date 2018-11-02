package com.cashsale.util;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 肥宅快乐码
 * @date 2018/11/2 - 9:14
 */
public class SendPostUtil {
    public static int sendPost(String url, List<NameValuePair> nameValuePairList) {
        CloseableHttpClient client = null;
        CloseableHttpResponse response = null;
        try {
            // 创建一个httpclient对象
            client = HttpClients.createDefault();
            // 创建一个post对象
            HttpPost post = new HttpPost(url);
            // 包装成一个Entity对象
            StringEntity entity = new UrlEncodedFormEntity(nameValuePairList, "UTF-8");
            // 设置请求的内容

            post.setEntity(entity);
            // 设置请求的报文头部的编码
            post.setHeader(new BasicHeader("Content-Type", "application/x-www-form-urlencoded; charset=utf-8"));
            // 设置请求的报文头部的编码
            post.setHeader(new BasicHeader("Accept", "text/plain;charset=utf-8"));
            // 执行post请求
            response = client.execute(post);
            // 获取响应码
            int statusCode = response.getStatusLine().getStatusCode();
            return statusCode;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    /**
     * 组织请求参数{参数名和参数值下标保持一致}
     *
     * @param params 参数名数组
     * @param values 参数值数组
     * @return 参数对象
     */
    public static List<NameValuePair> getParams(Object[] params, Object[] values) {
        /**
         * 校验参数合法性
         */
        boolean flag = params.length > 0 && values.length > 0 && params.length == values.length;
        if (flag) {
            List<NameValuePair> nameValuePairList = new ArrayList<>();
            for (int i = 0; i < params.length; i++) {
                nameValuePairList.add(new BasicNameValuePair(params[i].toString(), values[i].toString()));
            }
            return nameValuePairList;
        } else {
        }
        return null;
    }
}
