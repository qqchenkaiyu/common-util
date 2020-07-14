/*
 * Copyright (c) Huawei Technologies Co., Ltd. 2019-2020. All rights reserved.
 */

package hutooltest;

import cn.hutool.core.lang.Assert;

import java.io.File;
import java.io.FileReader;
import java.lang.reflect.Field;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Properties;

/**
 * 功能描述
 *
 * @author c30000456
 * @since 2020-07-14
 */
public class Setting {

    public static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;

    Properties property = new Properties();

    public Setting(String path) throws Exception {
        this(path, DEFAULT_CHARSET);
    }

    public Setting(String path, Charset defaultCharset) throws Exception {
        Assert.notBlank(path, "Blank setting path !", new Object[0]);
        //如果是绝对路径
        File file = null;
        if (isAbsolutePath(path)) {
            file = new File(path);
        } else {
            //如果是相对路径
            URL resource = getClass().getClassLoader().getResource(path);
            file = new File(resource.toURI().getPath());
        }
        property.load(new FileReader(file));

    }

    private static boolean isAbsolutePath(String path) {
        return '/' == path.charAt(0) || path.matches("^[a-zA-Z]:([/\\\\].*)?");
    }

    private  <T> T toBean(T target){
        Field[] declaredFields = target.getClass().getDeclaredFields();
        Arrays.stream(declaredFields).filter(field -> property.containsKey(field.getName())).forEach(field -> {
            safeSetField(field, target, property.get(field.getName()));
        });

        return target;
    }
    private  <T> T toBean(Class<T> clazz) throws IllegalAccessException, InstantiationException {
        return toBean( clazz.newInstance());
    }
    public  static  <T> T readSettingToBean(String path,Class<T> clazz) throws Exception {
        return new Setting(path).toBean(clazz);
    }
    private void safeSetField(Field field, Object target, Object data) {
        try {
            String name = field.getType().getName();
            if (name.endsWith("ong")) {
                field.set(target, Long.parseLong(data.toString()));
            } else if (name.endsWith("nteger") || name.equals("int")) {
                field.set(target, Integer.parseInt(data.toString()));
            } else {
                field.set(target, data);
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception {
        hutooltest.RedisSetting redisSetting3 = Setting.readSettingToBean("redis.setting",
            hutooltest.RedisSetting.class);
        System.out.println(redisSetting3);
    }
}
