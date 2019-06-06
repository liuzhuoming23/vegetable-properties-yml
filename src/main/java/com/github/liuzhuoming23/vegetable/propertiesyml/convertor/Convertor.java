package com.github.liuzhuoming23.vegetable.propertiesyml.convertor;

import java.io.File;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;

/**
 * 转换器接口
 *
 * @author liuzhuoming
 */
public interface Convertor {

    /**
     * 本地转换
     *
     * @param file 文件
     * @return 数据行集合
     */
    List<String> convert(File file);

    /**
     * web转换
     *
     * @param multipartFile 文件
     * @return 数据行集合
     */
    List<String> convert(MultipartFile multipartFile);
}
