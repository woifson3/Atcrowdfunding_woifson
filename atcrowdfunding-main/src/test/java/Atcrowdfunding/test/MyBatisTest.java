package Atcrowdfunding.test;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import Atcrowdfunding.bean.TAdmin;
import Atcrowdfunding.bean.TAdminExample;
import Atcrowdfunding.mapper.TAdminMapper;
//使用SpringTest： spring测试可以配置spring配置文件  创建测试环境使用的容器对象
//1、使用Spring单元测试来驱动普通的测试方法
@RunWith(value=SpringJUnit4ClassRunner.class)
//2、加载Spring配置文件
@ContextConfiguration(locations={"classpath:spring/spring-bean.xml",
"classpath:spring/spring-mybatis.xml","classpath:spring/spring-tx.xml"})

public class MyBatisTest {
	
	//使用commons-logging  进行日志打印：
	//开发中  使用不同的框架整合开发，不同的框架选择使用不同的日志包，不同的日志包api不一样，增加了程序员的学习成本
	//Log log = LogFactory.getLog(getClass());
	
	//使用slf4j+logback打印日志：
	//步骤：1、在pom中导入slf4j+logback两个依赖 ， 2、导入logback的配置文件  3、使用
	Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	TAdminMapper adminMapper;
	@Test
	public void test() {
		// spring已经配置了commons-logging，默认输出info级别以上的日志
		//  debug-> info -> warn -> error
		//log.debug("开始测试....");
		logger.debug("开始测试，时间：{}", System.currentTimeMillis());
		TAdminExample e = new TAdminExample();
		e.createCriteria().andLoginacctEqualTo("zhangsan").andUserpswdEqualTo("123456");
		//log.info("即将查询数据库...");
		logger.info("即将查询数据库...");
		List<TAdmin> list = adminMapper.selectByExample(e );
		//log.warn("查询数据库完毕...");
		logger.warn("查询数据库完毕,结果：{} , 结束时间：{}", list , System.currentTimeMillis());
		//System.out.println(list);
		//log.error("查询结果："+list);
		logger.error("hehe");
	}

}
