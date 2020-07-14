/*
 * Copyright (c) Huawei Technologies Co., Ltd. 2019-2020. All rights reserved.
 */

package com.cky.setting;


import com.cky.annotation.AnnotationUtil;
import com.cky.check.Assert;

import java.io.File;
import java.io.FileReader;
import java.lang.reflect.Field;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Properties;

import static com.cky.io.FileUtil.getAbsoluteFile;

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
        Assert.notBlank(path, "Blank setting path !");
        //如果是绝对路径
        File file = getAbsoluteFile(path);
        property.load(new FileReader(file));

    }

    private  <T> T toBean(T target){
        Field[] declaredFields = target.getClass().getDeclaredFields();
        Arrays.stream(declaredFields).forEach(field -> {
            Object annotationValue = AnnotationUtil.getAnnotationValue(field, PropName.class);
            String propName=annotationValue==null?field.getName():annotationValue.toString();
            if(property.containsKey(propName)) {
                safeSetField(field, target, property.get(propName));
            }
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
            } else if (name.endsWith("oolean")) {
                field.set(target, Boolean.parseBoolean(data.toString()));
            }else {
                field.set(target, data);
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }


}
