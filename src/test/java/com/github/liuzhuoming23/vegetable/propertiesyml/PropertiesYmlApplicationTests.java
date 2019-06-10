package com.github.liuzhuoming23.vegetable.propertiesyml;

import com.github.liuzhuoming23.vegetable.propertiesyml.convertor.PropertiesConvertor;
import com.github.liuzhuoming23.vegetable.propertiesyml.convertor.YmlConvertor;
import com.github.liuzhuoming23.vegetable.propertiesyml.io.Reader;
import com.github.liuzhuoming23.vegetable.propertiesyml.io.Writer;
import java.io.File;
import java.util.ArrayList;
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
        File out = new File("C:/Users/fm/Desktop/test.properties");

        List<String> lines = Reader.line(in);

        List<List<String>> lists = PropertiesConvertor.parseList2ListInList(lines);

        for (List<String> strings : lists) {
            List<String> strings1 = new ArrayList<>(strings);
            parse(strings1, "", PropertiesConvertor.indentNum(strings.get(0)));
        }
        Writer.write(out, data);
    }

    private static List<String> data = new ArrayList<>();

    private void parse(List<String> lines, String prefix, int indent) {
        if (lines.size() != 0) {
            String str = lines.get(0);
            lines.remove(0);
            if (!"".equals(str.trim()) && !str.trim().startsWith("#")) {
                String line = str.stripLeading();
                String[] strings = line.split(":");

                String name = strings[0];

                String value = "";
                if (strings.length == 2) {
                    value = strings[1].trim();
                }

                int indentNum = PropertiesConvertor.indentNum(str);

                if (indentNum > indent) {
                    if (!"".equalsIgnoreCase(value)) {
                        data.add(prefix + name + "=" + value);
                        parse(lines, prefix, indentNum);
                    } else {
                        parse(lines, prefix + name + ".", indentNum);
                    }
                } else if (indentNum == indent) {
                    parse(lines, prefix, indentNum);
                } else {
                    //TODO
                }
            } else {
                parse(lines, prefix, indent);
            }
        }
    }
}
