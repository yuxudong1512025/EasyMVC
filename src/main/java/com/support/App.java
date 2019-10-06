package com.support;
import com.publicgroup.factory.DefaultListableBeanFactory;
import com.support.core.controller.DefaultDispatcherController;
import com.support.model.controller.UserController;
import com.support.model.dao.UserDao;
import com.support.model.dao.xmldata.Reader;
import com.support.model.dao.xmldata.Writer;
import com.support.model.service.UserService;

import java.util.Scanner;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws Exception {
        System.out.println(App.class.getResource("/bean.xml").getFile());
        DefaultListableBeanFactory defaultListableBeanFactory=new DefaultListableBeanFactory(App.class.getResource("/bean.xml").getFile());
        DefaultDispatcherController defaultDispatcherController= (DefaultDispatcherController) defaultListableBeanFactory.getBean("defaultDispatcherController");
        defaultDispatcherController.setBeanFactory(defaultListableBeanFactory);
        defaultDispatcherController.setResource(App.class.getResource("/trans.xml").getFile());
        System.out.println(defaultDispatcherController.execute("login?username=gu1&password=123"));


        Scanner scanner=new Scanner(System.in);
        String input=null;
        while (true){
            input=scanner.next();
            if("*".equals(input)){
                break;
            }
            System.out.println(defaultDispatcherController.execute(input));
        }


        System.out.println("finish");
    }
}
