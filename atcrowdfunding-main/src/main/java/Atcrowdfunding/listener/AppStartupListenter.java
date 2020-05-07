package Atcrowdfunding.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import Atcrowdfunding.consts.AppConsts;

/**
 *	web组件创建的步骤：
 *		1、创建类实现特定的接口
 *		2、在web.xml中配置：
 *			配置全类名
 */
public class AppStartupListenter implements ServletContextListener {

    public void contextDestroyed(ServletContextEvent sce)  { 
    }

    public void contextInitialized(ServletContextEvent sce)  { 
    	//存入路径
    	sce.getServletContext().setAttribute(AppConsts.CONTEXT_PATH, sce.getServletContext().getContextPath());
    }
	
}
