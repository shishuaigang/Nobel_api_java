package com.nobel.apitest.concurrency;

import com.nobel.apitest.common.Tipswindow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by shishuaigang on 2017/5/27.
 * 并发测试GUI
 */

public class ConcurrencyGUI {

    public void concurrencyGUI() {

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
        JTextField jtf3 = new JTextField(15); //用于输入并发次数
        JLabel jlb1 = new JLabel("     APIVersion:   ");
        JLabel jlb2 = new JLabel(" JSON文件夹地址:");
        JLabel jlb3 = new JLabel("      并发次数:      ");
        jp1.add(jlb1);
        jp1.add(jtf1);
        jp2.add(jlb2);
        jp2.add(jtf2);
        jp3.add(jlb3);
        jp3.add(jtf3);

        // 给button添加点击事件
        JButton jb = new JButton("开始测试"); //button名字
        jb.addActionListener(e -> new Thread(new Runnable() {
            @Override
            public void run() {

                String apiversion = jtf1.getText();

                // 查询系统属性，windows的文件夹名字与类unix的不同，需要将\转义成\\
                String folderPATH;
                String path = jtf2.getText();
                if (System.getProperty("os.name").contains("Windows")) {
                    folderPATH = path.replace("\\", "\\\\");
                } else {
                    folderPATH = path;
                }

                String concurrency_times = jtf3.getText();

                if (path.equals("") || apiversion.equals("") || concurrency_times.equals("")) {
                    JOptionPane.showMessageDialog(null,
                            "请输入正确的APIVersion,JSON文件夹地址和并发次数");
                } else {
                    try {
                        Tipswindow mt = new Tipswindow();
                        JFrame t = mt.tipswindow();
                        t.setVisible(true);
                        MainProcess N = new MainProcess(folderPATH, Integer.valueOf(concurrency_times));
                        N.mainProcess();
                        t.setVisible(false);
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                }
            }
        }).start());
        jp4.add(jb);

        //Executor executor = Executors.newFixedThreadPool(10);
        container.setLayout(new GridLayout(0, 1));
        container.add(jp1);
        container.add(jp2);
        container.add(jp3);
        container.add(jp4);

        frame.setVisible(true);
    }

    public static void main(String args[]) {
        ConcurrencyGUI cgui = new ConcurrencyGUI();
        cgui.concurrencyGUI();
    }
}
