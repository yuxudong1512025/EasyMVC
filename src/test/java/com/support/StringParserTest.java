package com.support;

import com.support.core.controller.StringParser;
import org.junit.Assert;
import org.junit.Test;

import java.util.Map;

public class StringParserTest {

    @Test
    public void test() {
        Map<String, String> data = StringParser.sendDataFormat("register.do?username=gnn&password=123456");
        Assert.assertNotNull(data);
        Assert.assertEquals("gnn",data.get("username"));
        Assert.assertEquals("123456",data.get("password"));
        Assert.assertEquals("register",data.get("method"));
    }
}
