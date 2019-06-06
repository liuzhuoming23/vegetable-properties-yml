package com.github.liuzhuoming23.vegetable.propertiesyml.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;

/**
 * 读文件
 *
 * @author liuzhuoming
 */
public class Reader {

    /**
     * 本地读文件
     *
     * @param file 读取文件
     * @return 数据行集合
     */
    public static List<String> line(File file) {
        List<String> lines = new ArrayList<>();
        try (FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader)) {
            String s;
            while ((s = bufferedReader.readLine()) != null) {
                lines.add(s);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lines;
    }

    /**
     * web读文件
     *
     * @param multipartFile 读取文件
     * @return 数据行集合
     */
    public static List<String> line(MultipartFile multipartFile) {
        List<String> lines = new ArrayList<>();
        try (InputStream inputStream = multipartFile.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream,
                StandardCharsets.UTF_8);
            BufferedReader br = new BufferedReader(inputStreamReader)) {
            String s;
            while ((s = br.readLine()) != null) {
                lines.add(s);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lines;
    }
}
