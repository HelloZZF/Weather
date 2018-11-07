package com.weather.util;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

/**
 * Created by admin on 2018/7/25.
 */
public class HttpUtil {
    public static String getHttpContent(String url) {
        /**
         * 两种构建HttpClient的方式
         * HttpClients.custom().build();
         * HttpClientBuilder.create().build();
         */
        try {

            HttpClient client = HttpClientBuilder.create().build();
            //设置请求方式
            HttpGet request = new HttpGet(url);
            //发起请求
            HttpResponse response = client.execute(request);
            //获取网页实体对象
            HttpEntity entity = response.getEntity();
            //获取网页内容
            return EntityUtils.toString(entity);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
