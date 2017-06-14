package com.nobel.apitest.scan;

import com.nobel.apitest.common.*;

import java.net.HttpURLConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MainProcess {

    private static String test_type= "scan";
    private String PATH;

    MainProcess(String jsonfolderPATH) {
        this.PATH = jsonfolderPATH;
    }

    public void main() throws Exception {

        Date d = new Date();
        SimpleDateFormat bt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String begintime = bt.format(d);// 测试开始的时间
        SimpleDateFormat fn = new SimpleDateFormat("yyyyMMddHHmmss");
        String foldername = fn.format(d);// 存放报告的文件夹的名字(时间戳命名)

        //登录,获取cookie
        GetCookie cookie = new GetCookie();
        String[] c = cookie.Cookie().split(";");

        //读取文件夹中的json数据,获取需要的参数
        Params p = new Params(PATH);

        //获取api的数量
        int len = p.get_summary().size();

        ArrayList<String> params = p.full_params();  //用于存放拼接的params
        ArrayList<String> url = p.url(); //从json中取出的短url，用于写入数据库和test report
        ArrayList<String> full_url = p.full_url(); //完整的url，带上了IP地址
        ArrayList<String> cn_name = p.get_summary(); //从json中取出的中文名字

        ArrayList<String> cn_name_for_db = new ArrayList<>(); //从json中取出的中文名字('替换成#)
        ArrayList<String> res_time = new ArrayList<>(); //用于存放response time
        ArrayList<String> res_code = new ArrayList<>(); //用于存放response code
        ArrayList<String> res_status = new ArrayList<>(); //用于存放response status
        ArrayList<String> res_error_message = new ArrayList<>(); //用于存放response error message
        ArrayList<String> res_error_message_for_db = new ArrayList<>(); //用于存放response error message('替换成#)

        //将中文名字中的'替换为#,方便写入数据库
        for (int i = 0; i < len; i++) {
            String sUmmary = cn_name.get(i).replace("'", "#"); //替换中文名字内的'
            cn_name_for_db.add(sUmmary);
        }

        // 发送请求，获取需要的结果
        try {
            for (int i = 0; i < len; i++) {
                ArrayList<HttpURLConnection> RE = new ArrayList<>();
                ArrayList<String> RE_TIME = new ArrayList<>();

                SendPostRequest sp = new SendPostRequest(full_url.get(i), params.get(i), c[0]);
                ElapsedTimeAndResponse REStime = new ElapsedTimeAndResponse(sp);

                RE.addAll(REStime.calculateResTime().keySet());
                RE_TIME.addAll(REStime.calculateResTime().values());

                HttpURLConnection connection = RE.get(0); // return post connection

                GetResponseDetail mes = new GetResponseDetail(connection);

                resStatusAndErrormessage t = new resStatusAndErrormessage(mes.getResMessage());

                String tsp;
                if (t.responseErrorMessage() != null) {
                    tsp = t.responseErrorMessage().replace("'", "#");//替换error message内的'
                } else {
                    tsp = t.responseErrorMessage();
                }
                res_code.add(String.valueOf(connection.getResponseCode()));
                res_time.add(RE_TIME.get(0));
                res_status.add(String.valueOf(t.responseStatus()));
                res_error_message.add(t.responseErrorMessage());
                res_error_message_for_db.add(tsp);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        //计算成功率
        PassRate pr = new PassRate(len, res_code, res_status);
        ArrayList<String> passrate = pr.calculatePassrate(); //成功率数组

        ReadAndWriteCsv csv = new ReadAndWriteCsv(url, cn_name, res_time, res_code, res_status, res_error_message, foldername);
        csv.writeCsv();

        //生成test report
        GenerateHtml gt = new GenerateHtml(begintime, url,
                cn_name, res_time, res_code, res_status, res_error_message, passrate, foldername);
        gt.createHtml();

        //生成写入数据库的SQL语句
        ArrayList<String> sql = new ArrayList<>();
        for (int i = 0; i < len; i++) {
            sql.add("insert into Nobel_Crawler_Test(TestNo, API_URL, API_ChineseName, Response_code, Response_time," +
                    "Response_status, Error_Message) values(" + foldername + ",'" + url.get(i) + "','" + cn_name_for_db.get(i) + "','" +
                    res_code.get(i) + "','" + res_time.get(i) + "','" + res_status.get(i) + "','" + res_error_message_for_db.get(i) + "')");
        }

        //执行SQL语句
        for (String SQL : sql) {
            DBoperation.executeUpdate(SQL);
        }

        //发送邮件
        SendMail sm = new SendMail(foldername,test_type);
        sm.sendMail();
    }
}
