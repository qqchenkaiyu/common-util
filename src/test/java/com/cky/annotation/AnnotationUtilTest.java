package com.cky.annotation;

import com.cky.setting.PropName;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.junit.Test;

import java.lang.reflect.Field;
import java.util.Arrays;

import static org.junit.Assert.*;

public class AnnotationUtilTest {

    @Test
    public void getAnnotationValue() {
       // System.out.println(AnnotationUtil.getAnnotationValue());
        Field[] declaredFields = RedisSetting.class.getDeclaredFields();
        Arrays.stream(declaredFields).forEach(field -> {
            Object annotationValue = AnnotationUtil.getAnnotationValue(field, PropName.class);
            System.out.println(annotationValue);
        });
    }

    @Test
    public void getAnnotation() {
        Field[] declaredFields = RedisSetting.class.getDeclaredFields();
        Arrays.stream(declaredFields).forEach(field -> {
            Object annotationValue = AnnotationUtil.getAnnotation(field, PropName.class);
            System.out.println(annotationValue);
        });
    }
    @Data
    static class RedisSetting{
        @PropName("LOW")
        String low;
        boolean testbool;
    }
}