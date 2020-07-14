package com.cky.strUtil;

public class StringUtil {
    /**
     * 字符串是否为空白 空白的定义如下： <br>
     * 1、为null <br>
     * 2、为不可见字符（如空格）<br>
     * 3、""<br>
     *
     * @param str 被检测的字符串
     * @return 是否为空
     */
    public static boolean isBlank(String str) {
        if ((str == null) || str.isEmpty()||str.trim().isEmpty()) {
            return true;
        }
        return false;
    }
}
