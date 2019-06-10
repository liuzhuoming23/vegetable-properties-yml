package com.github.liuzhuoming23.vegetable.propertiesyml.io;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import javax.servlet.http.HttpServletResponse;

/**
 * 写文件
 *
 * @author liuzhuoming
 */
public class Writer {

    /**
     * 本地写文件
     *
     * @param file 输出文件
     * @param lines 数据行集合
     */
    public static void write(File file, List<String> lines) {
        try {
            file.createNewFile();
            try (FileWriter fileWriter = new FileWriter(file);
                BufferedWriter bufferedWriter = new BufferedWriter(fileWriter)) {
                for (String line : lines) {
                    bufferedWriter.write(line);
                    bufferedWriter.newLine();
                }
                bufferedWriter.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * web写文件
     *
     * @param response 响应
     * @param filename 文件名
     * @param lines 数据行集合
     */
    public static void write(HttpServletResponse response, String filename, List<String> lines) {
        response.setContentType("application/force-download");
        response.addHeader("Content-Disposition", "attachment;fileName=" + filename);
        try (OutputStream outputStream = response.getOutputStream();) {
            for (String line : lines) {
                outputStream.write(line.getBytes(StandardCharsets.UTF_8));
                outputStream.write(System.lineSeparator().getBytes(StandardCharsets.UTF_8));
            }
            outputStream.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
