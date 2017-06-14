package com.nobel.apitest.concurrency;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.ArrayList;

class GenerateHtml {
    private String testbegintime;
    private ArrayList<String> url;
    private ArrayList<String> cn_name;
    private ArrayList<String> min;
    private ArrayList<String> max;
    private ArrayList<String> average;
    private String t_snap;


    GenerateHtml(String testbegintime, ArrayList<String> OriginalUrl, ArrayList<String> cn_name,
                        ArrayList<String> min, ArrayList<String> max, ArrayList<String> average,
                        String t) {
        this.url = OriginalUrl;
        this.cn_name = cn_name;
        this.min = min;
        this.max = max;
        this.average = average;
        this.testbegintime = testbegintime;
        this.t_snap = t;
    }

    void createHtml() throws Exception {
        String HtmlHeader = "<html>\n" +
                "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />\n" +
                "<head>\n" +
                "<title>Inroad API并发测试</title>\n" +
                "</head>\n" +
                "<body bgcolor=\"#F7F7F7\">\n" +
                "<h1 align=\"center\">Inroad_API_Concurrency&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<font size=3>开始时间: " + testbegintime + "</font></h1>\n" +
                "<table style=\"margin-left:12.5%;\" width=\"75%\" align=\"left-side\" border=\"1\" cellspacing=\"0\">\n" +
                "<tr>\n" +
                "<th align=\"center\"  bgcolor=\"#A4D3EE\">序号</th>\n" +
                "<th align=\"center\"  bgcolor=\"#A4D3EE\">API_URL</th>\n" +
                "<th align=\"center\"  bgcolor=\"#A4D3EE\">API中文名字</th>\n" +
                "<th align=\"center\"  bgcolor=\"#A4D3EE\">Min_Time</th>\n" +
                "<th align=\"center\"  bgcolor=\"#A4D3EE\">Max_Time</th>\n" +
                "<th align=\"center\"  bgcolor=\"#A4D3EE\">Average_Time</th>\n" +
                "</tr>";

        BufferedWriter info;
        if (System.getProperty("os.name").contains("Windows")) {
            info = new BufferedWriter(new FileWriter("C:\\testResults\\Nobel\\concurrency\\" + t_snap + "\\result.html"));
        } else {
            info = new BufferedWriter(new FileWriter("/Users/shishuaigang/testResults/Nobel/concurrency/" + t_snap + "/result.html"));
        }
        info.write(HtmlHeader);
        for (int i = 0; i < url.size(); i++) {
            info.write("<tr>\n" +
                    "<th align=\"center\" width=\"150\" bgcolor=\"#A4D3EE\">" + (i+1) + "</th>\n" +
                    "<th align=\"center\" width=\"150\" >" + url.get(i) + "</th>\n" +
                    "<th align=\"center\" width=\"150\" >" + cn_name.get(i) + "</th>\n" +
                    "<th align=\"center\" width=\"150\" >" + min.get(i)+  "ms</th>\n" +
                    "<th align=\"center\" width=\"150\" >" + max.get(i) + "ms</th>\n" +
                    "<th align=\"center\" width=\"150\" >" + average.get(i) + "ms</th>\n" +
                    "</tr>");
        }
        info.write("</table>");
        info.write("</body>");
        info.write("</html>");
        info.close();
    }
}
