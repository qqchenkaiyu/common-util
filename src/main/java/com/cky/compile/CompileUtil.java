package com.cky.compile;

import javax.tools.*;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2019/4/3.
 */

public class CompileUtil {
    /**
     * 装载字符串成为java可执行文件
     * @param className className
     * @param javaCodes javaCodes
     * @return Class
     */

    public Class<?> compile(String className, String javaCodes) {
        System.out.println(className);
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        StandardJavaFileManager fileManager = compiler.getStandardFileManager(null, null, null);
        StrSrcJavaObject srcObject = new StrSrcJavaObject(className, javaCodes);
        Iterable<? extends JavaFileObject> fileObjects = Arrays.asList(srcObject);
        String flag = "-d";
        String outDir = "";
        try {
            File classPath = new File(Thread.currentThread().getContextClassLoader().getResource("").toURI());
            outDir = classPath.getAbsolutePath() + File.separator;
        } catch (URISyntaxException e1) {
            e1.printStackTrace();
        }
        Iterable<String> options = Arrays.asList(flag, outDir);
        JavaCompiler.CompilationTask task = compiler.getTask(null, fileManager, null, options, null, fileObjects);
        boolean result = task.call();
        if (result == true) {
            try {
                return Class.forName(className);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return null;

    }


    public Class<?> compile(String javaCodes ) throws IOException {
      return compile(getClassName(javaCodes),javaCodes);
    }

    //从字符串中截取全类名


    private String getClassName(String javaCodes) throws IOException {

        Pattern m = Pattern.compile("^package\\s[\\w+.]*");
        Matcher matcher = m.matcher(javaCodes);
        String packgename = "";
if(matcher.find()){
    String group = matcher.group(0);
    packgename=group.split("package ")[1];
}
        m = Pattern.compile("class\\s[\\w+.$]*");
         matcher = m.matcher(javaCodes);
        String classname = "";
        if(matcher.find()){
            String group = matcher.group(0);
            classname=group.split("class ")[1];
        }
        return packgename+"."+classname;
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
