package com.cky.reflect;


import com.cky.strUtil.StringUtil;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

public class ReflectUtil {

    public static Object invokeMethodOfObj(Object obj, String methodName, Object... args) throws SecurityException {
        Method method = getMethod(obj.getClass(), methodName, args);
        Object invoke;
        try {
            invoke = method.invoke(obj, args);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return invoke;
    }
    /**
     * 查找指定对象中的所有方法（包括非public方法），也包括父对象和Object类的方法
     *
     * <p>
     * 此方法为精准获取方法名，即方法名和参数数量和类型必须一致，否则返回<code>null</code>。
     * </p>
     *
     * @param obj        被查找的对象，如果为{@code null}返回{@code null}
     * @param methodName 方法名，如果为空字符串返回{@code null}
     * @param args       参数
     * @return 方法
     * @throws SecurityException 无访问权限抛出异常
     */
    public static Method getMethodOfObj(Object obj, String methodName, Object... args) throws SecurityException {
        if (null == obj ) {
            return null;
        }
        return getMethod(obj.getClass(), methodName, args);
    }
    /**
     * 查找指定方法 如果找不到对应的方法则返回<code>null</code>
     *
     * <p>
     * 此方法为精准获取方法名，即方法名和参数数量和类型必须一致，否则返回<code>null</code>。
     * </p>
     *
     * @param clazz      类，如果为{@code null}返回{@code null}
     * @param methodName 方法名，如果为空字符串返回{@code null}
     * @return 方法
     * @throws SecurityException 无权访问抛出异常
     * @since 3.2.0
     */
    public static Method getMethod(Class<?> clazz, String methodName,Object... args) throws SecurityException {
        if (null == clazz || StringUtil.isBlank(methodName)) {
            return null;
        }
        Class<?>[] classes = getClasses(args);
        final Method[] methods = clazz.getDeclaredMethods();
            for (Method method : methods) {
                if (Objects.equals(methodName, method.getName())) {
                    if (isAllAssignableFrom(method.getParameterTypes(), classes)) {
                        return method;
                    }
                }
            }
        return null;
    }
    /**
     * 获得对象数组的类数组
     *
     * @param objects 对象数组，如果数组中存在{@code null}元素，则此元素被认为是Object类型
     * @return 类数组
     */
    public static Class<?>[] getClasses(Object... objects) {
        Class<?>[] classes = new Class<?>[objects.length];
        Object obj;
        for (int i = 0; i < objects.length; i++) {
            obj = objects[i];
            classes[i] = (null == obj) ? Object.class : obj.getClass();
        }
        return classes;
    }
    /**
     * 比较判断types1和types2两组类，如果types1中所有的类都与types2对应位置的类相同，或者是其父类或接口，则返回<code>true</code>
     *
     * @param types1 类组1
     * @param types2 类组2
     * @return 是否相同、父类或接口
     */
    public static boolean isAllAssignableFrom(Class<?>[] types1, Class<?>[] types2) {
        if (types1.length != types2.length) {
            return false;
        }
        for (int i = 0; i < types1.length; i++) {
            if(!isAllAssignableFrom(types1[i],types2[i])){
                return false;
            }
        }
        return true;
    }
    /**
     * type1是否能由type2赋值
     *
     * @return 是否相同、父类或接口
     */
    public static boolean isAllAssignableFrom(Class<?> type1, Class<?>type2) {
        //直接相同或type1是父类
        if(type1.isAssignableFrom(type2))return true;
     //其中一种是包装类
        if (unWrap(type1) == unWrap(type2)) {
            return true;
        }
     return false;
    }
    /**
     * 包装类转为原始类，非包装类返回原类
     * @param clazz 包装类
     * @return 原始类
     */
    public static Class<?> unWrap(Class<?> clazz){
        if(null == clazz || clazz.isPrimitive()){
            return clazz;
        }
        Class<?> result = wrapperPrimitiveMap.get(clazz);
        return (null == result) ? clazz : result;
    }
    /** 包装类型为Key，原始类型为Value，例如： Integer.class =》 int.class. */
    public static final Map<Class<?>, Class<?>> wrapperPrimitiveMap = new ConcurrentHashMap<>(8);
    static {
        wrapperPrimitiveMap.put(Boolean.class, boolean.class);
        wrapperPrimitiveMap.put(Byte.class, byte.class);
        wrapperPrimitiveMap.put(Character.class, char.class);
        wrapperPrimitiveMap.put(Double.class, double.class);
        wrapperPrimitiveMap.put(Float.class, float.class);
        wrapperPrimitiveMap.put(Integer.class, int.class);
        wrapperPrimitiveMap.put(Long.class, long.class);
        wrapperPrimitiveMap.put(Short.class, short.class);
    }
}
