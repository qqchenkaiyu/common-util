package com.cky.setting;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.junit.Test;

import static org.junit.Assert.*;

public class SettingTest {
    @Test
    public  void settingTest() throws Exception {
        RedisSetting redisSetting3 = Setting.readSettingToBean("redis.setting",
                RedisSetting.class);
        System.out.println(redisSetting3);
    }
    @Data
    @NoArgsConstructor
    static class RedisSetting{
        @PropName("LOW")
        String low;
        boolean testbool;
    }
}