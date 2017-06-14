package com.nobel.apitest.scan;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.ArrayList;


class GenerateHtml {

    private String t;
    private ArrayList<String> url;
    private ArrayList<String> cn_name;
    private ArrayList<String> res_time;
    private ArrayList<String> res_code;
    private ArrayList<String> res_status;
    private ArrayList<String> res_error_message;
    private ArrayList<String> passrate;
    private String t_snap;


    GenerateHtml(String testbegintime, ArrayList<String> OriginalUrl, ArrayList<String> cn_name,
                 ArrayList<String> res_time, ArrayList<String> res_code, ArrayList<String> res_status,
                 ArrayList<String> res_error_message, ArrayList<String> passrate, String t) {
        this.url = OriginalUrl;
        this.cn_name = cn_name;
        this.res_time = res_time;
        this.res_code = res_code;
        this.res_status = res_status;
        this.res_error_message = res_error_message;
        this.passrate = passrate;
        this.t = testbegintime;
        this.t_snap = t;
    }

    void createHtml() throws Exception {

        String HtmlHeader = "<html>\n" +
                "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />\n" +
                "<head>\n" +
                "<title>Nobel API扫描</title>\n" +
                "</head>\n" +
                "<body bgcolor=\"#F7F7F7\">\n" +
                "<h1 align=\"center\">Nobel_API_Scan&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<font size=3>开始时间: " + this.t + "</font></h1>\n" +
                "<table style=\"margin-left:12.5%;\" width=\"40%\" align=\"left-side\" border=\"1\" cellspacing=\"0\">\n" +
                "<tr>\n" +
                "<th align=\"center\" width=\"150\" bgcolor=\"#A4D3EE\">API总数</th>\n" +
                "<th align=\"center\" width=\"150\" bgcolor=\"#A4D3EE\">成功</th>\n" +
                "<th align=\"center\" width=\"150\" bgcolor=\"#A4D3EE\">失败</th>\n" +
                "<th align=\"center\" width=\"150\" bgcolor=\"#A4D3EE\">成功率</th>\n" +
                "</tr>" +
                "<tr>";

        String HtmlMiddle = "</tr>\n" +
                "        </table>\n" +
                "        <h3 align=\"center\" style=\"color:black\">详细信息&nbsp;&nbsp;&nbsp;<font size=3>(Response_time指的是api发送请求加上请求完全返回的时间)</font></h3>\n" +
                "        <table width=\"75%\" align=\"center\" style=\"TABLE-LAYOUT:fixed\" border=\"1\" cellspacing=\"0\">\n" +
                "        <tr>\n" +
                "        <td align=\"center\" width=\"5%\" bgcolor=\"#A4D3EE\">序号</td>\n" +
                "        <td align=\"center\" bgcolor=\"#A4D3EE\">API_URL</td>\n" +
                "        <td align=\"center\" bgcolor=\"#A4D3EE\">API_Chinese_name</td>\n" +
                "        <td align=\"center\" width=\"10%\" bgcolor=\"#A4D3EE\">Response_Time</td>\n" +
                "        <td align=\"center\" width=\"10%\" bgcolor=\"#A4D3EE\">Response_Code</td>\n" +
                "        <td align=\"center\" width=\"5%\" bgcolor=\"#A4D3EE\">Status</td>\n" +
                "        <td style=\"WORD-WRAP: break-word;word-break:break-all\" align=\"center\" bgcolor=\"#A4D3EE\">Error message</td>\n" +

                "        </tr>";
        BufferedWriter info;
        if (System.getProperty("os.name").contains("Windows")) {
            info = new BufferedWriter(new FileWriter("C:\\testResults\\Nobel\\scan\\" + t_snap + "\\result.html"));
        } else {
            info = new BufferedWriter(new FileWriter("/Users/shishuaigang/testResults/Nobel/scan/" + t_snap + "/result.html"));
        }
        info.write(HtmlHeader);
        info.write("<td align=\"center\">" + this.url.size() + "</td>");
        info.write("<td align=\"center\" bgcolor=\"#C1FFC1\">" + this.passrate.get(0) + "</td>");
        info.write("<td align=\"center\" bgcolor=\"#FF4500\">" + this.passrate.get(1) + "</td>");
        info.write("<td align=\"center\">" + this.passrate.get(2) + "</td>");
        info.write(HtmlMiddle);
        for (int i = 0; i < this.url.size(); i++) {
            info.write("<tr>");
            info.write("<td align=\"center\">" + (i + 1) + "</td>");
            info.write("<td align=\"center\" style=\"WORD-WRAP: break-word;word-break:break-all\">" + this.url.get(i) + "</td>");
            info.write("<td align=\"center\">" + this.cn_name.get(i) + "</td>");
            info.write("<td align=\"center\">" + this.res_time.get(i) + "ms</td>");
            if (Integer.valueOf(this.res_code.get(i)) == 200 && Integer.valueOf(this.res_status.get(i)) == 1) {
                info.write("<td align=\"center\" bgcolor=\"#C1FFC1\">" + 200 + "</td>");
                info.write("<td align=\"center\" bgcolor=\"#C1FFC1\">" + 1 + "</td>");
                info.write("<td style=\"word-break:keep-all;white-space:nowrap;overflow:hidden;text-overflow:ellipsis;\" align=\"center\">" + this.res_error_message.get(i) + "</td>");
            } else if (Integer.valueOf(this.res_code.get(i)) == 200 && Integer.valueOf(this.res_status.get(i)) == 0) {
                info.write("<td align=\"center\" bgcolor=\"#C1FFC1\">" + 200 + "</td>");
                info.write("<td align=\"center\" bgcolor=\"red\">" + 0 + "</td>");
                info.write("<td style=\"word-break:keep-all;white-space:nowrap;overflow:hidden;text-overflow:ellipsis;\" align=\"center\">" + this.res_error_message.get(i) + "</td>");
            } else if (Integer.valueOf(this.res_code.get(i)) == 500) {
                info.write("<td align=\"center\" bgcolor=\"purple\">" + 500 + "</td>");
                info.write("<td align=\"center\" bgcolor=\"purple\">" + "" + "</td>");
                info.write("<td style=\"word-break:keep-all;white-space:nowrap;overflow:hidden;text-overflow:ellipsis;\" align=\"center\">" + this.res_error_message.get(i) + "</td>");
            } else {
                info.write("<td align=\"center\" bgcolor=\"red\">" + this.res_code.get(i) + "</td>");
                info.write("<td align=\"center\" bgcolor=\"red\">" + "" + "</td>");
                info.write("<td style=\"word-break:keep-all;white-space:nowrap;overflow:hidden;text-overflow:ellipsis;\" align=\"center\">" + this.res_error_message.get(i) + "</td>");
            }
            info.write("</tr>");
        }
        info.write("</table>");
        info.write("</body>");
        info.write("</html>");
        info.close();
    }
}
