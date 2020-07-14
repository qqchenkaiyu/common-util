package com.cky.compile;

import com.cky.strUtil.RegexUtil;

import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.SimpleJavaFileObject;
import javax.tools.ToolProvider;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.URI;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Arrays;

/**
 * Created by Administrator on 2019/4/3.
 */

public class CompileUtil {

    /**
     * 装载字符串成为java可执行文件
     *
     * @param className className
     * @param javaCodes javaCodes
     * @return Class
     */

    private static void compile(String className, String javaCodes, String outDir, String lib) throws Exception {
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        if (compiler == null) {
            throw new RuntimeException("ToolProvider.getSystemJavaCompiler() == null   java_home maybe not set!!");
        }
        // 用来获取编译错误时的错误信息
        /** START 以下代码在打包成web程序时必须开启，在编辑器里面时请屏蔽 */
        StringBuilder cp = new StringBuilder();
        URLClassLoader urlClassLoader = (URLClassLoader) Thread.currentThread().getContextClassLoader();
        for (URL url : urlClassLoader.getURLs()) {
            cp.append(url.getFile()).append(File.pathSeparator);
        }
        if (lib != null) {
            //WriterLog.log("list file"+lib);
            File[] list = new File(lib).listFiles();
            // WriterLog.log("list file length"+list.length);
            for (File jar : list) {
                if (jar.getName().endsWith("jar"))
                    cp.append(jar.getAbsolutePath()).append(File.pathSeparator);
            }
        }

        /**  END  以上代码在打包成web程序时必须开启，在编辑器里面时请屏蔽 */
        StrSrcJavaObject srcObject = new StrSrcJavaObject(className, javaCodes);
        Iterable<? extends JavaFileObject> fileObjects = Arrays.asList(srcObject);
        Iterable<String> options = Arrays.asList("-d", outDir, "-classpath", cp.toString());
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        OutputStreamWriter writer = new OutputStreamWriter(byteArrayOutputStream);
        JavaCompiler.CompilationTask task = compiler.getTask(writer, null, null, options, null, fileObjects);
        boolean result = task.call();
        if (result == true) {
            return;
        }
        writer.flush();
        // WriterLog.log("编译时添加的cp "+cp.toString());
        // WriterLog.log(new String(byteArrayOutputStream.toByteArray(), StandardCharsets.UTF_8));
        return;

    }

    public static void compile(String javaCodes, String outDir, String lib) throws Exception {
        compile(getClassName(javaCodes), javaCodes, outDir, lib);
    }
    public static void compile(String javaCodes, String outDir) throws Exception {
        compile(getClassName(javaCodes), javaCodes, outDir, null);
    }
    //从字符串中截取全类名


    private static String getClassName(String javaCodes) throws IOException {
        String packgename = RegexUtil.findFirstGroup("package\\s[\\w+.]*",javaCodes);
        String classname = RegexUtil.findFirstGroup("class\\s[\\w+.]*",javaCodes);
        return packgename + "." + classname;
    }

    private static class StrSrcJavaObject extends SimpleJavaFileObject {
        private String content;

        StrSrcJavaObject(String name, String content) {
            super(URI.create("string:///" + name.replace('.', '/') + Kind.SOURCE.extension), Kind.SOURCE);
            this.content = content;
        }

        public CharSequence getCharContent(boolean ignoreEncodingErrors) {
            return content;
        }
    }

}
