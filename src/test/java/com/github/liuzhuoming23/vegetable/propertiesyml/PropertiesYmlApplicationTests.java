package com.github.liuzhuoming23.vegetable.propertiesyml;

import com.github.liuzhuoming23.vegetable.propertiesyml.convertor.PropertiesConvertor;
import com.github.liuzhuoming23.vegetable.propertiesyml.convertor.YmlConvertor;
import com.github.liuzhuoming23.vegetable.propertiesyml.domain.YmlTree;
import com.github.liuzhuoming23.vegetable.propertiesyml.io.Reader;
import com.github.liuzhuoming23.vegetable.propertiesyml.io.Writer;
import java.io.File;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * 本地转换测试
 *
 * @author liuzhuoming
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class PropertiesYmlApplicationTests {

    @Test
    public void properties2yml() {
        File in = new File("C:/Users/fm/Desktop/application.properties");
        File out = new File("C:/Users/fm/Desktop/application.yml");
        Writer.write(out, new YmlConvertor().convert(in));
    }

    @Test
    public void yml2properties() {
        File in = new File("C:/Users/fm/Desktop/application-test.yml");
        File out = new File("C:/Users/fm/Desktop/application-test.properties");

        Writer.write(out, new PropertiesConvertor().convert(in));
    }

    @Test
    public void test() {
        File in = new File("C:/Users/fm/Desktop/test.yml");
        List<YmlTree> ymlTrees = PropertiesConvertor.parse(Reader.line(in));
        System.out.println(ymlTrees.toString());
    }
}
