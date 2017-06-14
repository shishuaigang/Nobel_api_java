package com.nobel.apitest.common;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.Map;

public class Params {
    private String path;

    public Params(String JsonFolderPath) {
        this.path = JsonFolderPath;
    }

    //读取section,section的内容是JSONArray,本质上是一个list
    public ArrayList<JSONArray> read_section() {

        ArrayList<JSONArray> section = new ArrayList<>();
        GetAllJsonFile jf = new GetAllJsonFile(this.path);
        ArrayList<String> filelist = jf.JsonFile();

        for (String aFilelist : filelist) {
            String JsonFilePath = this.path + "/" + aFilelist;
            ReadJsonFile data = new ReadJsonFile();
            String JsonData = data.ReadFile(JsonFilePath).toString();
            JSONObject json = JSONObject.parseObject(JsonData);
            JSONArray array = json.getJSONArray("section");
            section.add(array); //section的返回形式: ArrayList<ArrayList>
        }
        return section;
    }

    //读取section中的value
    public ArrayList<ArrayList> get_section_value() {

        ArrayList<JSONArray> section = this.read_section();
        ArrayList<ArrayList> all_section_value = new ArrayList<>();

        for (JSONArray asection : section) { //asection为JSONArray
            ArrayList<String> every_section_value = new ArrayList<>();
            for (int i = 0; i < asection.size(); i++) { //读取ArrayList<ArrayList>中尖括号中的ArrayList的每一个元素
                JSONObject job = asection.getJSONObject(i); //获取jsonarray中的第i个jsonobject,类似{"name":"通用","value":"apis"}
                every_section_value.add(job.getString("value"));
            }
            all_section_value.add(every_section_value);
        }
        return all_section_value;
    }

    //读取apis下的JSONArray，将其顺序放入一个ArrayList中
    public ArrayList<JSONArray> get_apis() {

        ArrayList<ArrayList> s_value = this.get_section_value();
        ArrayList<JSONArray> apis = new ArrayList<>();

        GetAllJsonFile jf = new GetAllJsonFile(this.path);
        ArrayList<String> filelist = jf.JsonFile();

        for (int i = 0; i < filelist.size(); i++) {
            String JsonFilePath = this.path + "/" + filelist.get(i);
            ReadJsonFile data = new ReadJsonFile();
            String JsonData = data.ReadFile(JsonFilePath).toString();
            JSONObject json = JSONObject.parseObject(JsonData);
            for (int j = 0; j < s_value.get(i).size(); j++) { //s_value.get(i)还是一个arraylist，此arraylist的内容为string
                JSONArray array = json.getJSONArray(String.valueOf(s_value.get(i).get(j)));
                apis.add(array);
            }
        }
        return apis;
    }

    //顺序读取所有JSONArray下的summary的值，将所有读到的summary顺序丢入一个ArrayList
    public ArrayList<String> get_summary() {

        ArrayList<JSONArray> apis = this.get_apis();
        ArrayList<String> summary = new ArrayList<>();

        for (int i = 0; i < apis.size(); i++) {
            for (int j = 0; j < apis.get(i).size(); j++) {
                JSONObject jo = apis.get(i).getJSONObject(j);
                if (jo.getBoolean("NoNeed") == null || jo.getString("NoNeed").equals("0")) {
                    summary.add(jo.getString("summary"));
                }
            }
        }
        return summary;
    }

    public ArrayList<String> url() {
        //JSONArray其实是一个list，list的元素是JSONObject或者JSONArray
        ArrayList<JSONArray> apis = this.get_apis();
        ArrayList<String> url = new ArrayList<>();

        for (JSONArray api : apis) {
            for (int j = 0; j < api.size(); j++) {
                JSONObject jo = api.getJSONObject(j);
                if (jo.getBoolean("NoNeed") == null || jo.getString("NoNeed").equals("0")) {
                    url.add(jo.getString("url"));
                }
            }
        }
        return url;
    }

    /*本方法返回带IP地址的完整url,用于发送请求*/
    public ArrayList<String> full_url() {

        ArrayList<String> url = url();
        ArrayList<String> full_url = new ArrayList<>();

        for (String Url : url) {
            full_url.add("http://192.168.31.99:7385/" + Url);
        }
        return full_url;
    }

    public ArrayList<ArrayList> params() {
        ArrayList<JSONArray> apis = this.get_apis();
        ArrayList<ArrayList> params = new ArrayList<>();
        //foreach循环
        for (JSONArray api : apis) {
            for (int j = 0; j < api.size(); j++) {
                ArrayList<String> key_value_array = new ArrayList<>();
                JSONObject jo = api.getJSONObject(j);
                if (jo.getBoolean("NoNeed") == null || jo.getString("NoNeed").equals("0")) {
                    String z = jo.getJSONObject("params").toString();
                    //params的值其实还是json数据，于是将params的值再次转化为java对象
                    JSONObject jsonObject = JSONObject.parseObject(z);
                    //foreach循环
                    //Map.Entry<String, Object>的意思是一个泛型，表示Entry里装的是一个string的字符串一个object对象
                    // 分别是jsonObject的key和value(value是对象)
                    //将jsonObject里的每一个键值对取出来封装成一个Entry对象在存到一个Set里面
                    for (Map.Entry<String, Object> entry : jsonObject.entrySet()) {
                        String keys = entry.getKey();
                        String val = entry.getValue().toString();
                        //对象的值取出来发现还是json数据对，再次把对象的值转换成java对象
                        JSONObject jos = JSONObject.parseObject(val);
                        //再次转化后发现我们需要的default的json数据了，则取default的值
                        String values = jos.getString("default");
                        String key_value = keys + "=" + values + "&";
                        key_value_array.add(key_value);
                    }
                    params.add(key_value_array);
                }
            }
        }
        return params;
    }

    /*
    * 本方法返回拼接完成的request params，apiversion固定为9个9
    * 每次更新不必去理会apiversion的变更
    */
    public ArrayList<String> full_params() {

        ArrayList<String> full_param = new ArrayList<>();
        ArrayList<ArrayList> params = params();

        for (int i = 0; i < params.size(); i++) {
            StringBuilder ps = new StringBuilder();
            for (int j = 0; j < params.get(i).size(); j++) {
                ps.append(params.get(i).get(j));
            }
            full_param.add(ps.toString() + "APIVersion=999999999");
        }
        return full_param;
    }

    public static void main(String args[]) {
        Params p = new Params("/Users/shishuaigang/Desktop/Auto_test/testjson");
        System.out.println(p.full_url().get(37));
        System.out.println(p.params().get(0));
        System.out.println(p.params().get(37));
        System.out.println(p.full_params().get(0));
        System.out.println(p.full_params().get(37));
    }
}