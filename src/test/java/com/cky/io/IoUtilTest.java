package com.cky.io;

import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import static org.junit.Assert.*;

public class IoUtilTest {

    @Test
    public void readStringFromStream() throws IOException {
        FileInputStream fileInputStream = new FileInputStream(FileUtil.getAbsoluteFile("redis.setting"));
        String s = IoUtil.readStringFromStream(fileInputStream);
        System.out.println(s);
    }
}