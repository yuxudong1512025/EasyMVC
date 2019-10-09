package com.support;

import com.publicgroup.util.log.LogFactory;
import com.support.model.dao.xmldata.Reader;
import com.support.model.dao.xmldata.Writer;
import com.support.model.entity.User;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Set;
import java.util.logging.Logger;

public class XmlTest {
    private static final Logger logger= LogFactory.getGlobalLog();

    @Test
    public void update(){
        String url= System.getProperty("user.dir")+"\\src\\main\\resources\\user.xml";
        Writer datawrite = new Writer(url);
        User updateUser=new User("yu", "123", new BigDecimal("123.00"));
        datawrite.update(updateUser);
        Reader reader = new Reader(url);
        Set<User> users = reader.readAllData();
        for (User user : users) {
            System.out.println(user.toString());
            if("yu".equals(user.getUserName())){
                Assert.assertEquals(updateUser,user);
            }
        }

    }

    @Test
    public void insert(){
        String url= System.getProperty("user.dir")+"\\src\\main\\resources\\user.xml";
        System.out.println(url);
        Writer datawrite=new Writer(url);
        User insertUser=new User("gnn","123456",new BigDecimal("100.00"));
        datawrite.insert(insertUser);

        Reader reader=new Reader(url);
        Set<User> users= reader.readAllData();
        for (User user:users){
            if("gnn".equals(user.getUserName())){
                Assert.assertEquals(insertUser,user);
            }

        }

    }

    @Test
    public void read(){
        String url= System.getProperty("user.dir")+"\\src\\main\\resources\\user.xml";
        Reader xmlread = new Reader(url);
        Set<User> data = xmlread.readAllData();
        for (User user : data) {
            System.out.println(user.toString());
            if("wang".equals(user.getUserName())){
                Assert.assertEquals(user,new User("wang","123",new BigDecimal("0.00")));
            }
        }
    }

    @Test
    public void delete(){
        String url= System.getProperty("user.dir")+"\\src\\main\\resources\\user.xml";
        Writer datawrite = new Writer(url);
        User deleteUser=new User("gu", "123", new BigDecimal("123.00"));
        datawrite.delete(deleteUser);
        Reader xmlread = new Reader(url);
        Set<User> data = xmlread.readAllData();
        for (User user : data) {
            System.out.println(user.toString());
            Assert.assertNotEquals("gu",user.getUserName());
        }

    }

}
