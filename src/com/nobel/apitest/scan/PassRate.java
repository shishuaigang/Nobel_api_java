package com.nobel.apitest.scan;

import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * 计算成功率，200&1为pass，其余为fail
 */

public class PassRate {

    private int len;
    private ArrayList<String> res_code;
    private ArrayList<String> res_status;

    public PassRate(int len, ArrayList<String> res_code, ArrayList<String> res_status) {
        this.len = len;
        this.res_code = res_code;
        this.res_status = res_status;
    }

    public ArrayList<String> calculatePassrate() throws Exception {

        int success_api = 0;
        int fail_api = 0;
        ArrayList<String> result = new ArrayList<>(); //将成功数，失败数，成功率放入一个数组(String)

        //防止出现所有api都是noneed的情况发生
        if (this.len != 0) {
            for (int i = 0; i < this.len; i++) {
                if (Integer.valueOf(res_code.get(i)) == 200 && Integer.valueOf(res_status.get(i)) == 1) {
                    success_api++;
                } else {
                    fail_api++;
                }
            }

            result.add(String.valueOf(success_api)); //添加成功api的数目
            result.add(String.valueOf(fail_api)); //添加失败api的数目

            //格式化成2位小数
            float p_r = (float) success_api / this.len;
            DecimalFormat df = new DecimalFormat("0.00");
            String num = df.format(p_r * 100);

            //添加成功率
            result.add(num + "%");
        } else {
            //将所有数据都设置成0，result为一个三元数组
            result.add(String.valueOf(0));
            result.add(String.valueOf(0));
            result.add(String.valueOf(0));
        }

        return result; //返回结果，第一个为成功的api个数，第二个为失败的api个数，第三个为成功率(均以字符串的形式返回)
    }
}
