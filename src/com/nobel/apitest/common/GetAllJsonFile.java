package com.nobel.apitest.common;

import java.io.File;
import java.util.ArrayList;

public class GetAllJsonFile {

    private String path;

    public GetAllJsonFile(String JsonFolderPath) {
        this.path = JsonFolderPath;
    }

    public ArrayList<String> JsonFile() {
        ArrayList<String> arrayFile = new ArrayList<>();
        // 在Java中，可以用File类来表示一个文件（注意这里的文件可以是所有文件，包括文件夹）
        File file = new File(this.path);
        // 遍历文件夹中的file（文件或者文件夹）
        File[] subFile = file.listFiles();

        if (subFile != null) {
            //foreach循环(元素类型 元素名字 ：容器)
            for (File aSubFile : subFile) {
                // 判断是否为文件夹
                if (!aSubFile.isDirectory()) {
                    String fileName = aSubFile.getName();
                    // 判断是否为.json结尾
                    if (fileName.trim().toLowerCase().endsWith(".json")) {
                        arrayFile.add(fileName);
                    }
                }
            }
        }
        return arrayFile;
    }
}