package com.github.liuzhuoming23.vegetable.propertiesyml;

import com.github.liuzhuoming23.vegetable.propertiesyml.convertor.Convertor;
import com.github.liuzhuoming23.vegetable.propertiesyml.convertor.PropertiesConvertor;
import com.github.liuzhuoming23.vegetable.propertiesyml.convertor.YmlConvertor;
import com.github.liuzhuoming23.vegetable.propertiesyml.io.Writer;
import java.io.File;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * 本地转换
 *
 * @author liuzhuoming
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class PropertiesYmlApplicationTests {

    @Test
    public void properties2yml() {
        Convertor convertor = new YmlConvertor();
        File in = new File("/Users/liuzhuoming/Desktop/test.properties");
        File out = new File("/Users/liuzhuoming/Desktop/test.yml");
        Writer.write(out, convertor.convert(in));
    }

    @Test
    public void yml2properties() {
        Convertor convertor = new PropertiesConvertor();
        File in = new File("C:/Users/fm/Desktop/yml.yml");
        File out = new File("C:/Users/fm/Desktop/yml.properties");
        Writer.write(out, convertor.convert(in));
    }
}
