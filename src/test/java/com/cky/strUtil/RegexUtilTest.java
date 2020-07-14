package com.cky.strUtil;

import com.cky.io.FileUtil;
import org.junit.Test;

import static com.cky.compile.CompileUtil.compile;
import static org.junit.Assert.*;

public class RegexUtilTest {

    @Test
    public void findFirst() {
        String code="package com.cky.compile;\n" +
                "\n" +
                "public class Hello {\n" +
                "    \n" +
                "}";
        System.out.println(RegexUtil.findFirst("package\\s[\\w+.]*",code));
    }
    @Test
    public void findFirstGroupTest() {
        String code="package com.cky.compile;\n" +
                "\n" +
                "public class Hello {\n" +
                "    \n" +
                "}";
        System.out.println(RegexUtil.findFirstGroup("package\\s([\\w+.]*)",code));
    }
    @Test
    public void findAll() {
        String code="package com.cky.compile;\n" +
                "\n" +
                "public class Hello {\n" +
                "    \n" +
                "}";
        System.out.println(RegexUtil.findAll("package\\s[\\w+.]*",code).size());
    }
}