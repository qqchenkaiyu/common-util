package com.cky.strUtil;

import cn.hutool.core.text.UnicodeUtil;
import com.cky.check.Assert;

public class UnicodeUtilTest {

    @org.junit.Test
    public void unicodeStr2String() {
        String str = "aaa\\U4e2d\\u6587\\u111\\urtyu\\u0026";
        String res = UnicodeUtil.toString(str);
        Assert.isEquals(res,"aaa中文\\u111\\urtyu&");
    }
}