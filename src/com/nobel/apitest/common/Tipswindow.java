package com.nobel.apitest.common;

import javax.swing.*;
import java.awt.*;

public class Tipswindow {

    public JFrame tipswindow() {
        JFrame jf = new JFrame("Dialog");
        jf.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        jf.setSize(225, 75);
        jf.setResizable(false); //窗口固定大小,不能放大缩小
        jf.setLocationRelativeTo(null);

        Container c = jf.getContentPane();
        JLabel lb = new JLabel();
        JPanel jp = new JPanel();

        lb.setText("正在测试中，请稍后.....");
        jp.add(lb);
        c.add(jp);
        return jf;
    }
}