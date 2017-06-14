package com.nobel.apitest.scan;

import com.csvreader.CsvWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by shishuaigang on 2017/5/2.
 * 写入csv,一行一行的写入
 */

class ReadAndWriteCsv {

    private ArrayList<String> url;
    private ArrayList<String> cn_name;
    private ArrayList<String> res_time;
    private ArrayList<String> res_code;
    private ArrayList<String> res_status;
    private ArrayList<String> res_error_message;
    private String t_snap;

    ReadAndWriteCsv(ArrayList<String> OriginalUrl, ArrayList<String> cn_name, ArrayList<String> res_time,
                    ArrayList<String> res_code, ArrayList<String> res_status,
                    ArrayList<String> res_error_message, String t) {
        this.url = OriginalUrl;
        this.cn_name = cn_name;
        this.res_time = res_time;
        this.res_code = res_code;
        this.res_status = res_status;
        this.res_error_message = res_error_message;
        this.t_snap = t; //时间戳命名的testresult文件夹
    }

    void writeCsv() {
        //windows 类unix文件夹命令有别
        String path;

        if (System.getProperty("os.name").contains("Windows")) {
            path = "C:\\testResults\\Nobel\\scan\\";
        } else {
            path  = "/Users/shishuaigang/testResults/Nobel/scan/";
        }

        try {
            (new File(path + t_snap)).mkdirs();

            //windows 类unix系统创建文件命令有别
            FileWriter writer;
            if (System.getProperty("os.name").contains("Windows")) {
                writer = new FileWriter(path + t_snap + "\\result.csv");
            } else {
                writer = new FileWriter(path + t_snap + "/result.csv");
            }

            CsvWriter csvWriter = new CsvWriter(writer, ',');
            String[] contents = {"API_URL", "API_Chinese_Name", "Response_Time", "Response_Code", "Status", "Error_Message"};//一行的方式写入
            csvWriter.writeRecord(contents);
            for (int i = 0; i < this.url.size(); i++) {
                csvWriter.writeRecord(
                        new String[]{url.get(i), cn_name.get(i), res_time.get(i), res_code.get(i), res_status.get(i),
                                res_error_message.get(i)});
            }
            csvWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
