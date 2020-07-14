package com.cky.io;

import org.junit.Test;

import java.net.URISyntaxException;

public class FileUtilTest {


    @Test
    public void getAbsolutePath() throws URISyntaxException {
       System.out.println(FileUtil.getAbsolutePath("redis.setting"));
    }
}