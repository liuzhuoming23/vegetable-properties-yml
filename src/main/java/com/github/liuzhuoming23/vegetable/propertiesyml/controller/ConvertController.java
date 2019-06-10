package com.github.liuzhuoming23.vegetable.propertiesyml.controller;

import com.github.liuzhuoming23.vegetable.propertiesyml.convertor.PropertiesConvertor;
import com.github.liuzhuoming23.vegetable.propertiesyml.io.Writer;
import com.github.liuzhuoming23.vegetable.propertiesyml.convertor.YmlConvertor;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * 转换
 *
 * @author liuzhuoming
 */
@RestController
@Slf4j
public class ConvertController {

    /**
     * yml后缀
     */
    private final static String SUFFIXNAME_YML = ".yml";
    /**
     * properties后缀
     */
    private final static String SUFFIXNAME_PROPERTIES = ".properties";
    /**
     * 点
     */
    private final static String DOT = ".";

    private static final YmlConvertor YML_CONVERTOR = new YmlConvertor();
    private static final PropertiesConvertor PROPERTIES_CONVERTOR = new PropertiesConvertor();

    @RequestMapping(value = "/convert")
    public void convert(HttpServletResponse response, @RequestParam("file") MultipartFile file) {
        List<String> lines = new ArrayList<>();
        String filename = file.getOriginalFilename();
        if (filename != null && filename.contains(DOT)) {
            String suffixName = filename.substring(filename.lastIndexOf(DOT));
            String newFilename;

            if (SUFFIXNAME_PROPERTIES.equalsIgnoreCase(suffixName)) {
                newFilename = filename.substring(0, filename.lastIndexOf(DOT)) + SUFFIXNAME_YML;
                lines = YML_CONVERTOR.convert(file);
            } else if (SUFFIXNAME_YML.equalsIgnoreCase(suffixName)) {
                newFilename =
                    filename.substring(0, filename.lastIndexOf(DOT)) + SUFFIXNAME_PROPERTIES;
                lines = PROPERTIES_CONVERTOR.convert(file);
            } else {
                newFilename = "error.txt";
                lines.add("不支持转换" + suffixName + "文件");
            }
            Writer.write(response, newFilename, lines);
        } else {
            String newFilename = "error.txt";
            lines.add("请选择上传文件");
            Writer.write(response, newFilename, lines);
        }
    }
}
