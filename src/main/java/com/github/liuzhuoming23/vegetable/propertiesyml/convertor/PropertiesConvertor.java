package com.github.liuzhuoming23.vegetable.propertiesyml.convertor;

import com.github.liuzhuoming23.vegetable.propertiesyml.domain.YmlTree;
import com.github.liuzhuoming23.vegetable.propertiesyml.io.Reader;
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
        List<List<String>> list = new ArrayList<>();
        for (int i = 0; i < lines.size(); i++) {
            List<String> strings = new ArrayList<>();
            strings.add(lines.get(i));
            if (indentNum(lines.get(i)) == 0) {
                list.add(strings);
                strings.clear();
            }
        }

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
     * 将数据行集合按照缩进解析为多个数据行集合
     *
     * @param lines 数据行集合
     * @return 数据行集合的集合
     */
    public static List<List<String>> parseList2ListInList(List<String> lines) {
        List<List<String>> list = new ArrayList<>();
        List<String> strings = new ArrayList<>();
        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            if (!"".equals(lines.get(i).trim()) && !lines.get(i).trim().startsWith("#")) {
                if (i != 0 && indentNum(line) == 0) {
                    if (!strings.isEmpty()) {
                        List<String> strings1 = List.copyOf(strings);
                        list.add(strings1);
                        strings = new ArrayList<>();
                    }
                }
                strings.add(line);
                if (i == lines.size() - 1) {
                    if (!strings.isEmpty()) {
                        List<String> strings1 = List.copyOf(strings);
                        list.add(strings1);
                    }
                }
            }
        }
        return list;
    }


    /**
     * 获取行数据空格缩进数量
     *
     * @param line 数据行
     * @return 缩进空格数量
     */
    public static int indentNum(String line) {
        return line.length() - line.stripLeading().length();
    }
}
