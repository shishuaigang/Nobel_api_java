package com.nobel.apitest.common;

import java.net.HttpURLConnection;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

public class ElapsedTimeAndResponse {

    private SendPostRequest request;

    public ElapsedTimeAndResponse(SendPostRequest request) {
        this.request = request;
    }

    public Map<HttpURLConnection, String> calculateResTime() {
        String num;
        HttpURLConnection conn;
        Map<HttpURLConnection, String> m = new HashMap<>();

        long begin = System.nanoTime(); //请求发送前的时间戳
        SendPostRequest re = request;
        conn = re.Post();//发送post请求，return conn
        Long end = System.nanoTime(); // return conn 后的时间戳
        //发送请求-服务器响应-接收完response所经历的时间(elapsed time)

        float elapsed = (float) (end - begin) / 1000000L;
        DecimalFormat df = new DecimalFormat("0.0");
        num = df.format(elapsed);

        m.put(conn, num);
        return m;
    }
}