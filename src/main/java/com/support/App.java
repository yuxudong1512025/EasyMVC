package com.support;
import com.publicgroup.factory.DefaultListableBeanFactory;
import com.support.core.controller.DefaultDispatcherController;

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
//        System.out.println(defaultDispatcherController.execute("hello2?"));

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
