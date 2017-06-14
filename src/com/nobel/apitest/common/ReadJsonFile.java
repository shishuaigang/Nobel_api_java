package com.nobel.apitest.common;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class ReadJsonFile {

    public StringBuilder ReadFile(String path) {
        File file = new File(path);
        Scanner scanner = null;
        StringBuilder buffer = new StringBuilder();
        try {
            scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                buffer.append(scanner.nextLine());
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (scanner != null) {
                scanner.close();
            }
        }
        return buffer;
    }
}
