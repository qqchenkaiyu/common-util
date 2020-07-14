package com.cky.io;

import com.cky.check.Assert;
import com.cky.strUtil.StringUtil;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;

public class FileUtil {
    public static boolean isAbsolutePath(String path) {
       if(StringUtil.isBlank(path)){
           return false;
       }
        return '/' == path.charAt(0) || path.matches("^[a-zA-Z]:([/\\\\].*)?");
    }

    public static File getAbsoluteFile(String path) throws URISyntaxException {
        File file=new File(getAbsolutePath(path));
        Assert.isTrue(file.exists(),path+" is not exist");
        return file;
    }

    public static String getAbsolutePath(String path) throws URISyntaxException {
        if (isAbsolutePath(path)) {
            return path;
        } else {
            //如果是相对路径
            URL resource = Thread.currentThread().getContextClassLoader().getResource(path);
            Assert.notNull(resource,path+" is not exist");
            return new File(resource.toURI()).getAbsolutePath();
        }
    }
}
