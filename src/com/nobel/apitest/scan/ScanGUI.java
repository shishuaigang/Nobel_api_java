package com.nobel.apitest.scan;

import com.nobel.apitest.common.Tipswindow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * api监控界面
 */

public class ScanGUI {

    public void scanGUI() {
        JFrame frame = new JFrame("Beta版本");
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.setSize(350, 140);
        frame.setLocationRelativeTo(null); //将窗口置于屏幕中央显示
        frame.setResizable(false); //窗口固定大小不能动

        Container container = frame.getContentPane();

        JPanel jp1 = new JPanel();
        JPanel jp2 = new JPanel();
        JPanel jp3 = new JPanel();

        JTextField jtf1 = new JTextField(15); //用于输入APIVersion
        JTextField jtf2 = new JTextField(15); //用于输入JSON文件夹地址
        JLabel jlb1 = new JLabel("           APIVersion:");
        JLabel jlb2 = new JLabel("JSON文件夹地址:");
        jp1.add(jlb1);
        jp1.add(jtf1);
        jp2.add(jlb2);
        jp2.add(jtf2);
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

                if (path.equals("") || apiversion.equals("")) {
                    JOptionPane.showMessageDialog(null,
                            "请输入正确的APIVersion和JSON文件夹地址");
                } else {
                    try {
                        Tipswindow mt = new Tipswindow();
                        JFrame t = mt.tipswindow();
                        t.setVisible(true);
                        MainProcess N = new MainProcess(folderPATH);
                        N.main();
                        t.setVisible(false);
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                }
            }
        }).start());
        jp3.add(jb);

        //网格布局,不指定行数,列数为1
        container.setLayout(new GridLayout(0, 1));
        container.add(jp1);
        container.add(jp2);
        container.add(jp3);

        frame.setVisible(true);  //窗口可见
    }
}
