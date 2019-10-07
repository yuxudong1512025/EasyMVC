package com.support;

import com.publicgroup.util.log.LogFactory;
import com.support.model.dao.xmldata.Reader;
import com.support.model.dao.xmldata.Writer;
import com.support.model.entity.User;
import org.junit.Assert;
import org.junit.Test;

import java.util.Set;
import java.util.logging.Logger;

public class XmlTest {
    private static final Logger logger= LogFactory.getGlobalLog();

    @Test
    public void update(){
        String url = Writer.class.getResource("/user.xml").getFile();
        Writer datawrite = new Writer(url);
        datawrite.update(new User("王郁琦", "122322", 123.0));
        Reader reader = new Reader(url);
        Set<User> users = reader.readAllData();
        for (User user : users) {
            logger.info(user.getUserName() + " " + user.getPassword());
        }

    }

    @Test
    public void insert(){
        String url= System.getProperty("user.dir")+"\\src\\main\\resources\\user.xml";
        System.out.println(url);
        Writer datawrite=new Writer(url);
        User insertUser=new User("gnn","123456",100.0);
        datawrite.insert(insertUser);

        Reader reader=new Reader(url);
        Set<User> users= reader.readAllData();
        for (User user:users){
            if(user.getUserName().equals("gnn")){
                Assert.assertEquals(insertUser,user);
            }

        }


    }

}
