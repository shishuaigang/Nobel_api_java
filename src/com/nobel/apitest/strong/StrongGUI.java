package com.nobel.apitest.strong;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by shishuaigang on 2017/5/23.
 */
public class StrongGUI {

    public void strongGUI() {
        JFrame frame = new JFrame("Beta版本");
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.setSize(350, 180);
        frame.setLocationRelativeTo(null); //将窗口置于屏幕中央显示
        frame.setResizable(false); //窗口固定大小不能动

        Container container = frame.getContentPane();

        JPanel jp1 = new JPanel();
        JPanel jp2 = new JPanel();
        JPanel jp3 = new JPanel();
        JPanel jp4 = new JPanel();

        JTextField jtf1 = new JTextField(15); //用于输入APIVersion
        JTextField jtf2 = new JTextField(15); //用于输入JSON文件夹地址
        JTextField jtf3 = new JTextField(15); //用于错误参数文件地址
        JLabel jlb1 = new JLabel("         APIVersion:");
        JLabel jlb2 = new JLabel("  JSON文件夹地址:");
        JLabel jlb3 = new JLabel("错误参数文件地址:");
        jp1.add(jlb1);
        jp1.add(jtf1);
        jp2.add(jlb2);
        jp2.add(jtf2);
        jp3.add(jlb3);
        jp3.add(jtf3);
        // 给button添加点击事件
        JButton jb = new JButton("开始测试"); //button名字
        jb.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        String apiversion = jtf1.getText();
                        String PATH = jtf2.getText();
                        if (PATH.equals("") || apiversion.equals("")) {
                            JOptionPane.showMessageDialog(null,
                                    "请输入APIVersion和JSON文件夹地址\n例如 ：E:\\test\\inroad_api_crawler");
                        } else {
                            JOptionPane.showMessageDialog(null,
                                    "请输入APIVersion和JSON文件夹地址\n例如 ：E:\\test\\inroad_api_crawler");
                        }
                    }
                }).start();
            }
        });
        jp4.add(jb);

        //网格布局,不指定行数,列数为1
        container.setLayout(new GridLayout(0, 1));
        container.add(jp1);
        container.add(jp2);
        container.add(jp3);
        container.add(jp4);

        frame.setVisible(true);  //窗口可见
    }
}
