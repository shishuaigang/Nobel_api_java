package com.nobel.apitest.concurrency;

import com.nobel.apitest.common.SendPostRequest;

import java.net.HttpURLConnection;
import java.text.DecimalFormat;
import java.util.concurrent.*;


/**
 * Created by shishuaigang on 2017/6/2.
 * 并发测试的代码
 */

public class ConcurrencyCore {

    private String url;
    private int concurrencyTimes;
    private String cookie;
    private String param;

    public String getUrl() {
        return url;
    }

    public int getConcurrencyTimes() {
        return concurrencyTimes;
    }

    public String getCookie() {
        return cookie;
    }

    public String getParam() {
        return param;
    }

    public ConcurrencyCore(String url, int times, String param, String cookie) {
        this.url = url;
        this.concurrencyTimes = times;
        this.param = param;
        this.cookie = cookie;
    }

    public ConcurrentLinkedDeque concurrency() throws InterruptedException, ExecutionException {

        ConcurrentLinkedDeque<String> cld = new ConcurrentLinkedDeque<>();
        //线程数设为8,cpu为双核4线程
        ExecutorService fixedThreadPool = Executors.newFixedThreadPool(8, new WorkThreadFactory());

        //并发次数从输入框中获取
        for (int i = 0; i < getConcurrencyTimes(); i++) {
            Future<String> future = fixedThreadPool.submit(new Callable<String>() {
                @Override
                public String call() throws Exception {

                    long begin = System.nanoTime(); //请求发送前的时间戳
                    SendPostRequest re = new SendPostRequest(getUrl(), getParam(), getCookie());
                    HttpURLConnection fanhui = re.Post();//发送post请求，return conn
                    Long end = System.nanoTime(); // return conn 后的时间戳

                    float elapsed = (float) (end - begin) / 1000000L;
                    DecimalFormat df = new DecimalFormat("0.0");
                    //response.add(t);
                    //发送请求-服务器响应-接收完response所经历的时间(elapsed time)

                    return df.format(elapsed); //return，这样future才能get
                }
            });
            cld.add(future.get());
        }
        fixedThreadPool.shutdown();
        return cld;
    }
}
