package com.support;

import com.publicgroup.util.log.LogFactory;
import com.support.core.resolver.StringViewResolver;
import org.junit.Assert;
import org.junit.Test;

import static com.support.core.resolver.StringViewResolver.PAYSUCCESS;

public class StringViewResolverTest {

    @Test
    public void test(){
        String out= StringViewResolver.show(PAYSUCCESS,100.11);
        Assert.assertEquals("您已成功付款100.11 元",out);
    }
}
