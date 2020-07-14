package com.cky.annotation;

import com.cky.reflect.ReflectUtil;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;


public class AnnotationUtil {
    /**
     * 获取指定注解属性的值<br>
     * 如果无指定的属性方法返回null
     *
     * @param annotationEle  {@link AnnotatedElement}，可以是Class、Method、Field、Constructor、ReflectPermission
     * @param annotationType 注解类型
     * @return 注解对象
     */
    public static Object getAnnotationValue(AnnotatedElement annotationEle, Class<? extends Annotation> annotationType) {
        final Annotation annotation = getAnnotation(annotationEle, annotationType);
        if (null == annotation) {
            return null;
        }
        return ReflectUtil.invokeMethodOfObj(annotation, "value");
    }
    /**
     * 获取指定注解
     *
     * @param <A>            注解类型
     * @param annotationEle  {@link AnnotatedElement}，可以是Class、Method、Field、Constructor、ReflectPermission
     * @param annotationType 注解类型
     * @return 注解对象
     */
    public static <A extends Annotation> A getAnnotation(AnnotatedElement annotationEle, Class<A> annotationType) {
        return (null == annotationEle) ? null : annotationEle.getAnnotation(annotationType);
    }
}
