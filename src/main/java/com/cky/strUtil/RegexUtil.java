package com.cky.strUtil;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexUtil {
    public static String findFirst(String regex, CharSequence content){
        Pattern m = Pattern.compile(regex);
        Matcher matcher = m.matcher(content);
        if (matcher.find()) {
            String group = matcher.group(0);
            return group;
        }
        return "";
    }
    public static String findFirstGroup(String regex, CharSequence content){
        Pattern m = Pattern.compile(regex);
        Matcher matcher = m.matcher(content);
        if (matcher.find()) {
            int groupCount = matcher.groupCount();
            if(groupCount <1){
                return "";
            };
            String group = matcher.group(1);
            return group;
        }
        return "";
    }
    public static ArrayList<String> findAll(String regex, CharSequence content){
        Pattern m = Pattern.compile(regex);
        Matcher matcher = m.matcher(content);
        ArrayList<String> arrayList = new ArrayList<>();
        while (matcher.find()) {
            String group = matcher.group(0);
            arrayList.add(group);
        }
        return arrayList;
    }
}
