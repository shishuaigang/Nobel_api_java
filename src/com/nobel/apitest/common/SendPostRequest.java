package com.nobel.apitest.common;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class SendPostRequest {

    private String url;
    private String param;
    private String cookie;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }

    public String getCookie() {
        return cookie;
    }

    public void setCookie(String cookie) {
        this.cookie = cookie;
    }

    public SendPostRequest(String url, String param, String cookie) {
        this.url = url;
        this.param = param;
        this.cookie = cookie;
    }

    public HttpURLConnection Post() {
        OutputStreamWriter out = null;
        HttpURLConnection conn = null;
        try {
            URL inroad_url = new URL(url);
            conn = (HttpURLConnection) inroad_url.openConnection();
            // 请求协议(此处是http)生成的URLConnection类的子类HttpURLConnection,故此处最好将其转化
            // 为HttpURLConnection类型的对象,以便用到HttpURLConnection更多的API

            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            // 设置是否向httpUrlConnection输出，因为这个是post请求，参数要放在 http正文内，因此需要设为true, 默认情况下是false;
            conn.setDoInput(true);
            conn.setRequestMethod("POST");    // 请求为POST
            // 设置通用的请求属性
            //conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:52.0) Gecko/20100101 Firefox/52.0");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setRequestProperty("Cookie", cookie);
            // 以上设置步骤需在连接前完成
            conn.connect();  //连接
            // 输入输出相对于本机，输出指拼接完http请求，由本机网口输出，输入相当于服务端返回，由网口输入
            // 获取URLConnection对象对应的输出流
            out = new OutputStreamWriter(conn.getOutputStream(), "UTF-8");
            // 发送请求参数
            out.write(param);
            // flush输出流的缓冲
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return conn;
    }
}
