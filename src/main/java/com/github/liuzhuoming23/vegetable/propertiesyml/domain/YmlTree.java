package com.github.liuzhuoming23.vegetable.propertiesyml.domain;

import java.util.ArrayList;
import java.util.List;
import lombok.Data;

/**
 * yml多叉树
 *
 * @author liuzhuoming
 */
@Data
public class YmlTree {

    /**
     * 名称
     */
    private String name;
    /**
     * 值
     */
    private String value = "";
    /**
     * 注释（暂无）
     */
    private String comment;
    /**
     * 缩进数量
     */
    private int indexNum = 0;
    /**
     * 子节点集合
     */
    private List<YmlTree> children = new ArrayList<>();
}
