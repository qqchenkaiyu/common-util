package com.cky.compile;

import com.cky.check.Assert;
import com.cky.io.FileUtil;
import org.junit.Test;

import java.io.File;

import static com.cky.compile.CompileUtil.compile;

public class CompileUtilTest {

    @Test
    public void compileTest() throws Exception {
        String code="package com.cky.compile;\n" +
                "\n" +
                "public class Hello {\n" +
                "    \n" +
                "}";
        compile(code, FileUtil.getAbsolutePath(""));
        Assert.isTrue(new File(FileUtil.getAbsolutePath("com/cky/compile/Hello.class")).exists());
    }

}