package com.github.liuzhuoming23.vegetable.propertiesyml.convertor;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.springframework.web.multipart.MultipartFile;
import org.yaml.snakeyaml.Yaml;

/**
 * properties转换器
 *
 * @author liuzhuoming
 */
public class PropertiesConvertor implements Convertor {

    @Override
    public List<String> convert(File file) {
        try (FileInputStream fileInputStream = new FileInputStream(file)) {
            Yaml yaml = new Yaml();
            Map map = yaml.loadAs(fileInputStream, Map.class);
            format(map, "");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return PROPERTIES_LINES;
    }

    @Override
    public List<String> convert(MultipartFile multipartFile) {
        try (FileInputStream fileInputStream = (FileInputStream) multipartFile.getInputStream()) {
            Yaml yaml = new Yaml();
            Map map = yaml.loadAs(fileInputStream, Map.class);
            format(map, "");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return PROPERTIES_LINES;
    }

    /**
     * properties行数据集合
     */
    private static List<String> PROPERTIES_LINES = new ArrayList<>();
    /**
     * 值连接符
     */
    private static final String VALUE_LINK_SIGN = ":";
    /**
     * 点
     */
    private final static String DOT = ".";

    /**
     * 格式化peoperties
     *
     * @param map properties嵌套数据
     * @param prefix properties参数前缀
     */
    private void format(Map map, String prefix) {
        Set set = map.keySet();
        for (Object key : set) {
            Object value = map.get(key);
            if (value instanceof Map) {
                if ("".equals(prefix)) {
                    format((Map) value, key.toString());
                } else {
                    format((Map) value, prefix + DOT + key);
                }
            } else {
                if (value == null) {
                    value = "";
                }
                if ("".equals(prefix)) {
                    PROPERTIES_LINES.add(key + VALUE_LINK_SIGN + value);
                } else {
                    PROPERTIES_LINES.add(prefix + DOT + key + VALUE_LINK_SIGN + value);
                }
            }
        }
    }
}
