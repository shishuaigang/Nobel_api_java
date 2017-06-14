package com.nobel.apitest.common;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;


public class GetResponseDetail {

    private HttpURLConnection connection;

    public GetResponseDetail(HttpURLConnection conn) {
        this.connection = conn;
    }

    public String getResMessage() {
        BufferedReader in = null;
        String res_message = "";
        String result = "";
        try {
            in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
                res_message = result;  //返回api的response message
            }
        } catch (IOException e) {
            res_message = null;  //失败则null
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return res_message;
    }
}
