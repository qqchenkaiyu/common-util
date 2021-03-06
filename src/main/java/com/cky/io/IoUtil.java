package com.cky.io;

import static com.cky.io.FileUtil.getAbsoluteFile;

import cn.hutool.core.io.IORuntimeException;
import cn.hutool.core.lang.Assert;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class IoUtil {
    /**
     * 默认字符集
     */
    public static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;
    /**
     * 默认缓存大小 8192
     */
    public static final int DEFAULT_BUFFER_SIZE = 2 << 12;
    /**
     * 数据流末尾
     */
    public static final int EOF = -1;
    public static String readStringFromPath(String path,Charset... charsets)  {
        Assert.isTrue(charsets.length<2);
        try (FileInputStream inputStream=getInputStream(path);){
            return readStringFromStream(inputStream, charsets.length==1?charsets[0]:DEFAULT_CHARSET);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    public static String readStringFromStream(InputStream inputStream)  {
        return readStringFromStream(inputStream, DEFAULT_CHARSET);
    }
    public static String readStringFromStream(InputStream inputStream, Charset charset)  {

        ByteArrayOutputStream read = null;
        try {
            read = read(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return new String(read.toByteArray(),charset);
    }
    public static long copy(InputStream in, OutputStream out) throws IOException {
       return copy(in, out,DEFAULT_BUFFER_SIZE);
    }
    /**
     * 拷贝流，拷贝后不关闭流
     *
     * @param in             输入流
     * @param out            输出流
     * @param bufferSize     缓存大小
     * @return 传输的byte数
     * @throws IORuntimeException IO异常
     */
    public static long copy(InputStream in, OutputStream out, int bufferSize) throws IOException {
        Assert.notNull(in, "InputStream is null !");
        Assert.notNull(out, "OutputStream is null !");
        if (bufferSize <= 0) {
            bufferSize = DEFAULT_BUFFER_SIZE;
        }

        byte[] buffer = new byte[bufferSize];
        long size = 0;

            for (int readSize; (readSize = in.read(buffer)) != EOF; ) {
                out.write(buffer, 0, readSize);
                size += readSize;
                out.flush();
            }

        return size;
    }
    /**
     * 将Reader中的内容复制到Writer中，拷贝后不关闭Reader
     *
     * @param reader         Reader
     * @param writer         Writer
     * @param bufferSize     缓存大小
     * @return 传输的byte数
     * @throws IORuntimeException IO异常
     */
    public static long copy(Reader reader, Writer writer, int bufferSize) throws IORuntimeException {
        char[] buffer = new char[bufferSize];
        long size = 0;
        int readSize;
        try {
            while ((readSize = reader.read(buffer, 0, bufferSize)) != EOF) {
                writer.write(buffer, 0, readSize);
                size += readSize;
                writer.flush();
            }
        } catch (Exception e) {
            throw new IORuntimeException(e);
        }
        return size;
    }
    /**
     * 从流中读取内容，读到输出流中，读取完毕后并不关闭流
     *
     * @param in 输入流
     * @return 输出流
     * @throws IORuntimeException IO异常
     */
    public static ByteArrayOutputStream read(InputStream in) throws IOException {
        final ByteArrayOutputStream out = new ByteArrayOutputStream();
        copy(in, out);
        return out;
    }
    public static FileInputStream getInputStream(String path) throws IOException {
        File absoluteFile = getAbsoluteFile(path);
        return new FileInputStream(absoluteFile);
    }
    public static BufferedReader getReader(String path) throws IOException {
        return new BufferedReader(new InputStreamReader(getInputStream(path), StandardCharsets.UTF_8));
    }

    public static BufferedWriter getWriter(String path) throws IOException {
        File absoluteFile = getAbsoluteFile(path);
        return new BufferedWriter(new OutputStreamWriter(new FileOutputStream(absoluteFile), StandardCharsets.UTF_8));
    }

    public static void writeStringToPath(String path, String code) {
        try (BufferedWriter writer = IoUtil.getWriter(path)){
            writer.write(code);
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
