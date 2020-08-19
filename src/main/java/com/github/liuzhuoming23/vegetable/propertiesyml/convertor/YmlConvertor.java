package com.github.liuzhuoming23.vegetable.propertiesyml.convertor;

import com.github.liuzhuoming23.vegetable.propertiesyml.io.Reader;
import com.github.liuzhuoming23.vegetable.propertiesyml.domain.YmlTree;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;

/**
 * yml转换器
 *
 * @author liuzhuoming
 */
public class YmlConvertor implements Convertor {

    @Override
    public List<String> convert(File file) {
        YML_LINES = new ArrayList<>();
        List<String> lines = Reader.line(file);

        List<YmlTree> ymlTrees = new ArrayList<>();
        for (String line : lines) {
            insert(line, ymlTrees);
        }
        format(ymlTrees, "");
        return YML_LINES;
    }

    @Override
    public List<String> convert(MultipartFile multipartFile) {
        YML_LINES = new ArrayList<>();
        List<String> lines = Reader.line(multipartFile);
        //原文件抽象为最高级的根节点，其中每一项yml的配置对应为文件节点下面的各级分支
        YmlTree ymlTree = new YmlTree();
        ymlTree.setName(".yml");
        for (String line : lines) {
            insert(line, ymlTree.getChildren());
        }
        format(ymlTree.getChildren(), "");
        return YML_LINES;
    }

    /**
     * 插入数据
     *
     * @param line     行数据
     * @param ymlTrees 上级ymlTree子节点集合
     */
    private static void insert(String line, List<YmlTree> ymlTrees) {
        if (!"".equals(line.trim()) && !line.trim().startsWith("#")) {
            String[] strings = line.trim().split("=", 2);
            String value = "";
            if (strings.length == 2) {
                value = strings[1].trim();
            }
            List<String> strs = Arrays.asList(strings[0].trim().split("\\."));
            final List<String> copy = new ArrayList<>(strs);
            insertNode(copy, ymlTrees, value);
        }
    }

    /**
     * 递归添加节点数据
     *
     * @param strs     根据properties分解开的数据节点列表
     * @param ymlTrees 上级ymlTree子节点集合
     * @param value    值
     */
    private static void insertNode(List<String> strs, List<YmlTree> ymlTrees, String value) {
        if (!strs.isEmpty()) {
            String first = strs.get(0);

            YmlTree node = new YmlTree();
            node.setName(first);
            strs.remove(0);

            //如果节点下面的子节点数量为0，则为终端节点，也就是赋值节点
            if (strs.size() == 0) {
                node.setValue(value);
            }

            boolean hasEqualsName = false;
            //遍历查询节点是否存在
            for (YmlTree ymlTree : ymlTrees) {
                //如果节点名称已存在，则递归添加剩下的数据节点
                if (first.equals(ymlTree.getName())) {
                    hasEqualsName = true;
                    insertNode(strs, ymlTree.getChildren(), value);
                }
            }
            //如果遍历结果为节点名称不存在，则递归添加剩下的数据节点，并把新节点添加到上级ymlTree的子节点中
            if (!hasEqualsName) {
                insertNode(strs, node.getChildren(), value);
                ymlTrees.add(node);
            }
        }
    }

    /**
     * yml行数据集合
     */
    private static List<String> YML_LINES = new ArrayList<>();
    /**
     * 缩进空格
     */
    private static final String INDENT_BLANKS = "  ";
    /**
     * 值连接符
     */
    private static final String VALUE_LINK_SIGN = ": ";

    /**
     * 缩进格式化
     *
     * @param ymlTrees yml多叉树
     * @param blanks   缩进格式
     */
    private static void format(List<YmlTree> ymlTrees, String blanks) {
        for (YmlTree ymlTree : ymlTrees) {
            YML_LINES.add(blanks + ymlTree.getName() + VALUE_LINK_SIGN + ymlTree.getValue());
            format(ymlTree.getChildren(), INDENT_BLANKS + blanks);
        }
    }
}
