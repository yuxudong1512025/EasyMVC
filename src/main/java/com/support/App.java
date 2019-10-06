package com.support;

import com.publicgroup.factory.DefaultListableBeanFactory;
import com.publicgroup.util.log.LogFactory;
import com.support.core.controller.DefaultDispatcherController;

import java.io.File;
import java.util.Scanner;
import java.util.logging.Logger;

/**
 * Hello world!
 */
public class App {
	private static final Logger logger= LogFactory.getGlobalLog();
	public static void main(String[] args) throws Exception {
		String result;

		logger.info(App.class.getResource("/bean.xml").getFile());
		DefaultListableBeanFactory defaultListableBeanFactory = new DefaultListableBeanFactory(App.class.getResource("/bean.xml").getFile());
		DefaultDispatcherController defaultDispatcherController = (DefaultDispatcherController) defaultListableBeanFactory.getBean("defaultDispatcherController");
		defaultDispatcherController.setBeanFactory(defaultListableBeanFactory);
		defaultDispatcherController.setResource(App.class.getResource("/trans.xml").getFile());
		result=defaultDispatcherController.execute("login?username=gu&password=123");
		logger.info(result);

		Scanner scanner = new Scanner(System.in);
		String input = null;
		while (true) {
			input = scanner.next();
			if ("*".equals(input)) {
				break;
			}
			result=defaultDispatcherController.execute(input);
			logger.info(result);
		}


		logger.info("finish");
	}
}
