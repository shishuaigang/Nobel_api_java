package com.nobel.apitest.common;

import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class GetCookie {

    public String Cookie() {
        OutputStreamWriter ops = null;
        String cookie = "";
        try {
            URL inroad_url = new URL("http://192.168.31.99:7385/API/Account/Login");
            HttpURLConnection connection = (HttpURLConnection) inroad_url.openConnection();
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setRequestMethod("POST");
            connection.connect();
            ops = new OutputStreamWriter(connection.getOutputStream(), "UTF-8");
            ops.write("password=123456&phonenumber=18121225109&rememberme=True&APIVersion=999999999");
            ops.flush();
            new InputStreamReader(connection.getInputStream());
            cookie = connection.getHeaderField("Set-Cookie");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cookie;
    }
}
