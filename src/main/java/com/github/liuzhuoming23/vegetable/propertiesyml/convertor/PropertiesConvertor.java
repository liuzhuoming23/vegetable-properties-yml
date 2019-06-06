package com.github.liuzhuoming23.vegetable.propertiesyml.convertor;

import com.github.liuzhuoming23.vegetable.propertiesyml.domain.YmlTree;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;

/**
 * properties转换器
 *
 * @author liuzhuoming
 */
public class PropertiesConvertor implements Convertor {

    @Override
    public List<String> convert(File file) {
        return null;
    }

    @Override
    public List<String> convert(MultipartFile multipartFile) {
        return null;
    }

    public static List<YmlTree> parse(List<String> lines) {
        List<YmlTree> ymlTrees = new ArrayList<>();
        for (int i = 0; i < lines.size(); i++) {
            YmlTree ymlTree = new YmlTree();
            if (!"".equals(lines.get(i).trim()) && !lines.get(i).trim().startsWith("#")) {
                String line = lines.get(i).stripLeading();
                String[] strings = line.split(":");

                String value = "";
                if (strings.length == 2) {
                    value = strings[1].trim();
                }

                int indentNum = indentNum(lines.get(i));

                if (indentNum == 0) {
                    ymlTree.setName(strings[0].trim());
                    ymlTree.setValue(value);
                    ymlTree.setIndexNum(indentNum);
                } else {
                    if (indentNum > indentNum(lines.get(i - 1))) {
                        YmlTree node = new YmlTree();
                        node.setName(strings[0].trim());
                        node.setValue(value);
                        node.setIndexNum(indentNum);
                        ymlTree.getChildren().add(node);
                    }
                }
            }
            ymlTrees.add(ymlTree);
        }
        return ymlTrees;
    }

    /**
     * 获取行数据空格缩进数量
     *
     * @param line 数据行
     * @return 缩进空格数量
     */
    private static int indentNum(String line) {
        return line.length() - line.stripLeading().length();
    }
}
