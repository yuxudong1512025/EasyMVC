package com.support;

import static org.junit.Assert.assertTrue;

import com.publicgroup.factory.DefaultListableBeanFactory;
import com.publicgroup.util.log.LogFactory;
import com.support.core.controller.DefaultDispatcherController;
import org.junit.Assert;
import org.junit.Test;

import java.util.logging.Logger;

/**
 * Unit test for simple App.
 */
public class AppTest
{

    private static final Logger logger= LogFactory.getGlobalLog();

    @Test
    public void registerTest() throws Exception{
        logger.info(App.class.getResource("/bean.xml").getFile());
        DefaultListableBeanFactory defaultListableBeanFactory = new DefaultListableBeanFactory(App.class.getResource("/bean.xml").getFile());
        DefaultDispatcherController defaultDispatcherController = (DefaultDispatcherController) defaultListableBeanFactory.getBean("defaultDispatcherController");
        defaultDispatcherController.setBeanFactory(defaultListableBeanFactory);
        defaultDispatcherController.setResource(App.class.getResource("/trans.xml").getFile());

//        String registerSuccess =defaultDispatcherController.execute("register.do?username=gnn&password=123");
//        Assert.assertEquals("gnn 注册成功，欢迎您的加入",registerSuccess);
//        String registerFailure=defaultDispatcherController.execute("register.do?username=gnn&password=12");
//        Assert.assertEquals("该用户已注册",registerFailure);
        String empty = defaultDispatcherController.execute("register.do?password=1");
        Assert.assertEquals("用户名或密码不能为空",empty);

    }

    @Test
    public void loginTest() throws Exception
    {

        logger.info(App.class.getResource("/bean.xml").getFile());
        DefaultListableBeanFactory defaultListableBeanFactory = new DefaultListableBeanFactory(App.class.getResource("/bean.xml").getFile());
        DefaultDispatcherController defaultDispatcherController = (DefaultDispatcherController) defaultListableBeanFactory.getBean("defaultDispatcherController");
        defaultDispatcherController.setBeanFactory(defaultListableBeanFactory);
        defaultDispatcherController.setResource(App.class.getResource("/trans.xml").getFile());
        String loginSuccess=defaultDispatcherController.execute("login?username=gu&password=123");
        Assert.assertEquals("登录成功，欢迎您 gu",loginSuccess);
        String notRegister =defaultDispatcherController.execute("login.do?username=gu22&password=123");
        Assert.assertEquals("抱歉 gu22 未注册",notRegister);
        String wrongPassword=defaultDispatcherController.execute("login.do?username=gu&password=123456");
        Assert.assertEquals("密码错误",wrongPassword);
    }

    @Test
    public void depositTest() throws Exception
    {

        logger.info(App.class.getResource("/bean.xml").getFile());
        DefaultListableBeanFactory defaultListableBeanFactory = new DefaultListableBeanFactory(App.class.getResource("/bean.xml").getFile());
        DefaultDispatcherController defaultDispatcherController = (DefaultDispatcherController) defaultListableBeanFactory.getBean("defaultDispatcherController");
        defaultDispatcherController.setBeanFactory(defaultListableBeanFactory);
        defaultDispatcherController.setResource(App.class.getResource("/trans.xml").getFile());
        String loginSuccess=defaultDispatcherController.execute("login?username=gu&password=123");
        Assert.assertEquals("登录成功，欢迎您 gu",loginSuccess);
        String depositSuccess =defaultDispatcherController.execute("deposit.do?money=100");
        Assert.assertEquals("您已成功充值100 元",depositSuccess);
//        String depositFailure =defaultDispatcherController.execute("deposit.do?money=100");
//        Assert.assertEquals("请先登录",depositFailure);
    }

    @Test
    public void payTest() throws Exception
    {

        logger.info(App.class.getResource("/bean.xml").getFile());
        DefaultListableBeanFactory defaultListableBeanFactory = new DefaultListableBeanFactory(App.class.getResource("/bean.xml").getFile());
        DefaultDispatcherController defaultDispatcherController = (DefaultDispatcherController) defaultListableBeanFactory.getBean("defaultDispatcherController");
        defaultDispatcherController.setBeanFactory(defaultListableBeanFactory);
        defaultDispatcherController.setResource(App.class.getResource("/trans.xml").getFile());
//        String loginSuccess=defaultDispatcherController.execute("login?username=gu&password=123");
//        Assert.assertEquals("登录成功，欢迎您 gu",loginSuccess);
//        String depositSuccess =defaultDispatcherController.execute("pay.do?money=100");
//        Assert.assertEquals("您已成功付款100 元",depositSuccess);
//
//        String notEnoughMoney =defaultDispatcherController.execute("pay.do?money=100000");
//        Assert.assertEquals("付款失败，您的余额不足 100000 元",notEnoughMoney);

        String payFailure =defaultDispatcherController.execute("pay.do?money=100");
        Assert.assertEquals("请先登录",payFailure);
        System.out.println(payFailure);
    }




}
