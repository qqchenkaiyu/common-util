package com.cky.reflect;

import org.junit.Test;

import java.lang.reflect.Method;

import static org.junit.Assert.*;

public class ReflectUtilTest {

    @Test
    public void getMethodOfObj() {
        Method eat = ReflectUtil.getMethodOfObj(new Dog(), "eat",1,"包子");
    }

    @Test
    public void getMethod() {
    }

    @Test
    public void isAllAssignableFrom() {
    }
    class Dog{
        public void eat(int num,String food){
            System.out.println("我吃了"+num+"个"+food);
        }
    }
}